package com.poe;

import static org.junit.Assert.*;

import org.junit.Test;

import com.poe.webpage.LoginInfo;
import com.poe.webpage.StashRetriever;

public class Testing
{

	@Test
	public void T1_LoginInfoDefaults()
	{
		String email = "cody_w125@ymail.com";
		String password = "UbisoftBound18";
		
		LoginInfo test = new LoginInfo(email,password);
		
		assert(email == test.getEmail());
		assert(password == test.getPassword());
	}
	
	@Test
	public void T2_StashRetrieverDefaults()
	{
		String email = "cody_w125@ymail.com";
		String password = "UbisoftBound18";
		
		LoginInfo loginInfo = new LoginInfo(email,password);
		
		StashRetriever test = new StashRetriever(email,password);
		StashRetriever test2 = new StashRetriever(loginInfo);
	}
	
	@Test
	public void T3_PerformLogin()
	{
		String email = "cody_w125@ymail.com";
		String password = "UbisoftBound18";
		
		LoginInfo loginInfo = new LoginInfo(email,password);
		
		StashRetriever test = new StashRetriever(email,password);
		StashRetriever test2 = new StashRetriever(loginInfo);
		
		test.loginToWebsite();
	}

}
