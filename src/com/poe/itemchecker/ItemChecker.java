package com.poe.itemchecker;

public class ItemChecker
{	
	public static boolean areBoots(String[] item)
	{
		boolean areBoots = false;

		// check all mods if any tell us the item is a pair of boots
		// stop checking, if you run out of mods they arent boots
		for (int i = 0; i < item.length && !areBoots; i++)
		{
			if (item[i].contains("BOOTS"))
			{
				areBoots = true;
			}
		}
		return areBoots;
	}

	public static boolean isGoodItem(String[] item, int goodModRequirement)
	{
		int goodMods = 0;

		// for each mod on item if good increment by one
		for (String x : item)
		{
			if (isGoodMod(x))
			{
				goodMods++;
			}
		}

		return goodMods >= goodModRequirement;
	}

	public static boolean isGoodMod(String mod)
	{
		boolean returnValue = false;

		// these are the good mods we are checking for
		String[] goodMods =
		{ "MAXIMUM LIFE", "LIGHTNING RESISTANCE", "COLD RESISTANCE", "FIRE RESISTANCE", "ALL RESISTANCES" };

		// check and see if the mod is any of the good ones
		// if so stop checking and set the returnvalue to true
		for (int i = 0; i < goodMods.length && !returnValue; i++)
		{
			if (returnValue == false)
			{
				returnValue = mod.contains(goodMods[i]);
			}
		}

		return returnValue;
	}
}