package com.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import com.newideas.NewIdeas;

public class WebAccessTesting
{

	@Test
	public void testLogin()
	{
		NewIdeas test = new NewIdeas();
		
		assert(test.login("cody_w125@ymail.com", "UbisoftBound18"));
	}
	
	@Test
	public void testClickCharacterName()
	{
		NewIdeas test = new NewIdeas();
		
		assert(test.login("cody_w125@ymail.com", "UbisoftBound18"));
		
		assert(test.openStash());
	}

}
