package com.stashplusplus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stashplusplus.exceptions.FailedToLogin;
import com.stashplusplus.exceptions.FailedToOpenPopUp;

public class StashPlusPlus
{
	public static void main(String[] args)
	{
		StashPlusPlus test = new StashPlusPlus();
		
		try
		{
	
			openPopup();
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
		catch (FailedToOpenPopUp e)
		{
			System.err.println(e.getMessage());
		}
	
	}

	public StashPlusPlus()
	{
		//TODO
		// login gui stuff here
		String email = "Cody_w125@ymail.com";
		String password = "UbisoftBound18";
		LoginCredentials loginCredentials = new LoginCredentials(email, password);

		// the login stuff will blend with this try
		try
		{
			loginToPoeWebsite(loginCredentials);
		}
		catch (FailedToLogin e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void loginToPoeWebsite(LoginCredentials loginCredentials) throws FailedToLogin
	{
		// this is the url we will be navigating to
		final String loginUrl = "http://www.pathofexile.com/login/";

		// make settings that would make the browser headless
		FirefoxOptions options = new FirefoxOptions();
		// TODO
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
			throw new FailedToLogin();
		}

		// type info
		login_email.sendKeys(loginCredentials.getEmail());
		login_password.sendKeys(loginCredentials.getPassword());

		// submit to log in
		login.click();

		// this is the string that the url should contain after we login
		final String expectedUrlAfterLogin = "https://www.pathofexile.com/my-account";

		// check and make sure that we logged in if not throw an exception
		// because logging in failed
		if (!expectedUrlAfterLogin.equals(browser.getCurrentUrl()))
		{
			throw new FailedToLogin();
		}

	}

	private static void openPopup() throws FailedToOpenPopUp
	{
		// find the chacacter name in the top left
		WebElement characterName = browserWaiter
				.until(ExpectedConditions.presenceOfElementLocated(By.className("infoLine1")));

		// if we cant grab that throw an exception because this failed
		if (characterName == null)
		{
			throw new FailedToOpenPopUp();
		}

		// click on it
		characterName.click();

		// grab the overlay when it appears
		WebElement overlay = browserWaiter.until(ExpectedConditions.presenceOfElementLocated(By.id("cboxOverlay")));

		// grab the overlays style attribute
		String styleOfOverlay = overlay.getAttribute("style");

		// if the opacity is not 0.9; then we failed to pull it up
		if (!styleOfOverlay.contains("opacity: 0.9;"))
		{
			throw new FailedToOpenPopUp();
		}
	}

}
