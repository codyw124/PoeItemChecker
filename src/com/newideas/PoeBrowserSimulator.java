package com.newideas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.WebElement;

import com.stashplusplus.Item;

public class PoeBrowserSimulator extends BrowserSimulator
{
	// this is the url we will be navigating to
	static final String PATHOFEXILE = "https://www.pathofexile.com/login";

	public PoeBrowserSimulator() throws FailedToGoToUrl
	{
		super();
		goToUrl(PATHOFEXILE);
	}

	public void login(String email, String password)
	{
		getElementByName("login_email").sendKeys(email);
		getElementByName("login_password").sendKeys(password);
		getElementByName("login").click();
	}

	public void openAccountPopup() throws FailedToOpenOverlay
	{
		// click to open overlay
		getElementById("inventory-controls").click();

		WebElement overlay = getElementById("cboxOverlay");

		String overlayDisplay = overlay.getAttribute("style");

		// if the overlay is not visible
		if (overlayDisplay.contains("display: none"))
		{
			throw new FailedToOpenOverlay("overlay didnt pop up");
		}
	}

	public Set<String> getCharacterList()
	{
		return null;
	}

	public void selectCharacter(String characterName)
	{
	}

	public ArrayList<Item> getScreenItems()
	{
		return null;
	}

	class FailedToOpenOverlay extends Exception
	{
		public FailedToOpenOverlay()
		{
			super();
		}

		public FailedToOpenOverlay(String message)
		{
			super(message);
		}
	}

}
