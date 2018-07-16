package com.newideas;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.newideas.exceptions.FailedToLoginException;
import com.newideas.exceptions.FailedToOpenAccountInfo;
import com.newideas.exceptions.FailedToOpenStash;
import com.newideas.exceptions.InvalidCharacterName;

public class NewIdeas
{
	public static void main(String[] args)
	{
		NewIdeas tester_ = new NewIdeas();

		try
		{
			tester_.login("cody_w125@ymail.com", "UbisoftBound18");
			tester_.openAccountInformation();
			// tester_.selectCharacter("NEEDSMOREFROGS".toUpperCase());
			// tester_.selectCharacter("thingofthedeep".toUpperCase());
			// tester_.openStash();
			// tester_.closeBrowser();
		}
		catch (FailedToLoginException e)
		{
			System.err.println("Failed to login with given account information.");
		}
		catch (FailedToOpenAccountInfo e)
		{
			System.err.println("Failed to open account.");
		}
		// catch (InvalidCharacterName e)
		// {
		// System.err.println("Invalid Character name entered.");
		// }
		// catch (FailedToOpenStash e)
		// {
		// System.err.println("Failed to open the stash.");
		// }
	}

	private WebDriver driver_;

	private WebDriverWait wait_;

	public NewIdeas()
	{
		// make it headless
		FirefoxOptions options = new FirefoxOptions();
		// options.setHeadless(true);

		// make a driver
		driver_ = new FirefoxDriver(options);

		// set up wait
		wait_ = new WebDriverWait(driver_, 10);
	}

	public void login(String email, String password) throws FailedToLoginException
	{
		// this is the url we will be navigating to
		final String loginUrl = "http://www.pathofexile.com/login/";

		// go to login page
		driver_.get(loginUrl);

		// grab the login email and password elements once it loads
		WebElement login_email = wait_.until(ExpectedConditions.presenceOfElementLocated(By.name("login_email")));
		WebElement login_password = wait_.until(ExpectedConditions.presenceOfElementLocated(By.name("login_password")));

		// grab the submit button
		WebElement login = wait_.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

		// if we couldnt grab those 3 throw an exception because we will be unable to
		// login
		if (login_email == null || login_password == null || login == null)
		{
			throw new FailedToLoginException();
		}

		// type info
		login_email.sendKeys(email);
		login_password.sendKeys(password);

		// submit to log in
		login.click();

		// this is the string that the url should contain after we login
		final String expectedUrlAfterLogin = "https://www.pathofexile.com/my-account";

		// check and make sure that we logged in if not throw an exception
		// because logging in failed
		if (!expectedUrlAfterLogin.equals(driver_.getCurrentUrl()))
		{
			throw new FailedToLoginException();
		}
	}

	public void openAccountInformation() throws FailedToOpenAccountInfo
	{
		// find the chacacter name in the top left
		WebElement characterName = wait_.until(ExpectedConditions.presenceOfElementLocated(By.className("infoLine1")));
		
		//if we cant grab that throw an exception because this failed
		if(characterName == null)
		{
			throw new FailedToOpenAccountInfo();
		}

		// click on it
		characterName.click();
		
		//grab the overlay when it appears
		WebElement overlay = wait_.until(ExpectedConditions.presenceOfElementLocated(By.id("cboxOverlay")));

		//grab the overlays style attribute
		String styleOfOverlay = overlay.getAttribute("style");

		//if the opacity is not 0.9; then we failed to pull it up
		if (!styleOfOverlay.contains("opacity: 0.9;"))
		{
			throw new FailedToOpenAccountInfo();
		}
	}

	public void selectCharacter(String characterName) throws InvalidCharacterName
	{
		// get all the characters in the list of characters
		List<WebElement> characters = driver_.findElements(By.className("characterName"));

		int currentCharacter = 0;

		// while the current char is not the one im looking for
		// and i still have chars to look for
		while (!characters.get(currentCharacter).getText().toUpperCase().equals(characterName.toUpperCase())
				&& currentCharacter < characters.size())
		{
			currentCharacter++;
		}

		// isolate the single character we want
		WebElement characterWeWant = characters.get(currentCharacter);

		characterWeWant.click();

		// TODO ensure that the character name that we clicked was clicked
		// check to see that it was marked as active

		// if (characterWeWantToPullUpInfoFor != null)
		// {
		// characterWeWantToPullUpInfoFor.click();
		// }
		// else
		// {
		throw new InvalidCharacterName();
		// }

	}

	public void openStash() throws FailedToOpenStash
	{
		// grab the inventory manager
		WebElement inventoryManager = driver_.findElement(By.className("inventoryManager"));

		// find the blades it holds
		List<WebElement> aTagsWithinInventoryManager = inventoryManager.findElements(By.tagName("a"));

		// select the one that will show us the stash
		WebElement showStashButton = aTagsWithinInventoryManager.get(2);

		// wait until it is clickable
		wait_.until(ExpectedConditions.elementToBeClickable(showStashButton));

		// click it
		showStashButton.click();

		// if()
		// {
		//
		// }
		// else
		// {
		// throw new FailedToOpenStash();
		// }
	}

	public void grabItems()
	{
		List<WebElement> a = driver_.findElements(By.className("itemRendered"));

		for (WebElement b : a)
		{
			System.out.println(b.toString());
		}
	}

	public void closeBrowser()
	{
		driver_.close();
	}

	protected void finalize() throws Throwable
	{
		if (driver_ != null)
		{
			driver_.close();
		}
	}
}
