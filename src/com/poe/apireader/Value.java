package com.poe.apireader;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(Value.class)
public class Value
{
	String zero_;
	int one_;
}
