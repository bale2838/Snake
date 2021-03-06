package com.bale.snake.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static final Sound eat = new Sound("/test.wav");
	public static final Sound death = new Sound("/death.wav");

	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}