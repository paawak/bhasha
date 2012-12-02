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

package com.swayam.bhasha.oldview;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComponent;

import com.swayam.bhasha.utils.page.IndicPane;

/**
 *
 * @author paawak
 */
public interface PageContainer {
	
	JComponent getPageContainerView();
	
	Dimension getPageDimension();
	
	IndicPane getNewPage();
	
	int getPageCount();
    
    /**gets the vector which stores all pages*/
    List<IndicPane> getPageVector();
    
    IndicPane getPage(int index);
    
    int getCurrentPageIndex();
    
}
