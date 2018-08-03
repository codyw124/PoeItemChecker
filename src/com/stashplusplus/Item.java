package com.stashplusplus;

import java.util.ArrayList;

public class Item
{
	String name_;
	ArrayList<String> baseStats_;
	ArrayList<String> attributes_;

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
		return attributes_;
	}

	public void addBaseStat(String baseStat)
	{
		baseStats_.add(baseStat);
	}

	public void addAttribute(String attribute)
	{
		attributes_.add(attribute);
	}
}