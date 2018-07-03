package com.poe.webpage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class StashRetriever
{
	private LoginInfo loginInfo_;

	public StashRetriever(String email, String password)
	{
		loginInfo_ = new LoginInfo(email, password);
	}

	public StashRetriever(LoginInfo loginInfo)
	{
		loginInfo_ = loginInfo;
	}
	
	public void loginToWebsite()
	{
		try
		{
			// make a url containing the page we want to use to login
			URL loginPage = new URL("https://www.pathofexile.com/login");

			// open the stream based on that page
			InputStream is = loginPage.openStream();

			// make a buffered reader to read that stream
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			
			while((line = br.readLine()) != null)
			{
				System.out.println(line);
			}
		}
		catch (MalformedURLException e)
		{
			System.out.println("malformed url using login page");
		}
		catch (IOException e)
		{
			System.out.println("bad io from open stream on login page");
		}
	}

	public Stash getStash(int stashIndex)
	{
		Stash returnStash = new Stash();

		loginToWebsite();

		return returnStash;
	}
}
