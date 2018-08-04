package com.stashplusplus;

import java.util.ArrayList;

public class Item
{
	String name_;
	ArrayList<String> baseStats_;
	ArrayList<String> itemAttributes_;

	public Item(String name)
	{
		name_ = name;
	}

	public ArrayList<String> getBaseStats()
	{
		return baseStats_;
	}

	public ArrayList<String> getAttributs()
	{
		return itemAttributes_;
	}

	public void addBaseStat(String baseStat)
	{
		baseStats_.add(baseStat);
	}

	public void addAttribute(String attribute)
	{
		itemAttributes_.add(attribute);
	}
	
	public boolean checkForAttributes(ArrayList<String> attributesIWant)
	{
		boolean checkPassed = true;
		
		for(String attribute : attributesIWant)
		{
			if(!itemAttributes_.contains(attribute)) 
			{
				checkPassed = false;
			}
		}
		
		return checkPassed;
	}
}