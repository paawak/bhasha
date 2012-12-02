/*
 * AnimationFrame.java
 *
 * Created on Sep 2, 2006, 11:50:39 PM
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

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Displays a set of images in a given directory according to
 * the last modified date of the image.
 * To be used in conjunction with <code>ScreenCapture</code>,
 * which generates the screen-shots in the first place.
 * 
 * @author paawak
 */
public class AnimationFrame extends JFrame {
	
	private JLabel label;
	
	public AnimationFrame() {
		initComponents();
	}
	
	private void initComponents() {
		
		label = new JLabel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(label, BorderLayout.CENTER);
		pack();
		setBounds(0, 0, 700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void animate() {
		
		Thread animate = new Thread() {
			
			public void run() {
				
				List<File> files = getImageFiles(ScreenCapture.SAVE_DIR);
				
				for (File file : files) {
					
					System.out.println("AnimationFrame.animate() " + file.getName());
					
					try {
						ImageIcon icon = new ImageIcon(file.toURL());
						label.setIcon(icon);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					
					try {
						Thread.sleep(ScreenCapture.CAPTURE_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		};
		
		animate.start();
		
	}
	
	private List<File> getImageFiles(String imageDir) {
		
		File picDir = new File(imageDir);
		
		Comparator<File> lastModifiedComparator = new Comparator<File>() {

			public int compare(File file1, File file2) {
				
				return (int)(file1.lastModified() - file2.lastModified());
				
			}
			
		};
		
		File[] files = picDir.listFiles(new FileFilter() {

			public boolean accept(File file) {
				
				if (file.isDirectory()) {
					return false;
				}
				
				String fileName = file.getName().toLowerCase();
				
				if (fileName.indexOf(".jpg") != -1
						|| fileName.indexOf(".jpeg") != -1
						|| fileName.indexOf(".gif") != -1
						|| fileName.indexOf(".png") != -1) {
					return true;
				}
				
				return false;
			}
			
		});
		
		List<File> fileList = new ArrayList<File>();
		Collections.addAll(fileList, files);
		Collections.sort(fileList, lastModifiedComparator);
		
		return Collections.unmodifiableList(fileList);
		
	}

	public static void main(String[] args) {
		
		Runnable showFrame = new Runnable() {

			public void run() {

				AnimationFrame test = new AnimationFrame();
				test.setVisible(true);
				test.animate();
				
			}
			
		};
		
		SwingUtilities.invokeLater(showFrame);
		
	}

}

