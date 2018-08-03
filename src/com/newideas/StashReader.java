package com.newideas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.stashplusplus.Item;

public class StashReader
{
	public static void main(String[] args)
	{
		StashReader sr = new StashReader();
		String html = sr.loadTestString();
		
		Document doc = Jsoup.parse(html);
		
		Element test = doc.getElementById("poe-popup-container");
		
		ArrayList<Item> items = new ArrayList<>();
		
		for(Element x : test.getAllElements())
		{
			if(x.classNames().contains("itemPopupContainer"))
			{
				items.add(new Item(x.getElementsByClass("itemName").get(0).text()));
			}
		}
		
//		return items;
		
//		Element poePopupContainer = doc.getElementById("poe-popup-container");
//		Elements items = poePopupContainer.getAllElements();
//		
//		Elements t = doc.getElementsByClass("itemHeader");
//		
//		System.out.println(t.toArray().length);
//		
//		for(Element x : t)
//		{
//			System.out.println(x.getElementsByClass("itemName").text());
//		}
	}

	public String loadTestString()
	{
		StringBuffer returnValue = new StringBuffer();

		try
		{
			File test = new File("test.html");

			System.out.println(test.exists());
			System.out.println(test.getCanonicalPath());

			BufferedReader br = new BufferedReader(new FileReader(test));

			String st;
			while ((st = br.readLine()) != null)
			{
				returnValue.append(st);
			}

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnValue.toString();
	}

}
