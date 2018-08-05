package com.newideas;

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

			test.openAccountPopup();
			
		}
		catch (FailedToGoToUrl | FailedToOpenOverlay e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			test.closeBrowser();
		}
	}
}
