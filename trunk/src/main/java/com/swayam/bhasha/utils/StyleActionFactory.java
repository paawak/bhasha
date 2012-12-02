/*
 * StyleActionFactory.java
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

package com.swayam.bhasha.utils;

import javax.swing.Action;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 * Gets <code>javax.swing.Action</code> for bold, italic, alignment, etc.
 * 
 * Created on Jul 14, 2007, 5:23:44 AM
 * @author paawak
 *
 */

public class StyleActionFactory {
	
	private StyleActionFactory() {
		
	}
	
	public static Action getBold() {
		return new StyledEditorKit.BoldAction();
	}
	
	public static Action getItalic() {
		return new StyledEditorKit.ItalicAction();
	}
	
	public static Action getUnderline() {
		return new StyledEditorKit.UnderlineAction();
	}
	
	public static Action getAlignLeft() {
		return new StyledEditorKit.AlignmentAction("ALIGN_LEFT",StyleConstants.ALIGN_LEFT);
	}
	
	public static Action getAlignCenter() {
		return new StyledEditorKit.AlignmentAction("ALIGN_CENTER",StyleConstants.ALIGN_CENTER);
	}
	
	public static Action getAlignRight() {
		return new StyledEditorKit.AlignmentAction("ALIGN_RIGHT",StyleConstants.ALIGN_RIGHT);
	}
	
	public static Action getAlignJustified() {
		return new StyledEditorKit.AlignmentAction("ALIGN_JUSTIFIED",StyleConstants.ALIGN_JUSTIFIED);
	}

}

