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
		// make a factory
		JsonFactory jasonFactory = new JsonFactory();

		// create a parser with the file
		JsonParser jsonParser = jasonFactory.createParser(new File("pokedex.json"));

		// parse the object we started
		parseObject(jsonParser);
	}

	public static void parseObject(JsonParser jsonParser) throws IOException
	{
		// loop thru the tokens until we get to the end of the object we are on
		while (jsonParser.nextToken() != null)
		{
			if("id".equals(jsonParser.currentName()))
			{
				// move to next token
				jsonParser.nextToken();
				System.out.println(jsonParser.getText());
			}
		}
	}

}
