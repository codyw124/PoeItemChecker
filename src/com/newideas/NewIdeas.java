package com.newideas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NewIdeas
{
	private WebDriver driver_;

	public NewIdeas()
	{
		// make a webdriver
		driver_ = new FirefoxDriver();

//TODO make the browser headless
	}

	public boolean login(String email, String password)
	{
		//this is the url we will be navigating to
		final String loginUrl = "http://www.pathofexile.com/login/";
		
		// go to login page
		driver_.get(loginUrl);

		// grab the login email and password elements
		WebElement login_email = driver_.findElement(By.name("login_email"));
		WebElement login_password = driver_.findElement(By.name("login_password"));

		//grab the submit button
		WebElement login = driver_.findElement(By.name("login"));

		//type info 
		login_email.sendKeys(email);
		login_password.sendKeys(password);

		//submit to log in
		login.click();
		
		final String expectedUrlAfterLogin = "https://www.pathofexile.com/my-account";
		boolean returnValue = expectedUrlAfterLogin.equals(driver_.getCurrentUrl());
		
		// Close the browser
		driver_.quit();
		
		return returnValue;
	}

	
	public boolean openStash()
	{
		boolean success = false;
		
		WebElement characterName = driver_.findElement(By.className("infoLine1"));
		
		characterName.click();
		
		return success;
	}
}
