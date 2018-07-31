package com.stashplusplus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stashplusplus.exceptions.FailedToLoginException;

public class Main
{

	public static void main(String[] args)
	{
		try
		{
			goToPoeWebsite();
			// openPopup();
			// Elements wornItems = getItems();
			// navigateToStashSection();
			// Elements stashAndWornItems = getItems();
			//
			// Elements stashItems = stashAndWornItems - wornItems;
			//
			// for (Element item : stashItems)
			// {
			// if (isGood(item))
			// {
			// printName(item);
			// }
			// }
		}
		catch (FailedToLoginException e)
		{
			System.err.println(e.getMessage());
		}

	}

	public static void goToPoeWebsite() throws FailedToLoginException
	{
		// this is the url we will be navigating to
		final String loginUrl = "http://www.pathofexile.com/login/";
		final String loginEmail = "cody_w125@ymail.com";
		final String loginPassword = "UbisoftBound18";

		// make settings that would make the browser headless
		FirefoxOptions options = new FirefoxOptions();
		// options.setHeadless(true);

		// make a browser
		WebDriver browser = new FirefoxDriver(options);

		// set up wait
		WebDriverWait browserWaiter = new WebDriverWait(browser, 10);

		// go to login page
		browser.get(loginUrl);

		// grab the login email and password elements once it loads
		WebElement login_email = browserWaiter
				.until(ExpectedConditions.presenceOfElementLocated(By.name("login_email")));
		WebElement login_password = browserWaiter
				.until(ExpectedConditions.presenceOfElementLocated(By.name("login_password")));

		// grab the submit button
		WebElement login = browserWaiter.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

		// if we couldnt grab those 3 throw an exception because we will be unable to
		// login
		if (login_email == null || login_password == null || login == null)
		{
			throw new FailedToLoginException();
		}

		// type info
		login_email.sendKeys(loginEmail);
		login_password.sendKeys(loginPassword);

		// submit to log in
		login.click();

		// this is the string that the url should contain after we login
		final String expectedUrlAfterLogin = "https://www.pathofexile.com/my-account";

		// check and make sure that we logged in if not throw an exception
		// because logging in failed
		if (!expectedUrlAfterLogin.equals(browser.getCurrentUrl()))
		{
			throw new FailedToLoginException();
		}

	}

}
