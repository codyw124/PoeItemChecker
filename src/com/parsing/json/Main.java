package com.parsing.json;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("hello");

		JsonFactory jasonFactory = new JsonFactory();
		JsonParser jsonParser = jasonFactory.createParser(new File("pokedex.json"));
		parseObject(jsonParser);
		
	}

	public static void parseObject(JsonParser jsonParser) throws IOException
	{
		JsonToken currentToken = ;
		while (currentToken != JsonToken.END_OBJECT)
		{
			currentToken = jsonParser.nextToken();
			if()
			
			// get the current token
			String fieldname = jsonParser.getCurrentName();
			if ("name".equals(fieldname))
			{
				// move to next token
				jsonParser.nextToken();
				System.out.println(jsonParser.getText());
			}
		}
	}
	
}
