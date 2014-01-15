package com.threecw.ecommerce;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.caucho.quercus.QuercusContext;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.program.QuercusProgram;
import com.caucho.vfs.VfsStream;
import com.caucho.vfs.WriteStream;

public class LanguageConverter {

	private QuercusContext quercus;

	public LanguageConverter(){
		 quercus=new QuercusContext();
		quercus.setCompile(false);
		quercus.start();
		quercus.init();
//		quercus.setScriptEncoding("iso-8859-1");
//		quercus.setIni("unicode.runtime_encoding", "iso-8859-1");		
	}
	
	public String convertToString(InputStream phpLang) throws IOException{
		StringBuilder phpCode=new StringBuilder();
		int b;
		
		while ((b=phpLang.read())!=-1){
			phpCode.append((char)b);
		}
		
		phpCode.append(""
				+ "\n"
				+ " foreach ($_ as $key=>$value){\n"
				+ "	echo \"$key=$value;\n\";"
				+ "}\n"
				+ "");
		
		String code=phpCode.toString().replaceFirst("<\\?php", "").replaceFirst("\\?>", "");

		QuercusProgram programm=quercus.parseCode(code);
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		VfsStream writer=new VfsStream(null, outStream);

		
		WriteStream ws = new WriteStream(writer);
		
//		ws.setEncoding("ISO-8859-1");
		Env env = new Env(quercus, null, ws, null, null);
		env.start();

		programm.execute(env);
		ws.flush();
		
		String result = new String(outStream.toByteArray());            
		return result;
	}
	
	private void convertPhpFileToPropertiesFile(File phpFile, File file) throws IOException {
		FileInputStream is=new FileInputStream(phpFile);
		String output=convertToString(is);
		
		BufferedWriter out=new BufferedWriter(new FileWriter(file));
		out.write(output);
		out.close();
	}


	public void convertDir(File themeLanguageDir, String sourceLanguage, String targetLocale) throws IOException{
		File localeDir=new File(themeLanguageDir, targetLocale);
		if (!localeDir.exists())
			localeDir.mkdirs();
		File languageDir=new File(themeLanguageDir, sourceLanguage);
		
		for (File subDir : languageDir.listFiles()){
			File[] files = subDir.listFiles();
			File localeSubDir=new File(localeDir, subDir.getName());
			if (!localeSubDir.exists())
				localeSubDir.mkdirs();

			if (files!=null){
				for (File phpFile : files){
					String propertiesFileName=phpFile.getName();
					propertiesFileName=propertiesFileName.replaceAll(".php", ".properties");
					convertPhpFileToPropertiesFile(phpFile, new File(localeSubDir, propertiesFileName));
				}
			}
		}
		File mainPhpFile=new File(languageDir, sourceLanguage+".php");
		convertPhpFileToPropertiesFile(mainPhpFile, new File(localeDir, "main.properties"));
	}

}
