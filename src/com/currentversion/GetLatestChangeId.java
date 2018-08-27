package com.currentversion;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

public class GetLatestChangeId
{
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		try (final WebClient webClient = new WebClient())
		{
			final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
			Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

			final String pageAsXml = page.asXml();
			Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

			final String pageAsText = page.asText();
			Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
		}

//		 WebClient webClient = new WebClient();
//		 
//		 HtmlPage page = webClient.getPage("http://poe.ninja/stats/");
//		 
//		 HtmlTableCell cell = page.getHtmlElementById("text-right");
//		 
//		 System.out.println(cell.getTextContent());
	}
}
