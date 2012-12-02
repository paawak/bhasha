/*
 * Para.java
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

import java.util.List;

import javax.swing.text.StyleConstants;

/**
 * Created on Sep 16, 2007, 8:48:31 PM
 * @author paawak
 *
 */

public interface Para {
	
	int RIGHT = StyleConstants.ALIGN_RIGHT;
	int CENTER = StyleConstants.ALIGN_CENTER;
	int LEFT = StyleConstants.ALIGN_LEFT;
	int JUSTIFIED = StyleConstants.ALIGN_JUSTIFIED;
	
	int getAlignment();
	
	List<ParaText> getParaTextList();

}

