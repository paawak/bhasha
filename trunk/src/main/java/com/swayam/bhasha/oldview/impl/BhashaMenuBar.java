package com.swayam.bhasha.oldview.impl;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import com.swayam.bhasha.help.Help;
import com.swayam.bhasha.oldview.BhashaView;
import com.swayam.bhasha.oldview.io.GenerateOutput;
import com.swayam.bhasha.prefs.KeyTable;
import com.swayam.bhasha.prefs.UserPreferencesImpl;
import com.swayam.bhasha.utils.page.IndicPane;

@SuppressWarnings("serial")
public class BhashaMenuBar extends JMenuBar {

    private final BhashaView bhashaView;

    public BhashaMenuBar(BhashaView bhashaView) {
	this.bhashaView = bhashaView;
	initComponents();
    }

    private void initComponents() {
	jMnFile = new JMenu();
	jMnNew = new JMenuItem();
	jSeparator1 = new JSeparator();
	jMnOpen = new JMenuItem();
	jSeparator2 = new JSeparator();
	jMnSave = new JMenu();
	jMnXML = new JMenuItem();
	jSeparator6 = new JSeparator();
	jMnHTML = new JMenuItem();
	jSeparator7 = new JSeparator();
	jMnJPEG = new JMenuItem();
	jSeparator8 = new JSeparator();
	jMnRTF = new JMenuItem();
	jSeparator9 = new JSeparator();
	jMnPDF = new JMenuItem();
	jMnHelp = new JMenu();
	jMnBanglaHelp = new JMenuItem();
	jSeparator3 = new JSeparator();
	jMnAbout = new JMenuItem();
	jMnSettings = new JMenu();
	jMnLbLanguage = new JMenu();
	jMnBangla = new JMenuItem();
	jMnHindi = new JMenuItem();
	jMnEnglish = new JMenuItem();
	jSeparator4 = new JSeparator();
	jMnKeyboard = new JMenuItem();
	jSeparator5 = new JSeparator();
	jMnResetSettings = new JMenuItem();

	jMnFile.setMnemonic('f');
	jMnFile.setText("File");
	jMnFile.setFont(new java.awt.Font("Verdana", 1, 14));

	jMnNew.setMnemonic('n');
	jMnNew.setText("New");
	jMnNew.addKeyListener(new java.awt.event.KeyAdapter() {
	    @Override
	    public void keyReleased(java.awt.event.KeyEvent evt) {
		jMnNewKeyReleased(evt);
	    }
	});
	jMnNew.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnNewMouseReleased(evt);
	    }
	});
	jMnFile.add(jMnNew);
	jMnFile.add(jSeparator1);

	jMnOpen.setMnemonic('o');
	jMnOpen.setText("Open");
	jMnOpen.addKeyListener(new java.awt.event.KeyAdapter() {
	    @Override
	    public void keyReleased(java.awt.event.KeyEvent evt) {
		jMnOpenKeyReleased(evt);
	    }
	});
	jMnOpen.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnOpenMouseReleased(evt);
	    }
	});
	jMnFile.add(jMnOpen);
	jMnFile.add(jSeparator2);

	jMnSave.setText("Save");

	jMnXML.setText("XML");
	jMnXML.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnXMLMouseReleased(evt);
	    }
	});
	jMnSave.add(jMnXML);
	jMnSave.add(jSeparator6);

	jMnHTML.setText("HTML");
	jMnHTML.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnHTMLMouseReleased(evt);
	    }
	});
	jMnSave.add(jMnHTML);
	jMnSave.add(jSeparator7);

	jMnJPEG.setText("Image(JPEG)");
	jMnJPEG.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnJPEGMouseReleased(evt);
	    }
	});
	jMnSave.add(jMnJPEG);
	jMnSave.add(jSeparator8);

	jMnRTF.setText("RTF");
	jMnRTF.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnRTFMouseReleased(evt);
	    }
	});
	jMnSave.add(jMnRTF);
	jMnSave.add(jSeparator9);

	jMnPDF.setText("PDF");
	jMnPDF.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnPDFMouseReleased(evt);
	    }
	});
	jMnSave.add(jMnPDF);

	jMnFile.add(jMnSave);

	add(jMnFile);

	jMnHelp.setMnemonic('h');
	jMnHelp.setText("Help");
	jMnHelp.setFont(new java.awt.Font("Verdana", 1, 14));

	jMnBanglaHelp.setMnemonic('t');
	jMnBanglaHelp.setText("How to Type");
	jMnBanglaHelp.addKeyListener(new java.awt.event.KeyAdapter() {
	    @Override
	    public void keyReleased(java.awt.event.KeyEvent evt) {
		jMnBanglaHelpKeyReleased(evt);
	    }
	});
	jMnBanglaHelp.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnBanglaHelpMouseReleased(evt);
	    }
	});
	jMnHelp.add(jMnBanglaHelp);
	jMnHelp.add(jSeparator3);

	jMnAbout.setText("About");
	jMnAbout.addKeyListener(new java.awt.event.KeyAdapter() {
	    @Override
	    public void keyReleased(java.awt.event.KeyEvent evt) {
		jMnAboutKeyReleased(evt);
	    }
	});
	jMnAbout.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnAboutMouseReleased(evt);
	    }
	});
	jMnHelp.add(jMnAbout);

	add(jMnHelp);

	jMnSettings.setMnemonic('t');
	jMnSettings.setText("Settings");
	jMnSettings.setFont(new java.awt.Font("Verdana", 1, 14));

	jMnLbLanguage.setMnemonic('l');
	jMnLbLanguage.setText("Display Language");

	jMnBangla.setMnemonic('b');
	jMnBangla.setText("Bangla");
	jMnBangla.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnBanglaMouseReleased(evt);
	    }
	});
	jMnLbLanguage.add(jMnBangla);

	jMnHindi.setMnemonic('h');
	jMnHindi.setText("Hindi");
	jMnHindi.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnHindiMouseReleased(evt);
	    }
	});
	jMnLbLanguage.add(jMnHindi);

	jMnEnglish.setMnemonic('e');
	jMnEnglish.setText("English");
	jMnEnglish.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnEnglishMouseReleased(evt);
	    }
	});
	jMnLbLanguage.add(jMnEnglish);

	jMnSettings.add(jMnLbLanguage);
	jMnSettings.add(jSeparator4);

	jMnKeyboard.setText("Customise Keyboard");
	jMnKeyboard.setAutoscrolls(true);
	jMnKeyboard.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnKeyboardMouseReleased(evt);
	    }
	});
	jMnSettings.add(jMnKeyboard);
	jMnSettings.add(jSeparator5);

	jMnResetSettings.setText("Reset Settings");
	jMnResetSettings.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		jMnResetSettingsMouseReleased(evt);
	    }
	});
	jMnSettings.add(jMnResetSettings);

	add(jMnSettings);

    }

    private void jMnPDFMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnPDFMouseReleased
	generateOutput(GenerateOutput.PDF_FORMAT);
    }// GEN-LAST:event_jMnPDFMouseReleased

    private void jMnRTFMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnRTFMouseReleased
	generateOutput(GenerateOutput.RTF_FORMAT);
    }// GEN-LAST:event_jMnRTFMouseReleased

    private void jMnJPEGMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnJPEGMouseReleased
	generateOutput(GenerateOutput.IMAGE_JPEG_FORMAT);
    }// GEN-LAST:event_jMnJPEGMouseReleased

    private void jMnHTMLMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnHTMLMouseReleased
	generateOutput(GenerateOutput.HTML_FORMAT);
    }// GEN-LAST:event_jMnHTMLMouseReleased

    private void jMnXMLMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnXMLMouseReleased
	generateOutput(GenerateOutput.XML_FORMAT);
    }// GEN-LAST:event_jMnXMLMouseReleased

    private void jMnResetSettingsMouseReleased(java.awt.event.MouseEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_jMnResetSettingsMouseReleased
	if (new UserPreferencesImpl().reset()) {
	    JOptionPane.showMessageDialog(this,
		    "User settings deleted successfully", "Deleted",
		    JOptionPane.INFORMATION_MESSAGE);
	} else {
	    JOptionPane.showMessageDialog(this, "Could not delete settings",
		    "Sorry", JOptionPane.ERROR_MESSAGE);
	}

    }// GEN-LAST:event_jMnResetSettingsMouseReleased

    private void jMnKeyboardMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST
	// :
	// event_jMnKeyboardMouseReleased
	new KeyTable(bhashaView.getMainWindow()).setVisible(true);
	// reload the charMap in case the user has changed settings
	bhashaView.refillFontFamilyCombo();
    }// GEN-LAST:event_jMnKeyboardMouseReleased

    private void jMnAboutKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:
	// event_jMnAboutKeyReleased
	showAbout();
    }// GEN-LAST:event_jMnAboutKeyReleased

    private void jMnAboutMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST:
	// event_jMnAboutMouseReleased
	showAbout();
    }// GEN-LAST:event_jMnAboutMouseReleased

    private void jMnBanglaHelpMouseReleased(java.awt.event.MouseEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_jMnBanglaHelpMouseReleased
	showBanglaHelp();
    }// GEN-LAST:event_jMnBanglaHelpMouseReleased

    private void jMnBanglaHelpKeyReleased(java.awt.event.KeyEvent evt) {// GEN-
	// FIRST
	// :
	// event_jMnBanglaHelpKeyReleased
	showBanglaHelp();
    }// GEN-LAST:event_jMnBanglaHelpKeyReleased

    private void jMnEnglishMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST
	// :
	// event_jMnEnglishMouseReleased
	bhashaView.setCurrentLocale(IndicPane.ENGLISH_LOCALE);
	// setLabelsByLocale();
    }// GEN-LAST:event_jMnEnglishMouseReleased

    private void jMnHindiMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST:
	// event_jMnHindiMouseReleased
	bhashaView.setCurrentLocale(IndicPane.HINDI_LOCALE);
	// setLabelsByLocale();
    }// GEN-LAST:event_jMnHindiMouseReleased

    private void jMnBanglaMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST
	// :
	// event_jMnBanglaMouseReleased
	bhashaView.setCurrentLocale(IndicPane.BANGLA_LOCALE);
	// setLabelsByLocale();
    }// GEN-LAST:event_jMnBanglaMouseReleased

    private void jMnOpenMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnOpenMouseReleased
	bhashaView.open();
    }// GEN-LAST:event_jMnOpenMouseReleased

    private void jMnOpenKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:
	// event_jMnOpenKeyReleased
	bhashaView.open();
    }// GEN-LAST:event_jMnOpenKeyReleased

    private void jMnNewMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_jMnNewMouseReleased
	bhashaView.newPage();
    }// GEN-LAST:event_jMnNewMouseReleased

    private void jMnNewKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:
	// event_jMnNewKeyReleased
	bhashaView.newPage();
    }// GEN-LAST:event_jMnNewKeyReleased

    /**
     * method to display Help Frame
     */
    private void showBanglaHelp() {
	Help help = new Help("BanglaHelp");
	help.setSize(550, 800);
	// help.setResizable(false);
	help.setVisible(true);
    }

    private void showAbout() {
	Help help = new Help("About");
	help.setSize(600, 300);
	help.setVisible(true);
    }

    private void generateOutput(final String type) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		GenerateOutput generateoutput = new GenerateOutput(true,
			bhashaView.getPageContainer(), type);
		generateoutput.setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuItem jMnAbout;
    private JMenuItem jMnBangla;
    private JMenuItem jMnBanglaHelp;
    private JMenuItem jMnEnglish;
    private JMenu jMnFile;
    private JMenuItem jMnHTML;
    private JMenu jMnHelp;
    private JMenuItem jMnHindi;
    private JMenuItem jMnJPEG;
    private JMenuItem jMnKeyboard;
    private JMenu jMnLbLanguage;
    private JMenuItem jMnNew;
    private JMenuItem jMnOpen;
    private JMenuItem jMnPDF;
    private JMenuItem jMnRTF;
    private JMenuItem jMnResetSettings;
    private JMenu jMnSave;
    private JMenu jMnSettings;
    private JMenuItem jMnXML;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JSeparator jSeparator7;
    private JSeparator jSeparator8;
    private JSeparator jSeparator9;

}
