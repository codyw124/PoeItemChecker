package com.poe.apireader;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(Requirement.class)
public class Requirement
{
	String name_;
	Value[] values_;
	int displayMode_;
}
