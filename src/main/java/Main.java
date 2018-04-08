/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;

import decisaocomatosdefala.util.FileUtil;

/**
 *
 * @author edveloso
 */
public class Main {

    public static void main(String args[]) throws Exception{
        FileUtil.execucaoDoArquivo("arquivos"+  File.separator +   "LogMessage.csv");
    }

}
