package com.poe.apireader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Test
{
	public static void main(String[] args)
	{
		String jsonString = getPage();

		JsonParser parser = new JsonParser();
		JsonElement rootNode = parser.parse(jsonString);

		if (rootNode.isJsonObject())
		{
			JsonObject page = rootNode.getAsJsonObject();
			
			JsonElement nameNode = page.get("next_change_id");
			System.out.println("next id: " + nameNode.getAsString());

			JsonElement ageNode = page.get("stashes");
			// System.out.println("Age: " + ageNode.getAsInt());
			//
			// JsonElement verifiedNode = details.get("verified");
			// System.out.println("Verified: " + (verifiedNode.getAsBoolean() ? "Yes" :
			// "No"));
			// JsonArray marks = details.getAsJsonArray("marks");
			//
			// for (int i = 0; i < marks.size(); i++)
			// {
			// JsonPrimitive value = marks.get(i).getAsJsonPrimitive();
			// System.out.print(value.getAsInt() + " ");
			// }
		}
	}

	public static void parsePage(String json)
	{
		JsonReader reader = new JsonReader(new StringReader(json));

		String nextId = "";

		try
		{
			while (reader.hasNext())
			{
				JsonToken token = reader.peek();

				if (token.equals(JsonToken.BEGIN_OBJECT))
				{
					reader.beginObject();
					reader.endObject();
				}
				else if (token.equals(JsonToken.END_OBJECT))
				{
					reader.endObject();
				}
				else
				{
					if (token.equals(JsonToken.NAME))
					{
						// get the current token
						String fieldname = reader.nextName();

						if (fieldname.equals("next_change_id"))
						{
							nextId = reader.nextString();
						}
						else if (fieldname.equals("stashes"))
						{
							reader.beginArray();
							while (reader.peek() != JsonToken.END_ARRAY)
							{
								reader.beginObject();
								reader.nextString();
								reader.nextBoolean();
								reader.nextString();
								reader.nextString();
								reader.nextString();
								reader.nextString();
								reader.nextString();
								reader.beginArray();
								reader.endArray();
								reader.endObject();
							}

							reader.endArray();
						}
					}
				}
			}

		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}

		if (!nextId.equals(""))
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
