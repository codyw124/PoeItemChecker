package com.stashplusplus.exceptions;

public class FailedToLogin extends Exception
{
	public FailedToLogin()
	{
		super();
	}
	
	public FailedToLogin(String message)
	{
		super(message);
	}
}
