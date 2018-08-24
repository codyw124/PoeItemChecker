package com.parsing.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		String poeApiUrl = "http://www.pathofexile.com/api/public-stash-tabs?id=";
		
		URL url = new URL(poeApiUrl);
		
		// make a factory
		JsonFactory jsonFactory = new JsonFactory();

		// create a parser with the file
		JsonParser jsonParser = jsonFactory.createParser(url);

		// parse the object we started
		parseObject(jsonParser);
	}

	public static void parseObject(JsonParser jsonParser) throws IOException
	{
		String nextPage = null;
		
		// loop thru the tokens until we get to the end of the object we are on
		while (jsonParser.nextToken() != null)
		{
			if("accountName".equals(jsonParser.currentName()))
			{
				// move to next token
				jsonParser.nextToken();
				
				//get the text
				String currentText = jsonParser.getText();
				
				//if its not null print it
				if(!currentText.equals("null"))
				{
					System.out.println(currentText);
				}
			}
		}
	}

}
