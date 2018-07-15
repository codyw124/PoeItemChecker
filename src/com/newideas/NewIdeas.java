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

public class NewIdeas
{
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

	public boolean login(String email, String password)
	{
		// this is the url we will be navigating to
		final String loginUrl = "http://www.pathofexile.com/login/";

		// go to login page
		driver_.get(loginUrl);

		// grab the login email and password elements
		WebElement login_email = driver_.findElement(By.name("login_email"));
		WebElement login_password = driver_.findElement(By.name("login_password"));

		// grab the submit button
		WebElement login = driver_.findElement(By.name("login"));

		// type info
		login_email.sendKeys(email);
		login_password.sendKeys(password);

		// submit to log in
		login.click();

		final String expectedUrlAfterLogin = "https://www.pathofexile.com/my-account";
		boolean returnValue = expectedUrlAfterLogin.equals(driver_.getCurrentUrl());

		return returnValue;
	}

	public boolean openCharacterInformation()
	{
		WebElement characterName = driver_.findElement(By.className("infoLine1"));

		characterName.click();

		// TODO need some kind of check here to make sure that we are opening the
		// character information

		return true;
	}

	// TODO need to make sure that we are opening a character in the correct league

	public void selectCharacter(String characterName)
	{
		WebElement characterWeWantToPullUpInfoFor = driver_.findElement(By.linkText(characterName));

		if (characterWeWantToPullUpInfoFor != null)
		{
			characterWeWantToPullUpInfoFor.click();
		}
		else
		{
			// TODO figure out how to throw exceptions
			// throw InvalidCharacterNameException
		}

	}

	public boolean openStash()
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

		// TODO need some way to tell if we clicked the stash button here
		// perhaps items appear -- no there may be no items

		return true;
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
