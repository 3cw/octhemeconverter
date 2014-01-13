package com.threecw.ecommerce;

import java.io.ByteArrayOutputStream;
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
		
		String code=phpCode.toString();

		QuercusProgram programm=quercus.parseCode(quercus.createString(code));
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
	
}
