package com.swayam.bhasha;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

import com.swayam.bhasha.oldview.impl.BhashaMainPanel;
import com.swayam.bhasha.oldview.impl.BhashaMenuBar;
import com.swayam.bhasha.oldview.impl.SinglePageContainer;
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

    @Override
    public void start() {
	showAppletAndHideImage();
    }

    private void showAppletAndHideImage() {
	JSObject window = JSObject.getWindow(this);
	String showAppletDiv = "document.getElementById('applet_div').style.visibility = 'visible';";
	window.eval(showAppletDiv);
	String hideImageDiv = "document.getElementById('image_div').style.visibility = 'hidden';";
	window.eval(hideImageDiv);
    }

    private void initComponents() {
	BhashaMainPanel bhashaMainPanel = new BhashaMainPanel(new SinglePageContainer(), new Locale[] { IndicPane.BANGLA_LOCALE, IndicPane.HINDI_LOCALE }, IndicPane.BANGLA_LOCALE);
	getContentPane().add(bhashaMainPanel, BorderLayout.CENTER);
	setJMenuBar(new BhashaMenuBar(bhashaMainPanel.getBhashaView()));
    }

}
