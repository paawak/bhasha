/*
 * BhashaDefaultView.java
 *
 * Created on February 22, 2004, 1:15 AM
 */

package com.swayam.bhasha.oldview.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.text.StyledEditorKit;

import org.apache.log4j.Logger;

import com.swayam.bhasha.engine.io.parsers.DocParser;
import com.swayam.bhasha.engine.io.parsers.XHtmlDocParser;
import com.swayam.bhasha.oldview.BhashaView;
import com.swayam.bhasha.oldview.PageContainer;
import com.swayam.bhasha.oldview.io.GenerateOutput;
import com.swayam.bhasha.utils.ColoredSquare;
import com.swayam.bhasha.utils.Filter;
import com.swayam.bhasha.utils.PropertyFileUtils;
import com.swayam.bhasha.utils.StyleActionFactory;
import com.swayam.bhasha.utils.page.IndicPane;
import com.swayam.generic.utils.FontLoader;
import com.swayam.generic.utils.JComboUtils;

public class BhashaDefaultPanelView extends JPanel implements BhashaView {

    private static final long serialVersionUID = 6840005430680654312L;

    private static final Logger logger = Logger
	    .getLogger(BhashaDefaultPanelView.class);

    private final Window window;

    static {
	// for debug only
	logger.info("*******Bangla fonts:\n" + FontLoader.getBanglaFonts());
	logger.info("*******Hindi fonts:\n" + FontLoader.getHindiFonts());
	logger.info("*******English fonts:\n" + FontLoader.getEnglishFonts());
    }

    public BhashaDefaultPanelView(Window window, PageContainer pageContainer,
	    Locale[] supportedLanguages, Locale defaultLocale) {

	this.window = window;
	setPageContainer(pageContainer);

	initComponents();

	customiseLanguageSupported(supportedLanguages, defaultLocale);

	// start auto save thread
	save = new Thread() {
	    @Override
	    public void run() {
		while (true) {
		    try {
			Thread.sleep(saveInterval * saveIntervalUnit);
		    } catch (InterruptedException e) {
		    }
		    if (autoSave && chosenFile != null) {
			autoSaveFile(chosenFile);
		    }
		}
	    }
	};

	save.setPriority(Thread.MIN_PRIORITY + 2);

	save.start();
	currentLocale = IndicPane.BANGLA_LOCALE;
	viewPnl.setLayout(new BorderLayout());
	viewPnl.add(pageContainer.getPageContainerView());
	newPage();

    }

    private void setPage(IndicPane page) {
	txtPnPad = page;
	// txtPnPad.initStyle(" ");
	initStyleButtons();
	initFontSizeCombo();
	initFontColourCombo();
	refillFontFamilyCombo();
    }

    public void setPageContainer(PageContainer pageContainer) {
	this.pageContainer = pageContainer;
    }

