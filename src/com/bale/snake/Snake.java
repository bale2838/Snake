package com.bale.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Snake extends JFrame {
	JLabel statusbar = new JLabel("0");
	
	public Snake() {
		add(new Board(this));
		setResizable(false);
		pack();
		
		setTitle("Snake");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		statusbar.setOpaque(true);
		statusbar.setBackground(Color.black);
		statusbar.setForeground(Color.white);
		add(statusbar, BorderLayout.NORTH);
	}
	
	public JLabel getStatusBar() {
		return statusbar;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {		
				JFrame snake = new Snake();
				snake.setVisible(true);
			}
		});
	}
}