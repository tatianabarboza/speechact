package decisaocomatosdefala.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import decisaocomatosdefala.execucao.AtosDeFalaDecisao;

public class FileUtil {
	
	public static File loadFileFromResource(String caminho) {
		InputStream fstream = AtosDeFalaDecisao.class.getResourceAsStream(File.separator + caminho);
		try {
			File tempFile = File.createTempFile("temp-"+ caminho.split("\\.")[0], caminho.split("\\.")[1]);
			tempFile.deleteOnExit();
		    FileOutputStream out = new FileOutputStream(tempFile);
		    IOUtils.copy(FileUtil.class.getResourceAsStream(File.separator + caminho), out);
		    return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
