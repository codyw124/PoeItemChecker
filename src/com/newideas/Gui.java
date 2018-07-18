package com.newideas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JFrame
{
	private JTextField email_;
	private JTextField password_;
	private int sum = 0;

	public Gui()
	{
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(2, 2, 5, 5));
		
		new GridLayout();

		cp.add(new JLabel("Email: "));
		email_ = new JTextField(10);
		cp.add(email_);
		cp.add(new JLabel("Password: "));
		password_ = new JTextField(10);
		cp.add(password_);

		// Allocate an anonymous instance of an anonymous inner class that
		// implements ActionListener as ActionEvent listener
		email_.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				// Get the String entered into the input TextField, convert to int
				int numberIn = Integer.parseInt(email_.getText());
				sum += numberIn; // accumulate numbers entered into sum
				email_.setText(""); // clear input TextField
				password_.setText(sum + ""); // display sum on the output TextField
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("New Ideas");
		setSize(1280, 720);
		setVisible(true);
	}

	// The entry main() method
	public static void main(String[] args)
	{
		// Run the GUI construction in the Event-Dispatching thread for thread-safety
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Gui(); // Let the constructor do the job
			}
		});
	}
}