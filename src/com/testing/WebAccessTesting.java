package com.testing;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.newideas.NewIdeas;

public class WebAccessTesting
{
	private static NewIdeas tester_;

	@BeforeClass()
	public static void setUp()
	{
		tester_ = new NewIdeas();
	}

	@Test
	public void test1Login()
	{
		assert (tester_.login("cody_w125@ymail.com", "UbisoftBound18"));
	}

	@Test
	public void test2ClickCharacterName()
	{
		assert (tester_.openCharacterInformation());
	}

	@Test
	public void test3ClickStash()
	{
		assert (tester_.openStash());
	}

	@AfterClass()
	public static void tearDown()
	{
		// test.closeBrowser();
	}
}
