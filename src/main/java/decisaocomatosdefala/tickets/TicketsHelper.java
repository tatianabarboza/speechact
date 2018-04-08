package decisaocomatosdefala.tickets;

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

import decisaocomatosdefala.execucao.AtosDeFalaDecisao;
import decisaocomatosdefala.model.Mensagem;
import decisaocomatosdefala.model.TicketsComMensagens;
import decisaocomatosdefala.nlp.StopWords;

public class TicketsHelper {

	 private String csvDivisor = ";";

	 public List<TicketsComMensagens> leituraDoArquivoCSV(String caminho) throws FileNotFoundException, IOException, ParseException {
		 	BufferedReader br = getBufferedReaderFrom(caminho);
	        String linha = br.readLine();

	        String[] colunas = linha.split(csvDivisor);
	        TicketsComMensagens ticket = new TicketsComMensagens();
	        ticket.setTicketId((colunas[1]));
	        Mensagem mensagem = new Mensagem();
	        mensagem.setMsgId((colunas[0]));
	        mensagem.setMensagem(StopWords.removendoCaracter(colunas[2]));
	        List<Mensagem> mensagens = new ArrayList<Mensagem>();
	        mensagens.add(mensagem);
	        List<TicketsComMensagens> tickets = new ArrayList<TicketsComMensagens>();
	        while ((linha = br.readLine()) != null) {
	            try {
	                colunas = linha.split(csvDivisor);
	                if (colunas.length > 1 && !colunas[1].equals(ticket.getTicketId().toString())) {
	                    ticket.setMensagens(mensagens);
	                    tickets.add(ticket);
	                    ticket = new TicketsComMensagens();
	                    ticket.setTicketId((colunas[1]));
	                    mensagens = new ArrayList<Mensagem>();
	                }
	                mensagem = new Mensagem();
	                mensagem.setMsgId((colunas[0]));
	                if(colunas.length > 2){
	                		mensagem.setMensagem(StopWords.removendoCaracter(colunas[2]));
	                		mensagens.add(mensagem);
	                }
	            } catch (Exception e) {
	            		System.out.println("Erro ao recuperar tickets do arquivo: " + e.getMessage());
	            }
	        }
	        ticket.setMensagens(mensagens);
	        tickets.add(ticket);
	        return tickets;
	    }

	private BufferedReader getBufferedReaderFrom(String caminho) {
		InputStream fstream =  AtosDeFalaDecisao.class.getResourceAsStream(File.separator + caminho);
		DataInputStream in = new DataInputStream(fstream);
		return new BufferedReader(new InputStreamReader(in));
	}
	
	 
}
