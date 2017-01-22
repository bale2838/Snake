package com.bale.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Snake extends JFrame {
	JLabel statusbar = new JLabel("0");
	
	public Snake() {
		add(new Board(this));
		setResizable(false);
		pack();
		
		setTitle("Snibbles v1.0");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		statusbar.setOpaque(true);
		statusbar.setBackground(Color.black);
		statusbar.setForeground(Color.white);
		add(statusbar, BorderLayout.PAGE_START);
		//setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/apple.png")));
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