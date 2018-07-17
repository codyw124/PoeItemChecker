package com.newideas;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;

public class Gui extends Frame
{
	/*
	 * Default Constructor takes no parameters and does initial setup
	 */
	public Gui()
	{
		Panel pnl = new Panel();          // Panel is a container
		Button btn = new Button("Press"); // Button is a component
		pnl.add(btn);                     // The Panel container adds a Button component
	}

	public static void main(String[] a)
	{
		new Gui();  
	}
}
