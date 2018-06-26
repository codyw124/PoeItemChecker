import java.util.ArrayList;
import java.util.Scanner;

public class ItemChecker
{

	public static void main(String[] args)
	{
		while (true)
		{
			// read in an item
			ArrayList<String> item = getItemInfo();

			if (isGoodItem(item, 3))
			{
				System.out.println("Check this");
			}
			else
			{
				System.out.println("Trash");
			}
		}
	}

	public static ArrayList<String> getItemInfo()
	{
		// make a scanner
		Scanner scanner = new Scanner(System.in);

		// make a place to hold the items info
		ArrayList<String> itemInfo = new ArrayList<String>();

		// make a string that will store each line as it gets
		// read in
		// also make it not empty to differentiate from the last
		// line being read in
		String currentLine = "NewItem";

		// while we havent reached the last line read in
		while (!currentLine.equals(""))
		{
			// get the next line
			currentLine = scanner.nextLine().toUpperCase();

			// append it to the array list
			itemInfo.add(currentLine);
		}

		return itemInfo;
	}

	public static boolean areBoots(ArrayList<String> item)
	{
		boolean areBoots = false;

		// check all mods if any tell us the item is a pair of boots
		// stop checking, if you run out of mods they arent boots
		for (int i = 0; i < item.size() && !areBoots; i++)
		{
			if (item.get(i).contains("BOOTS"))
			{
				areBoots = true;
			}
		}
		return areBoots;
	}

	public static boolean isGoodItem(ArrayList<String> item, int goodModRequirement)
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