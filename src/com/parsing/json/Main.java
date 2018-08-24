package com.parsing.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
		getJson("");
	}

	public static void getJson(String nextPageId) throws JsonParseException, IOException
	{
		String poeApiUrl = "http://www.pathofexile.com/api/public-stash-tabs?id=" + nextPageId;

		URL url = new URL(poeApiUrl);

		// make a factory
		JsonFactory jsonFactory = new JsonFactory();

		// create a parser with the file
		JsonParser jsonParser = jsonFactory.createParser(url);

		parseJson(jsonParser);
	}

	public static void parseJson(JsonParser jsonParser) throws IOException
	{
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
				// move to next token
				jsonParser.nextToken();

				// get the text and store it as the current account name
				currentAccountName = jsonParser.getText();
			}

			// if its an items section and we are on the right account name
			if ("items".equals(jsonParser.currentName()) && currentAccountName.equals("5a4oK"))
			{
				System.out.println("found him");
			}
		}

		// wait before going to next page
		try
		{
			Thread.sleep(750);
		} catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}

		// get the next json
		getJson(nextPage);
	}
}
