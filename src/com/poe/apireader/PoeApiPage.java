package com.poe.apireader;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(PoeApiPage.class)
public class PoeApiPage
{
	String nextChangeId_;
	Stash[] stashes_;
}
