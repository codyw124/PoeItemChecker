package com.newideas;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;

import com.stashplusplus.Item;

public class PoeBrowserSimulator extends BrowserSimulator
{
	// this is the url we will be navigating to
	static final String PATHOFEXILE = "https://www.pathofexile.com/";

	public PoeBrowserSimulator() throws FailedToGoToUrl
	{
		super();
		goToUrl(PATHOFEXILE);
	}

	public void login(String email, String password) throws FailedToGoToUrl
	{
		goToUrl(PATHOFEXILE + "login");
		getElementByName("login_email").sendKeys(email);
		getElementByName("login_password").sendKeys(password);
		getElementByName("login").click();
		goToUrl(PATHOFEXILE);
	}

	// TODO idk if i need this re evaluate at another time
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

	private void goToCharacterScreen() throws FailedToGoToUrl
	{
		// get username
		String username = getElementByClass("profile-link").getText();

		// go to the characters screen
		goToUrl(PATHOFEXILE + "/account/view-profile/" + username + "/characters/");
	}

	public ArrayList<String> getCharacterList() throws FailedToGoToUrl
	{
		goToCharacterScreen();

		List<WebElement> elementList = getElementsByClass("character");

		ArrayList<String> names = new ArrayList<String>();
		
		for (WebElement element : elementList)
		{
			String elementText = element.getText();
			
			//if text isnt concerning level or league it must be char name
			if (!elementText.contains("Level") && !elementText.contains("League"))
			{
				names.add(elementText);
			}
		}

		// return to the home page for consistency
		goToUrl(PATHOFEXILE);

		return names;
	}

	public void selectCharacter(String characterName) throws FailedToGoToUrl
	{
		goToCharacterScreen();

		List<WebElement> elementList = getElementsByClass("character");

		for (WebElement element : elementList)
		{
			if (element.getText().equals(characterName))
			{
				element.click();
			}
		}

		// return to the home page for consistency
		goToUrl(PATHOFEXILE);
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
