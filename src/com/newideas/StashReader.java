package com.newideas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StashReader
{
	public static void main(String[] args)
	{
		StashReader sr = new StashReader();
		String html = sr.loadTestString();
		
		Document doc = Jsoup.parse(html);
		
		Element itemInfoContainer = doc.getElementById("poe-popup-container");
		Elements items = itemInfoContainer.children();
		
		for(Element x : items)
		{
			System.out.println(x.child(0).child(0).child(1).child(0).text());
		}
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
