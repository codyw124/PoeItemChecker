package com.stashplusplus;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stashplusplus.exceptions.FailedToLogin;
import com.stashplusplus.exceptions.FailedToOpenPopUp;
import com.stashplusplus.exceptions.FailedToOpenStashesTab;

public class StashPlusPlus {
	public static void main(String[] args) {
		StashPlusPlus test = new StashPlusPlus();
	}

	private Frame gui_;
	private FirefoxDriver browser_;
	private WebDriverWait browserWaiter_;

	public StashPlusPlus() {
		// make a window
		initLoginGui();

		// make it visible so the user can input their login credentials
		gui_.setVisible(true);
	}

	private void initLoginGui() {
		gui_ = new Frame("Stash++");

		// allow the user to close by clicking the x
		gui_.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		// make the window the size we want
		Dimension d = new Dimension(800, 600);
		gui_.setSize(d);

		// apply a layout to the window
		gui_.setLayout(new GridLayout(3, 1));

		// make a panel for each piece of the layout
		Panel emailPanel = new Panel(new FlowLayout());
		Panel passwordPanel = new Panel(new FlowLayout());
		Panel submitPanel = new Panel(new FlowLayout());

		// add them to the frame
		gui_.add(emailPanel);
		gui_.add(passwordPanel);
		gui_.add(submitPanel);

		// set up the panels we added
		initLoginPanels(emailPanel, passwordPanel, submitPanel);
	}

	private void initLoginPanels(Panel emailPanel, Panel passwordPanel, Panel submitPanel) {
		// make the stuff that we need for the panels
		Label emailLabel = new Label("Email: ");
		final TextField emailField = new TextField(24);

		Label passwordLabel = new Label("Password: ");
		final TextField passwordField = new TextField(24);
		passwordField.setEchoChar('*');

		final String failedToLoginErrorMessage = "Error: could not log in";
		final Label failedToLoginLabel = new Label();
		failedToLoginLabel.setForeground(Color.RED);
		Button submitButton = new Button("Login");

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					// make the gui invisible
					gui_.setVisible(false);

					// login with those
					loginToPoeWebsite(emailField.getText(), passwordField.getText());

					// open the popup window
					openAccountPopup();

					// click the stashes tab to pull up a stash
					openStashesTab();
				} catch (Exception e) {
					// display that an error occurred
					// TODO maybe do an e.getMessage here
					failedToLoginLabel.setText(failedToLoginErrorMessage);

					// make the gui visible again
					gui_.setVisible(true);
				}
			}
		});

		// add all of it
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);

		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		submitPanel.add(failedToLoginLabel);
		submitPanel.add(submitButton);
	}

	private void loginToPoeWebsite(String email, String password) throws FailedToLogin {
		// this is the url we will be navigating to
		final String loginUrl = "http://www.pathofexile.com/login/";

		// make a headless browser and go to login url
		FirefoxOptions options = new FirefoxOptions();
		// options.setHeadless(true);
		browser_ = new FirefoxDriver(options);
		browser_.get(loginUrl);

		// set up wait
		browserWaiter_ = new WebDriverWait(browser_, 10);

		// grab the login email and password elements once it loads
		WebElement login_email = browserWaiter_
				.until(ExpectedConditions.presenceOfElementLocated(By.name("login_email")));
		WebElement login_password = browserWaiter_
				.until(ExpectedConditions.presenceOfElementLocated(By.name("login_password")));

		// grab the submit button
		WebElement login = browserWaiter_.until(ExpectedConditions.presenceOfElementLocated(By.name("login")));

		// if we couldnt grab those 3 throw an exception because we will be unable to
		// login
		if (login_email == null || login_password == null || login == null) {
			throw new FailedToLogin("Could not find form fields");
		}

		// type in credentials and click login
		login_email.sendKeys(email);
		login_password.sendKeys(password);
		login.click();

		// this is the string that the url should contain after we login
		final String expectedUrlAfterLogin = "https://www.pathofexile.com/my-account";

		// check and make sure that we logged in if not throw an exception
		// because logging in failed
		if (!expectedUrlAfterLogin.equals(browser_.getCurrentUrl())) {
			throw new FailedToLogin("URL did not update as expected");
		}
	}

	private void openAccountPopup() throws FailedToOpenPopUp {
		// find the chacacter name in the top left
		WebElement characterName = browserWaiter_
				.until(ExpectedConditions.presenceOfElementLocated(By.className("infoLine1")));

		// if we cant grab that throw an exception because this failed
		if (characterName == null) {
			throw new FailedToOpenPopUp();
		}

		// click on it
		characterName.click();

		// grab the overlay when it appears
		WebElement overlay = browserWaiter_.until(ExpectedConditions.presenceOfElementLocated(By.id("cboxOverlay")));

		// grab the overlays style attribute
		String styleOfOverlay = overlay.getAttribute("style");

		// if the opacity is not 0.9; then we failed to pull it up
		if (!styleOfOverlay.contains("opacity: 0.9;")) {
			throw new FailedToOpenPopUp();
		}
	}

	private void openStashesTab() throws FailedToOpenStashesTab {

	}
}