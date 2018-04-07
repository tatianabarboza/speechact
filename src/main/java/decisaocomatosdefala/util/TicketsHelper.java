package decisaocomatosdefala.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.tokenize.SimpleTokenizer;
import decisaocomatosdefala.execucao.AtosDeFalaDecisao;
import decisaocomatosdefala.model.Mensagem;
import decisaocomatosdefala.model.TicketsComMensagens;
import decisaocomatosdefala.nlp.StopWords;

public class TicketsHelper {

	 public static List<TicketsComMensagens> leituraDoArquivoCSV(String caminho) throws FileNotFoundException, IOException, ParseException {
	        BufferedReader br = null;
	        InputStream fstream =  AtosDeFalaDecisao.class.getResourceAsStream(File.separator + caminho);
	        DataInputStream in = new DataInputStream(fstream);
	        br = new BufferedReader(new InputStreamReader(in));
	        String linha = br.readLine();
	        String csvDivisor = ";";
	        String[] colunas = linha.split(csvDivisor);
	        TicketsComMensagens ticket = new TicketsComMensagens();
	        ticket.setTicketId((colunas[1]));
	        Mensagem mensagem = new Mensagem();
	        mensagem.setMsgId((colunas[0]));
	        mensagem.setMensagem(StopWords.removendoCaracter(colunas[2]));
	        List<Mensagem> mensagens = new ArrayList<Mensagem>();
	        mensagens.add(mensagem);
	        List<TicketsComMensagens> tickets = new ArrayList<TicketsComMensagens>();
	        int tam = tickets.size();
	        int i = 0;
	        TicketsComMensagens ticketPonto = null;
	        while ((linha = br.readLine()) != null) {
	            try {
	                colunas = linha.split(csvDivisor);
	                if (!colunas[1].equals(ticket.getTicketId().toString())) {
	                    ticket.setMensagens(mensagens);
	                    tickets.add(ticket);
	                    ticket = new TicketsComMensagens();
	                    ticket.setTicketId((colunas[1]));
	                    mensagens = new ArrayList<Mensagem>();
	                }
	                mensagem = new Mensagem();
	                mensagem.setMsgId((colunas[0]));
	                mensagem.setMensagem(StopWords.removendoCaracter(colunas[2]));
	                mensagens.add(mensagem);
	            } catch (Exception e) {
	            		System.out.println("Erro ao recuperar tickets do arquivo: " + e.getMessage());
	            }
	        }
	        ticket.setMensagens(mensagens);
	        tickets.add(ticket);
	        return tickets;
	    }
	
	 
}
