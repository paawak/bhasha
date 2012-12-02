/*
 * ParaText.java
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
 */

package com.swayam.bhasha.model.html;

import java.awt.Color;

/**
 * Created on Sep 16, 2007, 8:50:57 PM
 * @author paawak
 *
 */

public interface ParaText {
	
	/**
	 * Always returns string in UNICODE.
	 * 
	 */
	String getText();
	
	String getFontFamily();
	
	int getFontSize();
	
	Color getColor();
	
	boolean isBold();
	
	boolean isItalic();
	
	boolean isUnderline();

}

