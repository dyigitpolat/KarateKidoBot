import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


import javax.swing.*;

public class BotDriver_old {
	static BufferedImage b;
	public static Color getPixelColor(int x, int y) {
		int rgb = b.getRGB(x, y);
		return new Color(rgb);
	}

	public static void mainx(String[] args) {
		Robot r;
		int state = 0;
		int valid = 1;
		int counter = 0;
		int state_changed = 1;
		try {
			r = new Robot();

			Instant t1, t2, prev1, prev2, t3;
			prev1 = Instant.now();
			prev2 = Instant.now();
			while( true) { 
				t1 = Instant.now();
				b = r.createScreenCapture(new Rectangle(1920,1080));
				
				Color p = getPixelColor(920, 623);
				Color q = getPixelColor(994, 623);
				Color v = getPixelColor(1000, 133);
				
				if( state == 0 && Duration.between(prev2, Instant.now()).toMillis() > 200) {
					
					if( p.getRed() == 163 &&
						p.getGreen() == 90 &&
						p.getBlue() == 47) {
						state = 1;
						counter = 0;
						prev1 = Instant.now();
					}
				}
				
				if( state == 1 && Duration.between(prev1, Instant.now()).toMillis() > 200) {
					if( q.getRed() == 163 &&
						q.getGreen() == 90 &&
						q.getBlue() == 47) {
						state = 0;
						counter = 0;
						prev2 = Instant.now();
					}
				}
				
				
				if( v.getRed() == 44 &&
					v.getGreen() == 55 &&
					v.getBlue() == 64) {
					valid = 1;
				} else {
					valid = 0;
				}
				

				
				
				
				if( state == 1 && valid == 1) {
					if( counter % 4 == 0) {
						r.keyPress( KeyEvent.VK_RIGHT);
						r.keyRelease( KeyEvent.VK_RIGHT);
						System.out.println("right");
					}
				} else if ( state == 0 && valid == 1){
					if( counter % 4 == 0) {
						r.keyPress( KeyEvent.VK_LEFT);
						r.keyRelease( KeyEvent.VK_LEFT);
						System.out.println("left");
					}
				} else {
					r.keyRelease( KeyEvent.VK_RIGHT);
					r.keyRelease( KeyEvent.VK_LEFT);
				}
				
				t2 = Instant.now();
				long ms = Duration.between(t1, t2).toMillis();
				TimeUnit.MILLISECONDS.sleep(39-ms);
				//System.out.println(counter);
				counter++;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
