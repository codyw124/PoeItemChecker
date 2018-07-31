package com.stashplusplus.exceptions;

public class FailedToLoginException extends Exception
{
	public FailedToLoginException()
	{
		super();
	}
	public String getMessage()
	{
		return "Failed To Login";
	}
}
