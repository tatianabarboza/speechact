/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisaocomatosdefala.execucao;

import java.io.File;

import decisaocomatosdefala.util.FileUtil;

/**
 *
 * @author tatia
 */
public class AtosDeFalaDecisao {

    public static void main(String args[]) throws Exception{
        FileUtil.execucaoDoArquivo("arquivos"+  File.separator +   "LogMessage.csv");
    }

}
