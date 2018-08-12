package com.poe.apireader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Test
{
	public static void main(String[] args)
	{
		parsePage(getPage());

		System.out.println("made it here");
	}
	
	public static void parsePage(String json)
	{
		JsonReader reader = new JsonReader(new StringReader(json));

		String nextId = "";

		try
		{
			reader.beginObject();
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.NAME))
			{
				// get the current token
				String fieldname = reader.nextName();

				if (fieldname.equals("next_change_id"))
				{
					nextId = reader.nextString();
				}
			}
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		
		if(!nextId.equals(""))
		{
			System.out.println(nextId);
			
			try
			{
				Thread.sleep(750);
			}
			catch (InterruptedException e)
			{
				System.err.println(e.getMessage());
			}
			
			parsePage(getPage(nextId));
		}
	}

	public static String getPage()
	{
		String lookUp = "http://www.pathofexile.com/api/public-stash-tabs";

		return getPageHelper(lookUp);

	}

	public static String getPage(String nextId)
	{
		String lookUp = "http://www.pathofexile.com/api/public-stash-tabs?id=" + nextId;

		return getPageHelper(lookUp);
	}

	public static String getPageHelper(String lookUp)
	{
		String returnedJson = "";

		try
		{
			URL url = new URL(lookUp);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			returnedJson = in.readLine();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}

		return returnedJson;
	}
}
