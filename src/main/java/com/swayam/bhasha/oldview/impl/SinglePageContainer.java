/*
 * Page.java
 *
 * Created on January 28, 2006, 9:12 AM
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

package com.swayam.bhasha.oldview.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.swayam.bhasha.oldview.PageContainer;
import com.swayam.bhasha.utils.page.IndicPane;

/**
 *
 * @author paawak
 */
public class SinglePageContainer extends JPanel implements PageContainer {
	
	private static final long serialVersionUID = -531149382108018239L;
	private Dimension pageDimension = new Dimension(1000, 800);
    /**this is the vector where all pages are stored*/
    private final List<IndicPane> pageVector = new Vector<IndicPane>();
    
    public SinglePageContainer() {
        setMaximumSize(new Dimension(pageDimension.width, Integer.MAX_VALUE));
        setPreferredSize(pageDimension);
    }
    
    public JComponent getPageContainerView() {
    	return this;
    }
    
    public int getPageCount() {
    	return 1;
    }
    
    /**gets the vector which stores all pages*/
    public List<IndicPane> getPageVector(){
        return pageVector;
    }
    
    public IndicPane getPage(int index){
        return pageVector.get(index);
    }
    
    public int getCurrentPageIndex(){
        return 0;
    }
    
    public Dimension getPageDimension(){
        return pageDimension;
    }
    
    public IndicPane getNewPage() {
    	
    	pageVector.clear();
    	return addPageSinglePageMode();
    	
    }
    
    private IndicPane addPageSinglePageMode(){
    	
    	removeAll();
    	
    	setLayout(new BorderLayout());
    	
        IndicPane indicPage = new IndicPane();
        JScrollPane scrPane = new JScrollPane();
        scrPane.setViewportView(indicPage);

        add(scrPane, BorderLayout.CENTER);
        
        pageVector.add(indicPage);
        doLayout();     
        
        return indicPage;
        
    }
    
}
