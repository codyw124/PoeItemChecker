package com.newideas;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserSimulator
{
	private FirefoxDriver browser_;
	private WebDriverWait browserWaiter_;

	public BrowserSimulator()
	{
		FirefoxOptions options = new FirefoxOptions();
		//options.setHeadless(true);
		browser_ = new FirefoxDriver(options);
		browserWaiter_ = new WebDriverWait(browser_, 10);
	}

	public void goToUrl(String url) throws FailedToGoToUrl
	{
		browser_.get(url);

		if (!browser_.getCurrentUrl().contains(url))
		{
			throw new FailedToGoToUrl("Current url is not : " + url);
		}
	}

	public WebElement getElementByName(String name)
	{
		WebElement element = browserWaiter_.until(ExpectedConditions.presenceOfElementLocated(By.name(name)));

		return element;
	}

	public WebElement getElementByClass(String className)
	{
		WebElement element = browserWaiter_.until(ExpectedConditions.presenceOfElementLocated(By.className(className)));

		return element;
	}
	
	public WebElement getElementById(String id)
	{
		WebElement element = browserWaiter_.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));

		return element;
	}
	
	public void closeBrowser()
	{
		browser_.close();
	}

	public class FailedToGoToUrl extends Exception
	{
		public FailedToGoToUrl()
		{
			super();
		}

		public FailedToGoToUrl(String message)
		{
			super(message);
		}
	}
}
