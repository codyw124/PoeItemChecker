package com.poe.apireader;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(Item.class)
public class Item
{
	boolean verified_;
	int w_;
	int h_;
	int ilvl_;
	String icon_;
	String league_;
	String name_;
	String typeLine_;
	boolean identified_;
	boolean corrupted_;
	Requirement[] requirements_;
	String[] implicitMods_;
	String[] explicitMods_;
	int frameType_;
	Category[] category_;
	int x_;
	int y_;
	String inventoryId_;
}
