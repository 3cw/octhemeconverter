package com.threecw.ecommerce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class LanguageConverterTest {

	@Test
	public void phpConverter() throws IOException{
		LanguageConverter converter=new LanguageConverter();
		String code=""
				+ "$_=array();\n"
				+ "$_[\"test_key\"]=\"test_value\";\n"
				+ "";
		InputStream is=new ByteArrayInputStream(code.getBytes());
		String result=converter.convertToString(is);
		Assert.assertEquals("test_key=test_value;\n", result);
	}
}
