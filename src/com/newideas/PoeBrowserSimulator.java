package com.newideas;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

	public Set<String> getCharacterList() throws FailedToGoToUrl
	{
		goToCharacterScreen();

		WebElement characters = getElementByClass("characters");

		Document doc = Jsoup.parse(characters.getAttribute("innerHTML"));

		Set<String> names = new TreeSet<String>();

		for (Element element : doc.getAllElements())
		{
			String elementText = element.text();

			// if text isnt concerning level or league it must be char name
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

		WebElement characters = getElementByClass("characters");

		Document doc = Jsoup.parse(characters.getAttribute("innerHTML"));

		for (Element element : doc.getAllElements())
		{
			if(element.text().contains(characterName))
			{
				
			}
			System.out.println(element.text());
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
