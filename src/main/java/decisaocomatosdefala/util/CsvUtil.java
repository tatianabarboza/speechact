package decisaocomatosdefala.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import decisaocomatosdefala.model.Impressao;

public class CsvUtil {


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
