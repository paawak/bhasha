/*
 * BhashaView.java
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

package com.swayam.bhasha.oldview;

import javax.swing.JFrame;

/**
 * Created on Jul 14, 2007, 5:05:13 AM
 * @author paawak
 *
 */

public interface BhashaView {
	
	void setPageContainer(PageContainer pageContainer);
	
	PageContainer getPageContainer();
	
	JFrame getJFrame();

}

