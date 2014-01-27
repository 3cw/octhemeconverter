package com.threecw.ecommerce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageConverter {
	Pattern p1 = Pattern.compile("\\$_\\['([a-zA-Z0-9_]+)'\\]\\s*=\\s*'(.*)';");

	public LanguageConverter(){}
	
	public String convertToString(InputStream phpLang) throws IOException{
		StringBuilder out=new StringBuilder();
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(phpLang));
		String b;
		while ((b=reader.readLine())!=null){
			b=b.trim();
			Matcher m = p1.matcher(b);
			if (m.find()){
				out.append(m.group(1)+"="+m.group(2)+"\n");
			}
		}
		
		return out.toString();
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
