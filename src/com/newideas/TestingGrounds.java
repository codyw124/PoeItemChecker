package com.newideas;

import java.util.ArrayList;
import java.util.Set;

import com.newideas.BrowserSimulator.FailedToGoToUrl;
import com.newideas.PoeBrowserSimulator.FailedToOpenOverlay;

public class TestingGrounds
{
	public static void main(String[] args)
	{
		PoeBrowserSimulator test = null;

		try
		{
			test = new PoeBrowserSimulator();

			test.login("cody_w125@ymail.com", "UbisoftBound18");
			
			Set<String> charList = test.getCharacterList();
			
			for(String character : charList)
			{
				//System.out.println(character);
			}
			
			test.selectCharacter("HowLongDoesOnePOELast");
		}
		catch (FailedToGoToUrl e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			test.closeBrowser();
		}
	}
}
