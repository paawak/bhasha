package com.swayam.bhasha.oldview.impl;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.swing.JApplet;

import com.swayam.bhasha.utils.page.IndicPane;

@SuppressWarnings("serial")
public class BhashaDefaultApplet extends JApplet {

    public BhashaDefaultApplet() {
	BhashaMainPanel bhashaMainPanel = new BhashaMainPanel(new SinglePageContainer(), new Locale[] { IndicPane.BANGLA_LOCALE, IndicPane.HINDI_LOCALE }, IndicPane.BANGLA_LOCALE);
	getContentPane().add(bhashaMainPanel, BorderLayout.CENTER);

	setJMenuBar(new BhashaMenuBar(bhashaMainPanel.getBhashaView()));

    }

}
