package com.stashplusplus.exceptions;

public class FailedToLogin extends Exception
{
	public FailedToLogin()
	{
		super();
	}
	public String getMessage()
	{
		return "Failed To Login";
	}
}
