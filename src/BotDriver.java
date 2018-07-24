import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


import javax.swing.*;

public class BotDriver {
	static BufferedImage b;
	
	public static boolean isWood(Color c) {
		return (c.getRed() == 163 &&
				c.getGreen() == 90 &&
				c.getBlue() == 47);
	}
	
	public static int checkFast(int state) {
		int fast = 1;
		if( state == 0) {
			for( int i = -50; i < 100; i++) {
				if( isWood( getPixelColor( 920, 486 + i))) {
					fast = 0;
					return fast;
				}
			}
		}
		
		if( state == 1) {
			for( int i = -50; i < 100; i++) {
				if( isWood( getPixelColor( 994, 486 + i))) {
					fast = 0;
					return fast;
				}
			}
		}
		
		return fast;
	}
	
	
	public static int checkDanger(int state) {
		int danger = 0;
		if( state == 0) {
			for( int i = -40; i < 0; i++) {
				if( isWood( getPixelColor( 920, 623 + i))) {
					danger = 1;
					return danger;
				}
			}
		}
		
		if( state == 1) {
			for( int i = -40; i < 0; i++) {
				if( isWood( getPixelColor( 994, 623 + i))) {
					danger = 1;
					return danger;
				}
			}
		}
		
		return danger;
	}
	
	public static Color getPixelColor(int x, int y) {
		int rgb = b.getRGB(x, y);
		return new Color(rgb);
	}

	public static void main(String[] args) {
		Robot r;
		int state = 0;
		int valid = 1;
		int counter = 0;
		int state_changed = 0;
		int a = 0;
		int fast = 0;
		try {
			r = new Robot();

			Instant t1, t2, prev1, prev2, t3, press;
			prev1 = Instant.now();
			prev2 = Instant.now();
			press = Instant.now();
			while( true) { 
				t1 = Instant.now();
				b = r.createScreenCapture(new Rectangle(1920,1080));
				
				Color p = getPixelColor(920, 623);
				Color q = getPixelColor(994, 623);
				Color v = getPixelColor(1000, 133);
				fast = checkFast(state);
				if( state == 0) {
					
					if( checkDanger(state) == 1 && 
						Duration.between(prev2, Instant.now()).toMillis() > 250) {
						state = 1;
						counter = 0;
						state_changed = 1;
						prev1 = Instant.now();
						fast = 0;
					}
				}
				
				if( state == 1) {
					
					if( checkDanger(state) == 1 && 
						Duration.between(prev1, Instant.now()).toMillis() > 250) {
						state = 0;
						counter = 0;
						prev2 = Instant.now();
						state_changed = 1;
						fast = 0;
					}
				}
				
				
				if( v.getRed() == 44 &&
					v.getGreen() == 55 &&
					v.getBlue() == 64) {
					valid = 1;
				} else {
					valid = 0;
				}

				t2 = Instant.now();
				long ms = Duration.between(t1, t2).toMillis();
				
				int period = 130;
				if( fast == 1) period = 120;
				//if( fast == 2) period = 100; TODO:
				
				if( state == 1 && valid == 1) {
					if( Duration.between(press, Instant.now()).toMillis() > period) {
						r.keyPress( KeyEvent.VK_RIGHT);
						TimeUnit.MILLISECONDS.sleep(20);
						r.keyRelease( KeyEvent.VK_RIGHT);
						System.out.println("right");
						press = Instant.now();
					}
				} else if ( state == 0 && valid == 1){
					if( Duration.between(press, Instant.now()).toMillis() > period) {
						r.keyPress( KeyEvent.VK_LEFT);
						TimeUnit.MILLISECONDS.sleep(20);
						r.keyRelease( KeyEvent.VK_LEFT);
						System.out.println("left");
						press = Instant.now();
					}
				} else {
					r.keyRelease( KeyEvent.VK_RIGHT);
					r.keyRelease( KeyEvent.VK_LEFT);
				}
				
				//TimeUnit.MILLISECONDS.sleep(10-ms >= 0 ? 10-ms : 0);
				//System.out.println(ms);
				counter++;
				state_changed = 0;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
