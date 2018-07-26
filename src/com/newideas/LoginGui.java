package com.newideas;

import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginGui
{

	public static void main(String[] args)
	{
		// make a frame set its title
		Frame frame = new Frame("Stash++");

		// Set the width and height of frame
		frame.setSize(800, 600);

		// Setting the frame visibility to true
		frame.setVisible(true);

		// add a listener to tell the window what to do when they click close
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});

		Label headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);

		frame.add(headerLabel);
		
		headerLabel.setText("Control in action: Button"); 
	}
}