package com.poe.apireader;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(Category.class)
public class Category
{
	String[] accessories_;
}
