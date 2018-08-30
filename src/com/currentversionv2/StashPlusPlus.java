package com.currentversionv2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class StashPlusPlus
{
	public static void main(String[] args)
	{
		parsePageAtUrl("242172043-251088127-237186732-272004441-255950913");
	}

	private static void parsePageAtUrl(String currentChangeId)
	{
		// the url we will use to start with
		String poeApiUrl = "http://www.pathofexile.com/api/public-stash-tabs?id=" + currentChangeId;

		long before = System.currentTimeMillis();
		
		// get its contents
		String currentPageJson = getUrlContents(poeApiUrl);

		// get the main object from that string
		JSONObject currentPage = new JSONObject(currentPageJson);

		// get the next id we will use
		String nextChangeId = currentPage.getString("next_change_id");

		// get its stashes
		JSONArray stashes = currentPage.getJSONArray("stashes");

		// for each stash
		for (int i = 0; i < stashes.length(); i++)
		{
			// print the owners name
			JSONObject currentStash = stashes.getJSONObject(i);

			// get the owner
			Object owner = currentStash.get("accountName");

			// if the owner isnt null
			if (owner != null)
			{
				// if they are who we are looking for
				if (owner.toString().equals("Frank1601"))
				{
					// get the array of items in this stash
					JSONArray itemsInCurrentStash = currentStash.getJSONArray("items");

					// for every item
					for (int j = 0; j < itemsInCurrentStash.length(); j++)
					{
						// get the current item
						JSONObject currentItem = itemsInCurrentStash.getJSONObject(j);

						// get the name of that object as an object
						Object currentItemNameObject = currentItem.get("name");

						// if it has a name
						if (currentItemNameObject != "")
						{
							// convert to a string
							String currentItemName = currentItemNameObject.toString();

							// print it
							System.out.println(currentItemName);
						}
					}
				}
			}
		}
		
		long after = System.currentTimeMillis();
		
		final long TOTAL_TIME_TO_WAIT = 750;
		
		long timeToWait = TOTAL_TIME_TO_WAIT - (after - before);
		
		if(timeToWait > 0)
		{
			sleep(timeToWait);
		}

		parsePageAtUrl(nextChangeId);
	}

	private static void sleep(long timeToWait)
	{
		try
		{
			Thread.sleep(timeToWait);
		} catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	// compliments of
	// https://alvinalexander.com/blog/post/java/java-how-read-from-url-string-text
	private static String getUrlContents(String theUrl)
	{
		//make a builder
		StringBuilder builder = new StringBuilder();

		try
		{
			//make a url out of the string url
			URL url = new URL(theUrl);

			//open a conenction to it
			URLConnection urlConnection = url.openConnection();

			//make a buffered reader out of its stream
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			//read the first line
			String line = bufferedReader.readLine();

			do
			{
				if(builder.toString().length() != 0)
				{
					//start a new line
					builder.append("\n");
				}
				
				//go to the next line
				line = bufferedReader.readLine();
				
				//append it
				builder.append(line);
				
			} while (line != null);
			
			//close the reader
			bufferedReader.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return builder.toString();
	}
}
