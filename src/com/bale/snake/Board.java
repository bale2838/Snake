package com.bale.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.bale.snake.sound.Sound;

public class Board  extends JPanel implements ActionListener {

	private final int B_WIDTH = 300;
	private final int B_HEIGHT = 300;
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = (B_WIDTH * B_HEIGHT) / (DOT_SIZE * DOT_SIZE);
	private final int RAND_POS = 29;
	private final int DELAY = 140;

	// xy-coordinates of each snake bit
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];

	private int dots;
	private int apple_x;
	private int apple_y;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	private boolean isStarted = false;

	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;
	private JLabel statusbar;
	private int score;
	public Snake parent;

	public Board(Snake parent) {
		this.parent = parent;
		statusbar = parent.getStatusBar();
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}

	private void loadImages() {	
		ImageIcon dotImgIcon = new ImageIcon(Board.class.getResource("/dot.png"));
		ball = dotImgIcon.getImage();

		ImageIcon appleImgIcon = new ImageIcon(Board.class.getResource("/apple.png"));
		apple = appleImgIcon.getImage();

		ImageIcon headImgIcon = new ImageIcon(Board.class.getResource("/dot.png"));
		head = headImgIcon.getImage();
	}

	private void initGame() {
		score = 0;
		// snake begins with three bits
		dots = 3;

		for (int z = 0; z < dots; z++) {
			x[z] = 50 - (z * 10);
			y[z] = 50;
		}

		spawnApple();

		timer  = new Timer(DELAY, this);
		timer.start();
	}

	private void resetGame(Snake parent) {
		if (!inGame) {
			score = 0;
			statusbar.setText(String.valueOf(score));
			dots = 3;

			for (int z = 0; z < dots; z++) {
				x[z] = 50 - (z * 10);
				y[z] = 50;
			}

			inGame = true; 
			isStarted = false;

			spawnApple();
			rightDirection = true;
			leftDirection = false;
			upDirection = false;
			downDirection = false;
			timer = new Timer(DELAY, this);
			timer.start();
		}
	}

	private void spawnApple() {
		int r = (int)(Math.random() * RAND_POS);
		apple_x = (r * DOT_SIZE);

		r = (int)(Math.random() * RAND_POS);
		apple_y = (r * DOT_SIZE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isStarted) {
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {
		if (!isStarted) {
			titleMenu(g);
			return;
		}

		if (inGame) {
			statusbar.setText(String.valueOf(score));

			g.drawImage(apple, apple_x, apple_y, this);

			for (int z = 0; z < dots; z++) {
				if (z == 0) {
					g.drawImage(head, x[z], y[z], this);
				} else {
					g.drawImage(ball, x[z], y[z], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}

	private void titleMenu(Graphics g) {
		String title = "~Snibbles~";
		String author = "By: sti & Wrolli";
		String instruct = "Press [ENTER] to Start";

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(title, (B_WIDTH - metr.stringWidth(title)) / 2, (B_HEIGHT / 3));
		g.drawString(author, (B_WIDTH - metr.stringWidth(author)) / 2, (B_HEIGHT / 3 + 20));
		g.drawString(instruct, (B_WIDTH - metr.stringWidth(instruct)) / 2, (B_HEIGHT / 3) + 50);
	}

	private void gameOver(Graphics g) {
		String msg = "GAME OVER";
		String cred = "Credits:";
		String stiCred = "Programmer - sti";
		String wrolliCred = "Game Designer - Wrolli";
		String instruct = "Press [ESC] to Restart";
		String playerCmd = "Please give us feedback! <3";
		String itchIoLink = "http://sti.itch.io/snibbles";

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 6));
		g.drawString(cred, (B_WIDTH - metr.stringWidth(cred)) / 2, (B_HEIGHT / 6) + 20);
		g.drawString(stiCred, (B_WIDTH - metr.stringWidth(stiCred)) / 2, (B_HEIGHT / 6) + 40);
		g.drawString(wrolliCred, (B_WIDTH - metr.stringWidth(wrolliCred)) / 2, (B_HEIGHT / 6) + 60);
		g.drawString(instruct, (B_WIDTH - metr.stringWidth(instruct)) / 2, (B_HEIGHT / 6) + 80);
		g.drawString(playerCmd, (B_WIDTH - metr.stringWidth(playerCmd)) / 2, (B_HEIGHT / 6) + 160);
		g.drawString(itchIoLink, (B_WIDTH - metr.stringWidth(itchIoLink)) / 2, (B_HEIGHT / 6) + 180);

		timer.stop();
	}

	private void checkApple() {
		// if collide with snake head, increase snake, & spawn another apple
		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			Sound.eat.play();
			dots++;
			score++;
			spawnApple();
		}
	}

	private void move() {
		for (int z = dots; z > 0; z--) {
			x[z] = x[z - 1];
			y[z] = y[z - 1];

		}

		if (leftDirection) {
			x[0] -= DOT_SIZE;
		}

		if (rightDirection) {
			x[0] += DOT_SIZE;
		}

		if (upDirection) {
			y[0] -= DOT_SIZE;
		}

		if (downDirection) {
			y[0] += DOT_SIZE;
		}
	}

	private void checkCollision() {
		for (int z = dots; z > 0; z--) {
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				inGame = false;
			}
		}


		if (x[0] < 0) {
			inGame = false;
		}

		if (y[0] < 0) {
			inGame = false;
		}

		if (x[0] >= B_WIDTH) {
			inGame = false;
		}

		if (y[0] >= B_HEIGHT) {
			inGame = false;
		}

		if (!inGame) {
			Sound.death.play();
			timer.stop();
		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}

			if (key == KeyEvent.VK_ENTER) {
				Sound.eat.play();
				isStarted = true;
			}

			if (key == KeyEvent.VK_ESCAPE) {
				resetGame(parent);
			}
		}

	}
}