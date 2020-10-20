/*
 * BhashaLauncher.java
 *
 * Created on February 4, 2006, 12:22 PM
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
 *
 */

package com.swayam.bhasha;

import java.awt.Dimension;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;

import com.swayam.bhasha.oldview.impl.BhashaDefaultFrame;
import com.swayam.bhasha.oldview.impl.SinglePageContainer;
import com.swayam.bhasha.utils.page.IndicPane;

/**
 * 
 * @author paawak
 */
public class BhashaLauncher {

    public static final JFrame VIEW_INSTANCE;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BhashaLauncher.class);

    /**
     * This method sets the main frame to the required height and width and
     * centres it
     */
    public static void setScreen(JFrame frame) {
	Dimension scr = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	int scrWidth = scr.width, scrHeight = scr.height;
	// frame.setSize(scr.width, scr.height-40);
	// int frameWidth=800,frameHeight=600;
	int frameWidth = scrWidth, frameHeight = scrHeight - 40;
	frame.setSize(frameWidth, frameHeight);
	frame.setLocation((scrWidth - frameWidth) / 2, (scrHeight - frameHeight) / 2);
    }

    static {

	try {
	    // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"
	    // );
	    // UIManager.setLookAndFeel("javax.swing.plaf.multi.MultiLookAndFeel"
	    // );
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    // UIManager.setLookAndFeel(
	    // "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	    // UIManager.setLookAndFeel(
	    // "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
	    // UIManager.setLookAndFeel(
	    // "com.sun.java.swing.plaf.gtk.GTKLookAndFeel" );
	    // UIManager.setLookAndFeel(
	    // "it.unitn.ing.swing.plaf.macos.MacOSLookAndFeel" );
	    // UIManager.setLookAndFeel(
	    // "com.sun.java.swing.plaf.mac.MacLookAndFeel" );
	} catch (Exception e) {
	    log.warn("The desired luk-n-feel could not be loaded", e);
	}

	// try to read preference data
	// UserPreferences prefs = new UserPreferencesImpl().load();
	//
	// if (prefs == null) {
	// PreferenceSetterDialog prefDialog = new PreferenceSetterDialog(new
	// JFrame(), true);
	// prefDialog.setVisible(true);
	// prefs = new UserPreferencesImpl().load();
	// }
	//
	// if (prefs == null) {
	// JOptionPane.showMessageDialog(null, "Sorry! There was a problem.",
	// "Please try after sometime.", JOptionPane.ERROR_MESSAGE);
	// System.exit(0);
	// }
	//
	// Locale defLocale = prefs.getDefaultLanguage();

	VIEW_INSTANCE = new BhashaDefaultFrame(new SinglePageContainer(), new Locale[] { IndicPane.BANGLA_LOCALE, IndicPane.HINDI_LOCALE }, IndicPane.BANGLA_LOCALE);
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {

	String vers = System.getProperty("java.version");
	if (vers.compareTo("1.1.2") < 0) {
	    JOptionPane.showMessageDialog(VIEW_INSTANCE, "Sorry! To run this software, you need 1.4.2 or a higher version.", "A higher version of the Java Virtual Machine is needed.",
		    JOptionPane.ERROR_MESSAGE);
	    System.exit(1);
	}

	setScreen(VIEW_INSTANCE);
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		VIEW_INSTANCE.setVisible(true);
	    }
	});
    }

}
