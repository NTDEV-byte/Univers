package com.starbirth;

import java.util.Random;

public class GalaxySpawner {

	public static final int UNIVERS_SIZE = 256;
	public static final int UNIVERS_LIMIT = 256 - 1;
	public static final int GALAXY_RATE_BIRTH = 150;
	public static final int GALAXY_PROLIFERATION_LIMIT = 200;
	public static final int DARK_MATTER = 0x0;
	public static final int UNIVERS[] = new int[UNIVERS_SIZE * UNIVERS_SIZE];
	
	private int width;
	private int height;
	private int pixels[];
	private Random random = new Random();
	private int xa,ya,timer;
	
			public GalaxySpawner(int width,int height) {
				 this.width = width;
				 this.height = height;
				 this.pixels = new int[width * height];
			}
			
			public void render(int xOffs,int yOffs) { 
				int yp = 0,xp = 0;
				if(timer < 7500) timer++; else timer = 0;
				if(timer % GALAXY_RATE_BIRTH == 0) { 
					xa = random.nextInt(UNIVERS_SIZE);
					ya = random.nextInt(UNIVERS_SIZE);
					UNIVERS[xa + ya * UNIVERS_SIZE] = random.nextInt(0xffffff);
				}
				for(int y=0;y<height;y++) { 
					yp = y + yOffs;
					for(int x=0;x<width;x++) {
						 xp = x + xOffs;
						if(xa < 0 || xa >= width - 1 || ya < 0 || ya >= height - 1) { 
							  xa = random.nextInt(UNIVERS_SIZE);
							  ya = random.nextInt(UNIVERS_SIZE);
						 }
						
						 pixels[x + y * width] = UNIVERS[(xp & UNIVERS_LIMIT) + (yp & UNIVERS_LIMIT) * UNIVERS_SIZE];
					}
				}
				
			}
			
			public void travelMilkyWay(){ 
				if(galaxies() >= GALAXY_PROLIFERATION_LIMIT) { 
					
				}
			}
			
			private int galaxies() { 
				int total = 0;
				for(int i=0;i<pixels.length;i++) {
					 if(pixels[i] != DARK_MATTER) { 
						 total++;
					 }
				}
				return total;
			}
			public int getWidth() {
				return width;
			}

			public void setWidth(int width) {
				this.width = width;
			}

			public int getHeight() {
				return height;
			}

			public void setHeight(int height) {
				this.height = height;
			}

			public int[] getPixels() {
				return pixels;
			}

			public void setPixels(int[] pixels) {
				this.pixels = pixels;
			}

}
