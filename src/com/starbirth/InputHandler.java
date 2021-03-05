package com.starbirth;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{

	private boolean keys[] = new boolean[2048];
	public boolean up,right,down,left;
	
	
		public InputHandler() { 
			
		}
		
		public void update() { 
			up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_Z];
			right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
			down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
			left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_Q];
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		public void testKeys() { 
			if(up) {System.out.println("up");}
			if(right) {System.out.println("right");}
			if(down) {System.out.println("down");}
			if(left) {System.out.println("left");}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			keys[e.getKeyCode()] = true;
		}


		@Override
		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}
		
		
}
