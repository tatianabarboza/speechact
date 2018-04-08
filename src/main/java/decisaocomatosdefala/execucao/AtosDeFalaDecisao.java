/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisaocomatosdefala.execucao;

import static opennlp.tools.util.Span.spansToStrings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import opennlp.tools.util.Span;
import opennlp.tools.util.StringUtil;
import decisaocomatosdefala.model.Impressao;
import decisaocomatosdefala.model.Mensagem;
import decisaocomatosdefala.model.PontoDecisao;
import decisaocomatosdefala.model.TicketsComMensagens;
import decisaocomatosdefala.model.Verbo;
import decisaocomatosdefala.nlp.StopWords;
import decisaocomatosdefala.tickets.TicketsHelper;
import decisaocomatosdefala.util.DateUtil;
import decisaocomatosdefala.util.FileUtil;

/**
 *
 * @author tatia
 */
public class AtosDeFalaDecisao {

    public static void main(String args[]) throws Exception{
        execucaoDoArquivo("arquivos"+  File.separator +   "LogMessage.csv");
    }

    public static void execucaoDoArquivo(String caminho) throws IOException, FileNotFoundException, ParseException {
        //Leitura do Arquivo
        List<TicketsComMensagens> tickets = TicketsHelper.leituraDoArquivoCSV(caminho);
        //Limpeza das Mensagens
        List<TicketsComMensagens> ticketLimpos = new ArrayList<TicketsComMensagens>();
        List<Mensagem> mensagens = new ArrayList<Mensagem>();
        TicketsComMensagens ticketLimpo = null;
        if (tickets.isEmpty() == false) {
            for (TicketsComMensagens ticket : tickets) {
                ticketLimpo = new TicketsComMensagens();
                ticketLimpo = ticket;
                Mensagem mensagemLimpa = null;
                for (Mensagem msg : ticket.getMensagens()) {
                    mensagemLimpa = new Mensagem();
                    mensagemLimpa = msg;
                    mensagemLimpa.setMensagem(StopWords.removendoCaracter(mensagemLimpa.getMensagem()));
                    mensagens.add(mensagemLimpa);
                }
                ticketLimpo.setMensagens(mensagens);
                ticketLimpos.add(ticketLimpo);

                mensagens = new ArrayList<Mensagem>();
            }
        }

        List<Impressao> decisoesEncontradas = new ArrayList<>();
        List<TicketsComMensagens> ticketsComVerbos = PontoDecisao.buscandoVerbosEmMensagens(ticketLimpos);
        //Listar somente pontos de decisão

        for (TicketsComMensagens ticket : ticketsComVerbos) {
            for (Mensagem msg : ticket.getMensagens()) {
                for (Verbo verbo : msg.getVerbos()) {
                    Impressao impressao = null;
                    if (verbo.getTipoVerbo().equals("decision")) {
                        impressao = new Impressao(ticket.getTicketId(), msg.getMsgId(), verbo.getVerbo(), verbo.getTipoVerbo(), msg.getMensagem(), msg.getDatahora());
                        decisoesEncontradas.add(impressao);
                    }
                }
            }
        }

        List<Impressao> mensagensAnteriores = new ArrayList<>();

        int msgid = 0;
        Impressao impressao = null;
        for (TicketsComMensagens ticket : ticketsComVerbos) {
            for (Mensagem mensagem : ticket.getMensagens()) {
                Boolean achou = false;
                Verbo verboAchado = null;
                Mensagem mensagemAchada = null;
                for (Impressao impDecisao : decisoesEncontradas) {
                    if (impDecisao.getTicketId().toString().equals(ticket.getTicketId().toString())) {
                        if (mensagem.getMsgId().toString().equals(impDecisao.getMsgId())) {
                        } else {
                            if (Long.parseLong(mensagem.getMsgId()) < Long.parseLong(impDecisao.getMsgId())) {
                                for (Verbo verbo : mensagem.getVerbos()) {
                                    if (!verbo.getTipoVerbo().equals("decision")) {
                                        achou = true;
                                        verboAchado = new Verbo(verbo.getTipoVerbo(), verbo.getVerbo());
                                        mensagemAchada = new Mensagem();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (achou == true) {
                    impressao = new Impressao(ticket.getTicketId(), mensagem.getMsgId(), verboAchado.getVerbo(), verboAchado.getTipoVerbo(), mensagem.getMensagem(), mensagem.getDatahora());
                    mensagensAnteriores.add(impressao);
                }

            }
        }
        FileWriter writer = new FileWriter(FileUtil.loadFileFromResource("arquivos"+  File.separator +   "resultadoLog.csv"));

        System.out.println("PONTOS DE DECISÃO");
        System.out.println("==========================================================================");
        for (Impressao imp : decisoesEncontradas) {
            System.out.println(imp.getTicketId() + ";" + imp.getMsgId() + ";" + imp.getVerbo() + ";" + imp.getTipoVerbo() + ";" + imp.getMensagem());
            writer.append(imp.getTicketId() + ";" + imp.getMsgId() + ";" + imp.getVerbo() + ";" + imp.getTipoVerbo() + ";" + imp.getMensagem() + "\";");
            writer.append(System.lineSeparator());
        }
        System.out.println("Mensagens anteriores =" + mensagensAnteriores.size());
        System.out.println("==========================================================================");
        for (Impressao imp : mensagensAnteriores) {
            System.out.println(imp.getTicketId() + ";" + imp.getMsgId() + ";" + imp.getTipoVerbo() + ";" + imp.getVerbo() + ";" + imp.getMensagem());
            writer.append(imp.getTicketId() + ";" + imp.getMsgId() + ";" + imp.getTipoVerbo() + ";" + imp.getVerbo() + ";" + imp.getMensagem() + "\";");
            writer.append(System.lineSeparator());
        }
        System.out.println("==========================================================================");
        writer.flush();
        writer.close();

    }

   


    public static String[] tokenize(String s) {
        return spansToStrings(tokenizePos(s), s);
    }

    public static Span[] tokenizePos(String s) {
        CharacterEnum charType = CharacterEnum.WHITESPACE;
        CharacterEnum state = charType;

        List<Span> tokens = new ArrayList<>();
        int sl = s.length();
        int start = -1;
        char pc = 0;
        for (int ci = 0; ci < sl; ci++) {
            char c = s.charAt(ci);
            if (StringUtil.isWhitespace(c)) {
                charType = CharacterEnum.WHITESPACE;
            } else if (Character.isLetter(c)) {
                charType = CharacterEnum.ALPHABETIC;
            } else if (Character.isDigit(c)) {
                charType = CharacterEnum.NUMERIC;
            } else {
                charType = CharacterEnum.OTHER;
            }
            if (state == CharacterEnum.WHITESPACE) {
                if (charType != CharacterEnum.WHITESPACE) {
                    start = ci;
                }
            } else {
                if (charType != state || charType == CharacterEnum.OTHER && c != pc) {
                    tokens.add(new Span(start, ci));
                    start = ci;
                }
            }
            state = charType;
            pc = c;
        }
        if (charType != CharacterEnum.WHITESPACE) {
            tokens.add(new Span(start, sl));
        }
        return tokens.toArray(new Span[tokens.size()]);

    }

    static class CharacterEnum {

        static final CharacterEnum WHITESPACE = new CharacterEnum("whitespace");
        static final CharacterEnum ALPHABETIC = new CharacterEnum("alphabetic");
        static final CharacterEnum NUMERIC = new CharacterEnum("numeric");
        static final CharacterEnum OTHER = new CharacterEnum("other");

        private String name;

        private CharacterEnum(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
//
//    public static String removendoCaracter(String paragraph) {
//        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
//        String tokens[] = simpleTokenizer.tokenize(paragraph);
//        String list[] = StopWords.removeStopWords(tokens);
//        paragraph = "";
//        for (String word : list) {
//            paragraph += word + " ";
//        }
//        return paragraph;
//    }

    public List<Impressao> leituraDeCsvParaImpressao(String caminho) throws FileNotFoundException, IOException {
        List<Impressao> impressoes = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(caminho));
        String linha = "";
        String csvDivisor = ";";
        Impressao impressao = null;
        while ((linha = br.readLine()) != null) {
            try {
                String[] colunas = linha.split(csvDivisor);
                String parte1Data = colunas[5].replaceAll("\"", "").substring(0, 16);
                String parte2Data = colunas[5].replaceAll("\"", "").substring(17, 20);
                String data = parte1Data + ":00.0" + parte2Data;
                Date dataHora = (DateUtil.converterStringParaDate(data));
                impressao = new Impressao(colunas[0].replaceAll("\"", ""), colunas[1].replaceAll("\"", ""), colunas[2].replaceAll("\"", ""), colunas[3].replaceAll("\"", ""), colunas[4].replaceAll("\"", ""), dataHora);
                impressoes.add(impressao);
            } catch (Exception e) {

            }

        }
        return impressoes;
    }

}
