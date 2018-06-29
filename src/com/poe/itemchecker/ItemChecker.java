package com.poe.itemchecker;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.libpoe.model.StashTab;
import org.libpoe.model.item.Item;
import org.libpoe.net.AuthInfo;
import org.libpoe.util.League;
import org.libpoe.util.StashLoader;

public class ItemChecker
{
	public static void main(String[] args)
	{
		StashTab myStashTab = StashLoader.fromAccount(new AuthInfo("cody_w125@ymail.com", "7272428"), League.STANDARD, 0);
		
		Item[] myItems = myStashTab.getItems();
		
		System.out.println(myItems[0].getName());
	}
	
	public static ArrayList<String> appendNextPageToList(ArrayList<String> pages, String nextUrlString)
	{
		URL url;
		try
		{
			url = new URL(nextUrlString);
			
			HttpURLConnection con;
			try
			{
				con = (HttpURLConnection) url.openConnection();
				
				try
				{
					con.setRequestMethod("GET");
					
					BufferedReader in;
					try
					{
						in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						
						String inputLine;
						StringBuffer content = new StringBuffer();
						try
						{
							while ((inputLine = in.readLine()) != null)
							{
								content.append(inputLine);
							}
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally
						{
							pages.add(content.toString());
							in.close();
							con.disconnect();
						}
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				catch (ProtocolException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pages;
	}

	public static String[] getItem()
	{
		// make a stored and current to insure we dont run ont
		// the same string twice
		String stored = "";
		String current = "";

		// try to grab a string from the copy buffer
		try
		{
			current = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

			// if we can get one then set it to the stored
			if (!current.equals(stored))
			{
				stored = current;
			}

		}
		catch (HeadlessException | UnsupportedFlavorException | IOException e)
		{
			System.out.println("failed to get copy buffer");
		}

		// make a place to hold the items info
		String[] itemInfo = stored.split("\n");

		return itemInfo;
	}

	public static boolean areBoots(String[] item)
	{
		boolean areBoots = false;

		// check all mods if any tell us the item is a pair of boots
		// stop checking, if you run out of mods they arent boots
		for (int i = 0; i < item.length && !areBoots; i++)
		{
			if (item[i].contains("BOOTS"))
			{
				areBoots = true;
			}
		}
		return areBoots;
	}

	public static boolean isGoodItem(String[] item, int goodModRequirement)
	{
		int goodMods = 0;

		// for each mod on item if good increment by one
		for (String x : item)
		{
			if (isGoodMod(x))
			{
				goodMods++;
			}
		}

		return goodMods >= goodModRequirement;
	}

	public static boolean isGoodMod(String mod)
	{
		boolean returnValue = false;

		// these are the good mods we are checking for
		String[] goodMods =
		{ "MAXIMUM LIFE", "LIGHTNING RESISTANCE", "COLD RESISTANCE", "FIRE RESISTANCE", "ALL RESISTANCES" };

		// check and see if the mod is any of the good ones
		// if so stop checking and set the returnvalue to true
		for (int i = 0; i < goodMods.length && !returnValue; i++)
		{
			if (returnValue == false)
			{
				returnValue = mod.contains(goodMods[i]);
			}
		}

		return returnValue;
	}
}