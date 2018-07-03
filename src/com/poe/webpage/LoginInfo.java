package com.poe.webpage;

public class LoginInfo
{
	private String email_;
	private String password_;

	public LoginInfo(String email, String password)
	{
		this.email_ = email;
		this.password_ = password;
	}

	public String getEmail()
	{
		return email_;
	}

	public String getPassword()
	{
		return password_;
	}
}