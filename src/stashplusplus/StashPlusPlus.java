package stashplusplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StashPlusPlus
{
	public static void main(String[] args)
	{
		//make a scanner
		Scanner scanner = new Scanner(System.in);

	    //  prompt for the account name
	    System.out.print("Enter your account name: ");

	    // get their input as a String
	    String username = scanner.next();
	    
	    //close the scanner
	    scanner.close();
		
		String currentChangeId = getCurrentChangeId();
		
		while(true)
		{
			currentChangeId = parsePageAtUrl(currentChangeId, username);
		}
	}

	private static String getCurrentChangeId()
	{
		String poeNinjaApi = "http://poe.ninja/api/Data/GetStats/";
		
		String poeNinjaJson = getUrlContents(poeNinjaApi);
		
		JSONObject poeNinjaStats = null;
		
		try
		{
			poeNinjaStats = new JSONObject(poeNinjaJson);
		}
		catch(JSONException e)
		{
			System.err.println(e.getMessage());
		}
		
		String returnValue = "";
		
		if(poeNinjaStats != null)
		{
			returnValue = poeNinjaStats.getString("next_change_id");
		}
		
		return returnValue;
	}

	private static String parsePageAtUrl(String currentChangeId, String usernameToScanFor)
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
				if (owner.toString().equalsIgnoreCase(usernameToScanFor))
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
						if (currentItemNameObject != null)
						{
							// convert to a string
							String currentItemName = currentItemNameObject.toString();

							if(!currentItemName.equals(""))
							{
								String[] test = currentItemName.split(">");
								
								// print it
								System.out.println(test[test.length - 1]);
							}
						}
					}
				}
			}
		}
		
		sleepIfNeeded(before, System.currentTimeMillis());

		return nextChangeId;
	}

	private static void sleepIfNeeded(long before, long after)
	{
		final long TOTAL_TIME_TO_WAIT = 750;
				
		long timeToWait = TOTAL_TIME_TO_WAIT - (after - before);
		
		if(timeToWait > 0)
		{
			try
			{
				Thread.sleep(timeToWait);
			} catch (InterruptedException e)
			{
				System.err.println(e.getMessage());
			}
		}
	}
	
	private static String getUrlContents(String theUrl)
	{
		//make a builder
		StringBuilder builder = new StringBuilder();
		
		try
		{
			URL test = new URL(theUrl);
			
			InputStream testInputStream = test.openStream();
			
			InputStreamReader testInputStreamReader = new InputStreamReader(testInputStream);
			
			BufferedReader testBufferedReader = new BufferedReader(testInputStreamReader);
			
			//read the first line
			String currentLine = "";
			
			//while there are still lines
			while((currentLine = testBufferedReader.readLine()) != null)
			{
				//append current line
				builder.append(currentLine);
			}
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		
		return builder.toString();
	}
}
