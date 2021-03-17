package com.starbirth;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Univers extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 500;
	
	public static final int FPS = 30;
	
	private JFrame window;
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int pixels[] = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private boolean running;
	private Thread thread;
	private GalaxySpawner observableUni;
	private InputHandler input;
	private int x,y,xDir,yDir;
	
		public Univers() { 
			observableUni = new GalaxySpawner(WIDTH,HEIGHT);
			input = new InputHandler();
		}
		
		public void launchSimulation() { 
			window = new JFrame("Univers");
			window.setVisible(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setLocationRelativeTo(null);
			window.setResizable(false);
			setPreferredSize(new Dimension(WIDTH,HEIGHT));
			addKeyListener(input);
			window.add(this);
			window.pack();
		}
		
		private void playSound()
		{ 
			 new javafx.embed.swing.JFXPanel();
			Media media = new Media(new File("./sounds/interstellar.mp3").toURI().toString());
			MediaPlayer player = new MediaPlayer(media);
			player.play();
		}
		
		
		public void start() { 
			running = true;
			thread = new Thread(this,"Univers-Thread");
			thread.start();
		}
		
		public void stop() { 
			running = false;
		}
		
		public void update() { 
		input.update();
		if(input.up) 
			yDir--;
		else if(input.right) 
			xDir++;
		else if(input.down) 
			yDir++;
			
		else if(input.left)
			xDir--;
		
		else if(input.m) {
			observableUni.zoomOut();
			System.out.println("-");
		}
		
		else if(input.p)
			observableUni.zoomIn();
		
		x+=xDir;
		y+=yDir;
		}
		
		public void render() { 
			BufferStrategy bs = getBufferStrategy();
			if(bs == null) { 
				createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();
			observableUni.render(x,y);
			flipBuffer();
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			
			showInfo(g);
			g.dispose();
			bs.show();
		}
		
		
		private void showInfo(Graphics g) { 
			g.setColor(Color.GREEN);
			g.setFont(new Font("Verdana",Font.BOLD,15));
			g.drawString("XSpeed: "+xDir, 10, 20);
			g.drawString("XSpeed: "+yDir, 10, 40);
			g.drawString("Galaxies: "+observableUni.galaxies(),10,60);
		}
	@Override
	public void run() {
		launchSimulation();
		final double TARGET_FPS = FPS;
		double fps = 1000000000 / TARGET_FPS;
		double lastime = System.nanoTime();
		double now;
		double delta = 0;
		playSound();
		while(running) { 
			now = System.nanoTime();
			delta+=(now - lastime) / fps;
			lastime = now;
			if(delta >= 1) { 
				update();
				delta--;
			}
			 render();
			 requestFocus();
		}
	}
	
	
	
	
	private void flipBuffer() {
		 for(int y=0;y<HEIGHT;y++) {
			  for(int x=0;x<WIDTH;x++) { 
				  pixels[x + y * WIDTH] = observableUni.getPixels()[x + y * WIDTH];
			  }
		 }
	}
	
	public static void main(String[] args) {
		 Univers game = new Univers();
		 game.start();
	}

}
