/*
 * TaggedFileWriterWithTabs.java
 *
 * Created on Aug 29, 2006, 11:14:43 PM
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

package com.swayam.generic.utils.writer;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author paawak
 */
public class TaggedFileWriterWithTabs extends TaggedFileWriter {
	
	private static final String TAB = "    ";
	
	public TaggedFileWriterWithTabs(final String fileToWrite, final DefaultMutableTreeNode mainTreeNode) {
    	super(fileToWrite, mainTreeNode);
    }
	
    protected String extractContents(DefaultMutableTreeNode tagTreeNode){        
        TagElement tag = (TagElement)tagTreeNode.getUserObject();
        String tabAdded = "";
        int depth = tagTreeNode.getLevel();
        	
        for (int i = 1; i < depth; i++) {
        	tabAdded += TAB;
        }
        
        String startTag = tabAdded + tag.startTag + "\n";
        String endTag = tabAdded + tag.endTag + "\n";
        
        tagTreeNode.setUserObject(new TagElement(startTag, endTag));
        return super.extractContents(tagTreeNode);
    }

}