    public PageContainer getPageContainer() {
	return pageContainer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
	java.awt.GridBagConstraints gridBagConstraints;

	btnGrpLanguage = new javax.swing.ButtonGroup();
	btnGrpAlignment = new javax.swing.ButtonGroup();
	pnlMainControl = new javax.swing.JPanel();
	pnlLanguage = new javax.swing.JPanel();
	rdBtBangla = new javax.swing.JRadioButton();
	rdBtHindi = new javax.swing.JRadioButton();
	rdBtEnglish = new javax.swing.JRadioButton();
	chbAutoSave = new javax.swing.JCheckBox();
	pnlAutoSave = new javax.swing.JPanel();
	lbSaveInterval = new javax.swing.JLabel();
	txtSaveInterval = new javax.swing.JTextField();
	cbSaveIntervalUnit = new JComboBox(new String[] { "Sec", "Min" });
	pnlStyleControl = new javax.swing.JPanel();
	btBold = new javax.swing.JToggleButton();
	btItalic = new javax.swing.JToggleButton();
	btUnderline = new javax.swing.JToggleButton();
	btAlignLeft = new javax.swing.JToggleButton();
	btAlignCenter = new javax.swing.JToggleButton();
	btAlignRight = new javax.swing.JToggleButton();
	cbFontFamily = new javax.swing.JComboBox();
	cbFontColor = new javax.swing.JComboBox();
	cbFontSize = new javax.swing.JComboBox();
	viewPnl = new javax.swing.JPanel();

	setLayout(new java.awt.GridBagLayout());

	pnlMainControl.setMaximumSize(new java.awt.Dimension(32767, 90));
	pnlMainControl.setMinimumSize(new java.awt.Dimension(0, 90));
	pnlMainControl.setPreferredSize(new java.awt.Dimension(736, 90));

	pnlLanguage.setBorder(javax.swing.BorderFactory.createTitledBorder(
		javax.swing.BorderFactory.createEtchedBorder(),
		"Current Language:"));
	pnlLanguage.setMaximumSize(new java.awt.Dimension(350, 35));
	pnlLanguage.setMinimumSize(new java.awt.Dimension(193, 35));
	pnlLanguage.setPreferredSize(new java.awt.Dimension(193, 35));
	pnlLanguage.setLayout(new java.awt.GridBagLayout());

	btnGrpLanguage.add(rdBtBangla);
	rdBtBangla.setFont(new java.awt.Font("SolaimanLipi", 0, 18)); // NOI18N
	rdBtBangla.setMnemonic('b');
	rdBtBangla.setSelected(true);
	rdBtBangla.setText("\u09ac\u09be\u0999\u09cd\u0997\u09b2\u09be");
	rdBtBangla.setMaximumSize(new java.awt.Dimension(300, 35));
	rdBtBangla.setMinimumSize(new java.awt.Dimension(65, 35));
	rdBtBangla.setPreferredSize(new java.awt.Dimension(300, 35));
	rdBtBangla.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		rdBtBanglaActionPerformed(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.weightx = 0.1;
	pnlLanguage.add(rdBtBangla, gridBagConstraints);

	btnGrpLanguage.add(rdBtHindi);
	rdBtHindi.setFont(new java.awt.Font("Mangal", 1, 14));
	rdBtHindi.setMnemonic('d');
	rdBtHindi.setText("\u0926\u0947\u0935\u0928\u093e\u0917\u0930\u093f");
	rdBtHindi.setMaximumSize(new java.awt.Dimension(110, 33));
	rdBtHindi.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		rdBtHindiActionPerformed(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.weightx = 0.1;
	pnlLanguage.add(rdBtHindi, gridBagConstraints);

	btnGrpLanguage.add(rdBtEnglish);
	rdBtEnglish.setFont(new java.awt.Font("Arial", 1, 14));
	rdBtEnglish.setMnemonic('e');
	java.util.ResourceBundle bundle = java.util.ResourceBundle
		.getBundle("props/Display"); // NOI18N
	rdBtEnglish.setText(bundle.getString("rdBtEnglish")); // NOI18N
	rdBtEnglish.setMaximumSize(new java.awt.Dimension(110, 25));
	rdBtEnglish.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		rdBtEnglishActionPerformed(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.weightx = 0.1;
	pnlLanguage.add(rdBtEnglish, gridBagConstraints);

	chbAutoSave.setMnemonic('a');
	chbAutoSave.setText("Auto Save");
	chbAutoSave.addItemListener(new java.awt.event.ItemListener() {
	    public void itemStateChanged(java.awt.event.ItemEvent evt) {
		chbAutoSaveItemStateChanged(evt);
	    }
	});
	chbAutoSave.addKeyListener(new java.awt.event.KeyAdapter() {
	    @Override
	    public void keyReleased(java.awt.event.KeyEvent evt) {
		chbAutoSaveKeyReleased(evt);
	    }
	});

	pnlAutoSave.setMaximumSize(new java.awt.Dimension(300, 90));
	pnlAutoSave.setMinimumSize(new java.awt.Dimension(150, 28));
	pnlAutoSave.setPreferredSize(new java.awt.Dimension(150, 28));
	pnlAutoSave.setVisible(false);

	lbSaveInterval.setText("Interval:");
	pnlAutoSave.add(lbSaveInterval);

	txtSaveInterval.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
	txtSaveInterval.setText("5");
	txtSaveInterval.setMaximumSize(new java.awt.Dimension(100, 20));
	txtSaveInterval.setPreferredSize(new java.awt.Dimension(20, 20));
	txtSaveInterval.addFocusListener(new java.awt.event.FocusAdapter() {
	    @Override
	    public void focusLost(java.awt.event.FocusEvent evt) {
		txtSaveIntervalFocusLost(evt);
	    }
	});
	pnlAutoSave.add(txtSaveInterval);

	cbSaveIntervalUnit.setMaximumSize(new java.awt.Dimension(31, 25));
	cbSaveIntervalUnit.setPreferredSize(new java.awt.Dimension(60, 20));
	cbSaveIntervalUnit.addItemListener(new java.awt.event.ItemListener() {
	    public void itemStateChanged(java.awt.event.ItemEvent evt) {
		cbSaveIntervalUnitItemStateChanged(evt);
	    }
	});
	pnlAutoSave.add(cbSaveIntervalUnit);

	pnlStyleControl.setMaximumSize(new java.awt.Dimension(1500, 25));
	pnlStyleControl.setMinimumSize(new java.awt.Dimension(551, 25));
	pnlStyleControl.setPreferredSize(new java.awt.Dimension(792, 25));
	pnlStyleControl.setLayout(new java.awt.GridBagLayout());

	btBold.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		"/images/bold.gif"))); // NOI18N
	btBold.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		btBoldMouseReleased(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
	pnlStyleControl.add(btBold, gridBagConstraints);

	btItalic.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		"/images/italic.gif"))); // NOI18N
	btItalic.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		btItalicMouseReleased(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 1;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	pnlStyleControl.add(btItalic, gridBagConstraints);

	btUnderline.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		"/images/underline.gif"))); // NOI18N
	btUnderline.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		btUnderlineMouseReleased(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 2;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	pnlStyleControl.add(btUnderline, gridBagConstraints);

	btnGrpAlignment.add(btAlignLeft);
	btAlignLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		"/images/left.gif"))); // NOI18N
	btAlignLeft.setSelected(true);
	btAlignLeft.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		btAlignLeftMouseReleased(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 3;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
	pnlStyleControl.add(btAlignLeft, gridBagConstraints);

	btnGrpAlignment.add(btAlignCenter);
	btAlignCenter.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		"/images/center.gif"))); // NOI18N
	btAlignCenter.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		btAlignCenterMouseReleased(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 4;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	pnlStyleControl.add(btAlignCenter, gridBagConstraints);

	btnGrpAlignment.add(btAlignRight);
	btAlignRight.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		"/images/right.gif"))); // NOI18N
	btAlignRight.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		btAlignRightMouseReleased(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 5;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
	pnlStyleControl.add(btAlignRight, gridBagConstraints);

	cbFontFamily.setMinimumSize(new java.awt.Dimension(200, 24));
	cbFontFamily.setPreferredSize(new java.awt.Dimension(200, 24));
	cbFontFamily.addItemListener(new java.awt.event.ItemListener() {
	    public void itemStateChanged(java.awt.event.ItemEvent evt) {
		cbFontFamilyItemStateChanged(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 6;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
	gridBagConstraints.weightx = 1.0;
	gridBagConstraints.weighty = 0.3;
	gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
	pnlStyleControl.add(cbFontFamily, gridBagConstraints);

	cbFontColor.setMinimumSize(new java.awt.Dimension(100, 24));
	cbFontColor.setPreferredSize(new java.awt.Dimension(100, 24));
	cbFontColor.addItemListener(new java.awt.event.ItemListener() {
	    public void itemStateChanged(java.awt.event.ItemEvent evt) {
		cbFontColorItemStateChanged(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 7;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
	gridBagConstraints.weightx = 0.8;
	gridBagConstraints.weighty = 0.3;
	gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
	pnlStyleControl.add(cbFontColor, gridBagConstraints);

	cbFontSize.setMinimumSize(new java.awt.Dimension(60, 24));
	cbFontSize.setPreferredSize(new java.awt.Dimension(60, 24));
	cbFontSize.addItemListener(new java.awt.event.ItemListener() {
	    public void itemStateChanged(java.awt.event.ItemEvent evt) {
		cbFontSizeItemStateChanged(evt);
	    }
	});
	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 8;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
	gridBagConstraints.weightx = 0.5;
	gridBagConstraints.weighty = 0.3;
	gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
	pnlStyleControl.add(cbFontSize, gridBagConstraints);

	org.jdesktop.layout.GroupLayout pnlMainControlLayout = new org.jdesktop.layout.GroupLayout(
		pnlMainControl);
	pnlMainControl.setLayout(pnlMainControlLayout);
	pnlMainControlLayout
		.setHorizontalGroup(pnlMainControlLayout
			.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING)
			.add(pnlMainControlLayout
				.createSequentialGroup()
				.addContainerGap()
				.add(pnlMainControlLayout
					.createParallelGroup(
						org.jdesktop.layout.GroupLayout.LEADING)
					.add(pnlStyleControl,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
						847,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.add(pnlMainControlLayout
						.createSequentialGroup()
						.add(pnlLanguage,
							org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
							319,
							org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
							org.jdesktop.layout.LayoutStyle.RELATED)
						.add(chbAutoSave)
						.add(38, 38, 38)
						.add(pnlAutoSave,
							org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
							org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
							org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(441, Short.MAX_VALUE)));
	pnlMainControlLayout
		.setVerticalGroup(pnlMainControlLayout
			.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING)
			.add(pnlMainControlLayout
				.createSequentialGroup()
				.add(pnlMainControlLayout
					.createParallelGroup(
						org.jdesktop.layout.GroupLayout.LEADING)
					.add(pnlMainControlLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(pnlMainControlLayout
							.createParallelGroup(
								org.jdesktop.layout.GroupLayout.TRAILING)
							.add(chbAutoSave)
							.add(pnlAutoSave,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								28,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
					.add(pnlLanguage,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
						55,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(
					org.jdesktop.layout.LayoutStyle.RELATED)
				.add(pnlStyleControl,
					org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
					org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE).add(4, 4, 4)));

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 0;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
	add(pnlMainControl, gridBagConstraints);

	org.jdesktop.layout.GroupLayout viewPnlLayout = new org.jdesktop.layout.GroupLayout(
		viewPnl);
	viewPnl.setLayout(viewPnlLayout);
	viewPnlLayout.setHorizontalGroup(viewPnlLayout.createParallelGroup(
		org.jdesktop.layout.GroupLayout.LEADING).add(0, 1300,
		Short.MAX_VALUE));
	viewPnlLayout.setVerticalGroup(viewPnlLayout.createParallelGroup(
		org.jdesktop.layout.GroupLayout.LEADING).add(0, 434,
		Short.MAX_VALUE));

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.weightx = 1.0;
	gridBagConstraints.weighty = 1.0;
	add(viewPnl, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btAlignRightMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST
	// :
	// event_btAlignRightMouseReleased
	txtPnPad.requestFocus();
    }// GEN-LAST:event_btAlignRightMouseReleased

    private void btAlignCenterMouseReleased(java.awt.event.MouseEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_btAlignCenterMouseReleased
	txtPnPad.requestFocus();
    }// GEN-LAST:event_btAlignCenterMouseReleased

    private void btAlignLeftMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST
	// :
	// event_btAlignLeftMouseReleased
	txtPnPad.requestFocus();
    }// GEN-LAST:event_btAlignLeftMouseReleased

    private void btUnderlineMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST
	// :
	// event_btUnderlineMouseReleased
	txtPnPad.toggleUnderlineAction();
    }// GEN-LAST:event_btUnderlineMouseReleased

    private void btItalicMouseReleased(java.awt.event.MouseEvent evt) {// GEN-
	// FIRST:
	// event_btItalicMouseReleased
	txtPnPad.toggleItalicAction();
    }// GEN-LAST:event_btItalicMouseReleased

    private void btBoldMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST
	// :
	// event_btBoldMouseReleased
	txtPnPad.toggleBoldAction();
    }// GEN-LAST:event_btBoldMouseReleased

    private void cbFontSizeItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-
	// FIRST
	// :
	// event_cbFontSizeItemStateChanged

	txtPnPad.setFontSize(cbFontSize.getSelectedItem().toString());
	int size = 0;
	try {
	    size = Integer.parseInt(cbFontSize.getSelectedItem().toString());
	} catch (Exception e) {
	}
	ActionListener a = new StyledEditorKit.FontSizeAction("FontSizeAction",
		size);
	ActionListener[] oldActs = cbFontSize.getActionListeners();
	for (int i = 0; i < oldActs.length; i++)
	    cbFontSize.removeActionListener(oldActs[i]);
	cbFontSize.addActionListener(a);

	try {
	    txtPnPad.requestFocus();
	} catch (Exception e) {
	}

    }// GEN-LAST:event_cbFontSizeItemStateChanged

    private void cbFontFamilyItemStateChanged(java.awt.event.ItemEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_cbFontFamilyItemStateChanged
	// if(cbFontFamilyReshuffling)
	// return ;
	Object selFont = cbFontFamily.getSelectedItem();
	if (selFont == null)
	    return;
	txtPnPad.setFontName(selFont.toString());

	ActionListener a = new StyledEditorKit.FontFamilyAction(
		"FontFamilyAction", cbFontFamily.getSelectedItem().toString());
	ActionListener[] oldActs = cbFontFamily.getActionListeners();
	for (int i = 0; i < oldActs.length; i++)
	    cbFontFamily.removeActionListener(oldActs[i]);
	cbFontFamily.addActionListener(a);

	try {
	    txtPnPad.requestFocus();
	} catch (Exception e) {
	}
    }// GEN-LAST:event_cbFontFamilyItemStateChanged

    private void cbFontColorItemStateChanged(java.awt.event.ItemEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_cbFontColorItemStateChanged
	int index = cbFontColor.getSelectedIndex();
	txtPnPad.setColorIndex(index);
	ActionListener a = new StyledEditorKit.ForegroundAction(
		"set-foreground-" + IndicPane.COLOR_STR_ARRAY[index],
		IndicPane.COLOR_ARRAY[index]);
	ActionListener[] oldActs = cbFontColor.getActionListeners();
	for (int i = 0; i < oldActs.length; i++)
	    cbFontColor.removeActionListener(oldActs[i]);
	cbFontColor.addActionListener(a);
	try {
	    txtPnPad.requestFocus();
	} catch (Exception e) {
	}
    }// GEN-LAST:event_cbFontColorItemStateChanged

    private void rdBtBanglaActionPerformed(java.awt.event.ActionEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_rdBtBanglaActionPerformed
	refillFontFamilyCombo();
    }// GEN-LAST:event_rdBtBanglaActionPerformed

    private void rdBtHindiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-
	// FIRST
	// :
	// event_rdBtHindiActionPerformed
	refillFontFamilyCombo();
    }// GEN-LAST:event_rdBtHindiActionPerformed

    private void rdBtEnglishActionPerformed(java.awt.event.ActionEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_rdBtEnglishActionPerformed
	refillFontFamilyCombo();
    }// GEN-LAST:event_rdBtEnglishActionPerformed

    private void chbAutoSaveItemStateChanged(java.awt.event.ItemEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_chbAutoSaveItemStateChanged
	if (chbAutoSave.isSelected()) {
	    autoSave = true;
	    if (chosenFile == null)
		performAutoSave();

	    if (chosenFile != null)
		pnlAutoSave.setVisible(true);
	    else
		chbAutoSave.setSelected(false);
	} else {
	    autoSave = false;
	    pnlAutoSave.setVisible(false);
	}
    }// GEN-LAST:event_chbAutoSaveItemStateChanged

    private void chbAutoSaveKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST
	// :
	// event_chbAutoSaveKeyReleased
	if (chbAutoSave.isSelected()) {
	    autoSave = true;
	    if (chosenFile == null)
		performAutoSave();

	    if (chosenFile != null)
		pnlAutoSave.setVisible(true);
	    else
		chbAutoSave.setSelected(false);
	} else {
	    autoSave = false;
	    pnlAutoSave.setVisible(false);
	}
    }// GEN-LAST:event_chbAutoSaveKeyReleased

    private void txtSaveIntervalFocusLost(java.awt.event.FocusEvent evt) {// GEN-
	// FIRST
	// :
	// event_txtSaveIntervalFocusLost
	changeSaveInterval();
    }// GEN-LAST:event_txtSaveIntervalFocusLost

    private void cbSaveIntervalUnitItemStateChanged(java.awt.event.ItemEvent evt) {// GEN
	// -
	// FIRST
	// :
	// event_cbSaveIntervalUnitItemStateChanged
	changeSaveInterval();
    }// GEN-LAST:event_cbSaveIntervalUnitItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btAlignCenter;
    private javax.swing.JToggleButton btAlignLeft;
    private javax.swing.JToggleButton btAlignRight;
    private javax.swing.JToggleButton btBold;
    private javax.swing.JToggleButton btItalic;
    private javax.swing.JToggleButton btUnderline;
    private javax.swing.ButtonGroup btnGrpAlignment;
    private javax.swing.ButtonGroup btnGrpLanguage;
    private javax.swing.JComboBox cbFontColor;
    private javax.swing.JComboBox cbFontFamily;
    private javax.swing.JComboBox cbFontSize;
    private javax.swing.JComboBox cbSaveIntervalUnit;
    private javax.swing.JCheckBox chbAutoSave;
    private javax.swing.JLabel lbSaveInterval;
    private javax.swing.JPanel pnlAutoSave;
    private javax.swing.JPanel pnlLanguage;
    private javax.swing.JPanel pnlMainControl;
    private javax.swing.JPanel pnlStyleControl;
    private javax.swing.JRadioButton rdBtBangla;
    private javax.swing.JRadioButton rdBtEnglish;
    private javax.swing.JRadioButton rdBtHindi;
    private javax.swing.JTextField txtSaveInterval;
    private javax.swing.JPanel viewPnl;
    // End of variables declaration//GEN-END:variables

    private IndicPane txtPnPad = null;
    private boolean autoSave = false;
    private File chosenFile = null;
    private transient Thread save = null;
    private int saveInterval = 5;
    private int saveIntervalUnit = 1000;// sec, by default: 1sec = 1000ms

    /** current locale: for displaying the labels */
    private Locale currentLocale = IndicPane.BANGLA_LOCALE;
    private PageContainer pageContainer;

    /**
     * hides unwanted language Radio and sets the default language radio to
     * selected mode
     */
    protected void customiseLanguageSupported(Locale[] supportedLanguages,
	    Locale defaultLocale) {

	List<Locale> supportedLanguagesList = Arrays.asList(supportedLanguages);

	if (!supportedLanguagesList.contains(IndicPane.BANGLA_LOCALE)) {
	    rdBtBangla.setVisible(false);
	} else {
	    Font banglaFont = rdBtBangla.getFont();

	    if (!banglaFont.canDisplay('\u0985')) {
		String defaultBanglaFont = FontLoader.getBanglaFonts()
			.iterator().next();
		rdBtBangla.setFont(new Font(defaultBanglaFont, Font.PLAIN, 14));
	    }
	}

	if (!supportedLanguagesList.contains(IndicPane.HINDI_LOCALE)) {
	    rdBtHindi.setVisible(false);
	}

	if (defaultLocale.equals(IndicPane.BANGLA_LOCALE)) {
	    rdBtBangla.setSelected(true);
	} else if (defaultLocale.equals(IndicPane.HINDI_LOCALE)) {
	    rdBtHindi.setSelected(true);
	}

    }

    private void fillFontComboWithNewFonts(Collection<String> fonts,
	    String selectedFont) {
	JComboUtils.fillComboWithNewArray(cbFontFamily, fonts);
	cbFontFamily.setSelectedItem(selectedFont);
    }

    @Override
    public void newPage() {
	setPage(pageContainer.getNewPage());
    }

    @Override
    public void open() {
	newPage();
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setCurrentDirectory(new File(System
		.getProperty("user.home")));
	fileChooser.setAcceptAllFileFilterUsed(false);
	Filter filter = new Filter("xml", "XML Files(*.xml)");
	fileChooser.addChoosableFileFilter(filter);
	filter = new Filter("htm", "HTML Files(*.htm)");
	fileChooser.addChoosableFileFilter(filter);
	filter = new Filter("html", "HTML Files(*.html)");
	fileChooser.addChoosableFileFilter(filter);

	int returnVal = fileChooser.showOpenDialog(BhashaDefaultPanelView.this);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = fileChooser.getSelectedFile();
	    readFile(file);
	}// end if
    }// end methd open

    private void performAutoSave() {

	if (chosenFile == null) {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    Filter filter = new Filter("htm", "HTML Files(*.htm)");
	    fileChooser.addChoosableFileFilter(filter);
	    filter = new Filter("html", "HTML Files(*.html)");
	    fileChooser.addChoosableFileFilter(filter);
	    int returnVal = fileChooser
		    .showSaveDialog(BhashaDefaultPanelView.this);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		chosenFile = fileChooser.getSelectedFile();
		if (checkFileExists(chosenFile)) {
		    int option = JOptionPane
			    .showConfirmDialog(
				    this,
				    "Do you want to replace the existing file? If you chose yes, the current data will be lost",
				    "A file called " + chosenFile.getName()
					    + " alredy exists.",
				    JOptionPane.YES_NO_OPTION);
		    if (option == JOptionPane.YES_OPTION) {
			autoSaveFile(chosenFile);
		    } else {
			chosenFile = null;
		    }
		} else {
		    autoSaveFile(chosenFile);
		}
	    }
	}
    }

    private void readFile(File file) {
	if (chosenFile != null && file.compareTo(chosenFile) == 0) {
	    JOptionPane
		    .showMessageDialog(
			    this,
			    "The requested File could not be opened as it is alredy in use by you.",
			    "Sorry!", JOptionPane.ERROR_MESSAGE);
	} else {
	    String fileName = file.getAbsolutePath();
	    DocParser xml = new XHtmlDocParser();
	    txtPnPad.setDocument(xml.getDocument(fileName));
	}
	chosenFile = null;
    }

    private void autoSaveFile(File file) {

	GenerateOutput.write(getPageContainer(), GenerateOutput.HTML_FORMAT,
		file);

    }

    private void changeSaveInterval() {

	try {
	    saveInterval = Integer.parseInt(txtSaveInterval.getText());
	} catch (NumberFormatException ne) {
	    saveInterval = 5;
	    txtSaveInterval.setText("5");
	}

	if (cbSaveIntervalUnit.getSelectedIndex() == 1)
	    saveIntervalUnit = 60 * 1000;
	else
	    saveIntervalUnit = 1000;

    }

    @Override
    public void setCurrentLocale(Locale currentLocale) {
	this.currentLocale = currentLocale;
    }

    /**
     * to reshuffle the font family combo when the Language is changed
     */
    @Override
    public void refillFontFamilyCombo() {
	if (rdBtBangla.isSelected()) {
	    currentLocale = IndicPane.BANGLA_LOCALE;
	    txtPnPad.setIndicMap(new PropertyFileUtils().getKeyMap(
		    currentLocale, false));
	    fillFontComboWithNewFonts(FontLoader.getBanglaFonts(),
		    ((Font) IndicPane.LOCALE_TO_FONT.get(currentLocale))
			    .getFamily());

	} else if (rdBtHindi.isSelected()) {
	    currentLocale = IndicPane.HINDI_LOCALE;
	    txtPnPad.setIndicMap(new PropertyFileUtils().getKeyMap(
		    currentLocale, false));
	    fillFontComboWithNewFonts(FontLoader.getHindiFonts(),
		    ((Font) IndicPane.LOCALE_TO_FONT.get(currentLocale))
			    .getFamily());

	} else if (rdBtEnglish.isSelected()) {
	    currentLocale = IndicPane.ENGLISH_LOCALE;
	    txtPnPad.setIndicMap(null);
	    fillFontComboWithNewFonts(FontLoader.getEnglishFonts(),
		    ((Font) IndicPane.LOCALE_TO_FONT.get(currentLocale))
			    .getFamily());
	}

	txtPnPad.setLocale(currentLocale);
	Object selFont = cbFontFamily.getSelectedItem();
	if (selFont == null) {
	    JOptionPane
		    .showMessageDialog(
			    this,
			    "Suitable font not found",
			    "Sorry! Could not find a suitable font to display the selected language",
			    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	// if(selFont != null)
	txtPnPad.setAllAttributes(selFont.toString(), cbFontSize
		.getSelectedItem().toString(), cbFontColor.getSelectedIndex());
	txtPnPad.requestFocus();
    }

    private boolean checkFileExists(File chosenFile) {
	boolean flag = true;
	try {
	    FileInputStream checkExistance = new FileInputStream(chosenFile);
	    checkExistance.close();
	    checkExistance = null;
	} catch (FileNotFoundException e) {
	    flag = false;
	} catch (IOException e) {
	}
	return flag;
    }

    private void initFontColourCombo() {
	for (int i = 0; i < IndicPane.COLOR_ARRAY.length; i++) {
	    new StyledEditorKit.ForegroundAction("set-foreground-"
		    + IndicPane.COLOR_STR_ARRAY[i], IndicPane.COLOR_ARRAY[i]);
	    ColoredSquare colorIcon = new ColoredSquare(
		    IndicPane.COLOR_ARRAY[i], 20, 20);
	    ImageIcon imageIcon = colorIcon.getColoredSquare();
	    imageIcon.setDescription(IndicPane.COLOR_STR_ARRAY[i]);
	    cbFontColor.addItem(imageIcon);
	}// end for

	cbFontColor.setRenderer(new ListCellRenderer() {

	    private final JLabel label = new JLabel();

	    {
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.CENTER);
	    }

	    public Component getListCellRendererComponent(JList list,
		    Object value, int index, boolean isSelected,
		    boolean cellHasFocus) {
		if (isSelected) {
		    label.setBackground(list.getSelectionBackground());
		    label.setForeground(list.getSelectionForeground());
		} else {
		    label.setBackground(list.getBackground());
		    label.setForeground(list.getForeground());
		}

		ImageIcon icon = (ImageIcon) value;
		label.setText(icon.getDescription());
		label.setIcon(icon);
		return label;
	    }

	});
    }

    private void initFontSizeCombo() {
	JComboUtils
		.fillComboWithNewArray(cbFontSize, IndicPane.FONT_SIZE_ARRAY);
	cbFontSize.setSelectedItem(Integer
		.toString(IndicPane.STANDARD_FONT_SIZE));
    }

    private void initStyleButtons() {// to consist of bold, plain, underline....

	btBold.addActionListener(StyleActionFactory.getBold());

	btItalic.addActionListener(StyleActionFactory.getItalic());

	btUnderline.addActionListener(StyleActionFactory.getUnderline());

	btAlignLeft.addActionListener(StyleActionFactory.getAlignLeft());

	btAlignCenter.addActionListener(StyleActionFactory.getAlignCenter());

	btAlignRight.addActionListener(StyleActionFactory.getAlignRight());

    }

    @Override
    public Window getMainWindow() {
	return window;
    }

}
