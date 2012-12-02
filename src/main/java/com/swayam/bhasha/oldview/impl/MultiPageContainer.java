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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.swayam.bhasha.oldview.PageContainer;
import com.swayam.bhasha.utils.page.IndicPane;
import com.swayam.bhasha.utils.page.PageEvent;
import com.swayam.bhasha.utils.page.PageListener;
import com.swayam.bhasha.utils.page.PageableIndicPane;

/**
 *
 * @author paawak
 */
public class MultiPageContainer extends JPanel implements PageContainer {
    
	private static final long serialVersionUID = -8472546741911020576L;
	private Dimension pageDimension = new Dimension(700, 800);
    private int pageTopMargin = 100;
    private int pageLeftMargin = 20;
    private int pageBottomMargin = 100;
    private int pageRightMargin = 20;
    
    /**
     * the clearance between the horizontal extremities of the page container and the page.
     * the page will be center-aligned in the page container
     */
    private static final int horizontalOffset = 50;
    /**
     * the clearance between the vertical extremities of the page container and the page
     * and also the clearance between two consecutive pages
     */
    private static final int verticalOffset = 20;
    int currentPageIndex = 0;
    /**this is the vector where all pages are stored*/
    private final List<IndicPane> pageVector = new Vector<IndicPane>();
    private final JPanel leftVerticalFiller = new JPanel();
    private final JPanel rightVerticalFiller = new JPanel();
    
    public MultiPageContainer() {
    }
    
    public JComponent getPageContainerView() {
    	return this;
    }
    
    public int getPageCount() {
    	return pageVector.size();
    }
    
    /**gets the vector which stores all pages*/
    public List<IndicPane> getPageVector(){
        return pageVector;
    }
    
    public IndicPane getPage(int index){
        return pageVector.get(index);
    }
    
    public int getCurrentPageIndex(){
        return currentPageIndex;
    }
    
    public Dimension getPageDimension(){
        return pageDimension;
    }
    
    public IndicPane getNewPage() {
    	
    	addHorizontalFiller(currentPageIndex);
    	
    	return addPageMultiplePagesMode();
    	
    }
    
    private IndicPane addPageMultiplePagesMode(){
    	
    	if (getLayout() == null) {
    		setLayout(new GridBagLayout());
    	}
    	
        int gridy = getComponentCount();
        if(gridy > 2){
            gridy -= 2;
        }
        
        final PageableIndicPane indicPage = new PageableIndicPane(null,
                pageDimension, new Insets(pageTopMargin, pageLeftMargin, pageBottomMargin, pageRightMargin));
        
        indicPage.addPageListener(new PageListener() {
            public void beginOfPageReached(PageEvent pageEvent) {
            }

            public void endOfPageReached(PageEvent pageEvent) {
                /*
                 *the cursor must go to a new page. if this is the last page,
                 *add a new page. otherwise go to an existing page
                 */
                if(pageEvent.pageIndex == pageVector.size()-1){
                    addPageMultiplePagesMode();
                } else {
                    pageVector.get(pageEvent.pageIndex + 1).requestFocus();
                }
            }

            public void pageAdded(PageEvent pageEvent) {
            }

            public void pageDeleted(PageEvent pageEvent) {
            }

            public void textOverflowHappened(PageEvent pageEvent) {
            }
        });

        indicPage.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent evt) {
                if(getPage(currentPageIndex) == indicPage){
                    return ;
                }

                currentPageIndex = pageVector.indexOf(indicPage);
            }
        });        

        pageVector.add(indicPage);
        
        addPage_Or_HorizontalFiller(indicPage,  gridy);
        
        addHorizontalFiller(++gridy);
        addVerticalFillers();
        resizePageContainer();        
        //indicPage.requestFocus();
        
        return indicPage;
        
    }
    
    private void resizePageContainer(){
        Dimension dim = new Dimension(getPageContainerWidth(), getPageContainerHeight());
        setPreferredSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
        doLayout();
    }
    
    private void addVerticalFillers(){
        
        remove(leftVerticalFiller);
        remove(rightVerticalFiller);
        
        Dimension dim = new Dimension(verticalOffset, getPageContainerHeight());
        
        leftVerticalFiller.setPreferredSize(dim);
        leftVerticalFiller.setMaximumSize(dim);
        leftVerticalFiller.setMinimumSize(dim);
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        int gridHeight = pageVector.size()*2 + 1;
        gridBagConstraints.gridheight = gridHeight;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        add(leftVerticalFiller, gridBagConstraints);
        
        rightVerticalFiller.setPreferredSize(dim);
        rightVerticalFiller.setMaximumSize(dim);
        rightVerticalFiller.setMinimumSize(dim);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = gridHeight;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(rightVerticalFiller, gridBagConstraints);
    }
    
    private int getPageContainerHeight(){
        return pageVector.size()*pageDimension.height + (pageVector.size()+1)*horizontalOffset;
    }
    
    private int getPageContainerWidth(){
        return pageDimension.width + 2*horizontalOffset;
    }
    
    private void addHorizontalFiller(int gridy){
        JPanel horizontalFiller = new JPanel();
        Dimension dim = new Dimension(pageDimension.width, horizontalOffset);
        
        horizontalFiller.setPreferredSize(dim);
        horizontalFiller.setMaximumSize(dim);
        horizontalFiller.setMinimumSize(dim);
        
        addPage_Or_HorizontalFiller(horizontalFiller,  gridy);
    }
    
    /**
     *adds a Page or a HorizontalFiller to the page container
     */
    private void addPage_Or_HorizontalFiller(JComponent comp, int gridy){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        add(comp, gridBagConstraints);
    }

    
}
