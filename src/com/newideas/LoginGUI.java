package com.newideas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;

public class LoginGUI
{
	private Frame frame_;

	public LoginGUI()
	{
		// make a frame set its title
		Frame frame = new Frame("Stash++");

		// Set the width and height of frame
		frame.setSize(800, 600);

		// Setting the frame visibility to true
		frame.setVisible(true);

		// give the fram a layout
		frame.setLayout(new BorderLayout());

		// add a listener to tell the window what to do when they click close
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
	}
	
	public Frame getFrame()
	{
		return frame_;
	}

	public static void main(String[] args)
	{
		LoginGUI test = new LoginGUI();
		Panel panel = new Panel();
		panel.setSize(300, 300);
		panel.setBackground(Color.darkGray);
		BorderLayout layout = new BorderLayout();
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);

		panel.add(new Button("Center"), BorderLayout.CENTER);
		panel.add(new Button("Line Start"), BorderLayout.LINE_START);
		panel.add(new Button("Line End"), BorderLayout.LINE_END);
		panel.add(new Button("East"), BorderLayout.EAST);
		panel.add(new Button("West"), BorderLayout.WEST);
		panel.add(new Button("North"), BorderLayout.NORTH);
		panel.add(new Button("South"), BorderLayout.SOUTH);

		test.getFrame().add(panel);
	}
}