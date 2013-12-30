/*
 * BhashaDefaultView.java
 *
 * Created on February 22, 2004, 1:15 AM
 */

package com.swayam.bhasha.oldview.impl;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.swayam.bhasha.oldview.PageContainer;

@SuppressWarnings("serial")
public class BhashaDefaultFrame extends JFrame {

    private final BhashaMainPanel bhashaMainPanel;

    public BhashaDefaultFrame(PageContainer pageContainer, Locale[] supportedLanguages, Locale defaultLocale) {
	bhashaMainPanel = new BhashaMainPanel(this, pageContainer, supportedLanguages, defaultLocale);

	ImageIcon img = new ImageIcon(getClass().getResource("/images/BanglaLogo.jpg"));
	setIconImage(img.getImage());
	initComponents();
    }

    private void initComponents() {

	setTitle("Bhasha");
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent evt) {
		exitForm(evt);
	    }
	});
	getContentPane().add(bhashaMainPanel, BorderLayout.CENTER);

	setJMenuBar(new BhashaMenuBar(bhashaMainPanel.getBhashaView()));

	setBounds(2, 2, 800, 600);
    }

    private void exitForm(WindowEvent evt) {
	System.exit(0);
    }

}
