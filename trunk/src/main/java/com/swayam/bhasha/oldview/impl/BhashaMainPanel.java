package com.swayam.bhasha.oldview.impl;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.engine.io.writers.impl.XHtmlGenerator;
import com.swayam.bhasha.oldview.BhashaView;
import com.swayam.bhasha.oldview.PageContainer;
import com.swayam.bhasha.oldview.io.GenerateOutput;

@SuppressWarnings("serial")
public class BhashaMainPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private final BhashaDefaultPanelView bhashaView;

    private final JTextArea rawHtmlView;

    private final JScrollPane rawHtmlViewPane;

    public BhashaMainPanel(PageContainer pageContainer, Locale[] supportedLanguages, Locale defaultLocale) {
	bhashaView = new BhashaDefaultPanelView(pageContainer, supportedLanguages, defaultLocale);
	bhashaView.setPageContainer(pageContainer);

	tabbedPane = new JTabbedPane();
	setLayout(new BorderLayout());

	add(tabbedPane, BorderLayout.CENTER);

	tabbedPane.addTab("Editor View", bhashaView);

	rawHtmlViewPane = new JScrollPane();
	rawHtmlView = new JTextArea();
	rawHtmlView.setEditable(false);
	rawHtmlViewPane.setViewportView(rawHtmlView);

	tabbedPane.addTab("Raw HTML View", rawHtmlViewPane);

	tabbedPane.addChangeListener(new ChangeListener() {

	    public void stateChanged(ChangeEvent e) {

		if (tabbedPane.getSelectedComponent() == rawHtmlViewPane) {
		    try {
			XHtmlGenerator htmlGenerator = new XHtmlGenerator(GenerateOutput.getPageModels(bhashaView.getPageContainer().getPageVector()));
			String html = htmlGenerator.getHtml();
			rawHtmlView.setText(html);
		    } catch (DocGenerationException e1) {
			e1.printStackTrace();
		    }
		}

	    }
	});

    }

    public BhashaView getBhashaView() {
	return bhashaView;
    }

}
