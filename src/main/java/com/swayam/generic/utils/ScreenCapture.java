/*
 * ScreenCapture.java
 *
 * Created on Sep 2, 2006, 11:17:33 PM
 *
 * Copyright (c) 2002 - 2006 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 *
 */

package com.swayam.generic.utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Captures screen after a given time interval and stores it
 * in a directory as a JPEG image. It is useful for giving demos etc.
 * Use this with <code>AnimationFrame</code> to display the
 * captured images.
 * 
 * @author paawak
 */
public class ScreenCapture extends Thread {
	
	static final String SAVE_DIR = "c:/temp/";
	
	static final int CAPTURE_INTERVAL = 80;
	
	private static int frameCount = 0;
	
	private final Dimension SCREEN_DIM = Toolkit.getDefaultToolkit().getScreenSize(); 
	
	public void run() {
		
		while (true) {
			
			try {
				captureFrame();
			} catch (AWTException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(CAPTURE_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			
		}
		
	}
	
	private synchronized void captureFrame() throws AWTException, IOException {
		
		Robot robot = new Robot();
		BufferedImage im = robot.createScreenCapture(new Rectangle(0, 0, SCREEN_DIM.width, SCREEN_DIM.height));
		ImageIO.write(im, "jpg", new File(SAVE_DIR + frameCount++ + ".jpg"));
		
		
	}

	public static void main(String[] args) {
		
		new ScreenCapture().start();

	}

}

