package com.swayam.bhasha.oldview.impl;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.swayam.bhasha.utils.page.IndicPane;

@SuppressWarnings("serial")
public class BhashaDefaultApplet extends JApplet {

    public BhashaDefaultApplet() {

    }

    @Override
    public void init() {
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {
		@Override
		public void run() {
		    initComponents();
		}
	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    e.printStackTrace();
	}
    }

    private void initComponents() {
	BhashaMainPanel bhashaMainPanel = new BhashaMainPanel(new SinglePageContainer(), new Locale[] { IndicPane.BANGLA_LOCALE, IndicPane.HINDI_LOCALE }, IndicPane.BANGLA_LOCALE);
	getContentPane().add(bhashaMainPanel, BorderLayout.CENTER);
	setJMenuBar(new BhashaMenuBar(bhashaMainPanel.getBhashaView()));
    }

}
