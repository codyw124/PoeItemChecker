package com.poe.apireader;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(Stash.class)
public class Stash
{
	String id_;
	boolean public_;
	String accountName_;
	String lastCharacterName_;
	String stash_;
	String stashType_;
	String league_;
	Item[] items_;
}
