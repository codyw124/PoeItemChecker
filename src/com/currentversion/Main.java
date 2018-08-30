package com.currentversion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		// get the initial json
		String nextPageCode = "236144584-245503859-231017559-265145611-250091940";
		
		while(true)
		{
			nextPageCode = parseApiPage(nextPageCode);
		}
	}

	public static String parseApiPage(String nextPageId) throws IOException
	{
		JsonParser jsonParser = initParser(nextPageId);
		
		String nextPage = null;

		String currentAccountName = "";

		// loop thru the tokens until we get to the end of the object we are on
		while (jsonParser.nextToken() != null)
		{
			// if its the next item page store it
			if ("next_change_id".equals(jsonParser.currentName()))
			{
				// move to next token
				jsonParser.nextToken();

				// store the next page id
				nextPage = jsonParser.getText();
			}

			// if it is an account name store it
			if ("accountName".equals(jsonParser.currentName()))
			{
				
				// get the text and store it as the current account name
				currentAccountName = jsonParser.getText();
			}

			// if its an items section and we are on the right account name
			if ("items".equals(jsonParser.currentName()) && currentAccountName.equals("paridox125"))
			{
				parseArrayOfItems(jsonParser);
			}
		}

		// wait before going to next page
		sleep();

		//	return the next page we will parse
		return nextPage;
	}

	private static void parseArrayOfItems(JsonParser jsonParser)
	{
		
	}

	private static void sleep()
	{
		try
		{
			Thread.sleep(750);
		} catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
	}

	private static JsonParser initParser(String nextPageId)
			throws MalformedURLException, IOException, JsonParseException
	{
		String poeApiUrl = "http://www.pathofexile.com/api/public-stash-tabs?id=" + nextPageId;
		
		URL url = new URL(poeApiUrl);

		// make a factory
		JsonFactory jsonFactory = new JsonFactory();

		// create a parser with the file
		JsonParser jsonParser = jsonFactory.createParser(url);
		return jsonParser;
	}
}
