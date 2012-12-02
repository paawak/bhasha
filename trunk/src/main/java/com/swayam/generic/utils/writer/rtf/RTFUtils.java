/*
 * RTFUtils.java
 *
 * Created on July 10, 2006, 12:30 AM
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

package com.swayam.generic.utils.writer.rtf;


import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import com.swayam.generic.utils.FontLoader;
import com.swayam.generic.utils.writer.TagElement;

public class RTFUtils implements RTFConstants {
	
	private RTFUtils() {
		
	}
	
	public static DefaultMutableTreeNode getDefaultRTFDocumentHeader() {
		return new DefaultMutableTreeNode(new TagElement(DOCUMENT_HEADER, "\n}\n"));
	}
	
	/**
	 * returns <b>\pard\plain\<code>alignment</code> {<i>space for contents, to be added later</i>}\par</b>
	 * 
	 * @param alignment : this can be any of <code>StyleConstants.ALIGN_RIGHT, StyleConstants.ALIGN_CENTER,
	 * 				StyleConstants.ALIGN_LEFT</code>. Other than this, it defaults to StyleConstants.ALIGN_LEFT.
	 */
	public static DefaultMutableTreeNode getParagraph(int alignment) {
		String alignString = null;
		
		switch(alignment) {
		case StyleConstants.ALIGN_RIGHT:
			alignString = ALIGN_RIGHT;
			break;
		case StyleConstants.ALIGN_CENTER:
			alignString = ALIGN_CENTER;
			break;
		default:
			alignString = ALIGN_LEFT;
		}
		
		return new DefaultMutableTreeNode(new TagElement(PARD_PLAIN + alignString + "{\n", "\n}\n" + PARA + "\n"));
	}
	
	/**
	 * Configures the given text according to the given styleAttributes. Also, makes new entries to the color table
	 * and font tables if its not already there.
	 * 
	 * @param text : the raw text to be stylised
	 * @param styleAttributes : a Hashtable containg java.awt.font.TextAttribute as the style key and an Object as its value
	 * @param colorTbl : the color table to be modified
	 * @param fontTbl : the font table to be modified
	 */
	public static DefaultMutableTreeNode getStyledText(String text, 
			String fontFamily, int fontSize, Color fgColor,  
			boolean bold, boolean italic, boolean underline,
			LinkedHashMap<Color, String> colorTbl, LinkedHashMap<String, String> fontTbl) {
		
		if(!colorTbl.containsKey(fgColor)) {
			putColorInTable(fgColor, colorTbl);
		}
		
		//get color index from color table
		String colorIndex = FOREGROUND_COLOR + getIndexOfKey(fgColor, colorTbl);
		
		String indicLangCode = "";
		
		if (FontLoader.getBanglaFonts().contains(fontFamily)) {
			indicLangCode = BANGLA_LANG_ID;
		} else if (FontLoader.getHindiFonts().contains(fontFamily)) {
			indicLangCode = HINDI_LANG_ID;
		}
		
		if(!fontTbl.containsKey(fontFamily)) {
			putFontInTable(fontFamily, fontTbl);
		}
		
		String fontString = FONT + getIndexOfKey(fontFamily, fontTbl)
			+ FONT_SIZE + 2*fontSize;
		
		String fontStyleString = "";
		
		if (bold) {
			fontStyleString += BOLD;
		}
		
		if (italic) {
			fontStyleString += ITALIC;
		}
		
		if (underline) {
			fontStyleString += UNDERLINE;
		}
		
		if(FontLoader.getBanglaFonts().contains(fontFamily) || FontLoader.getHindiFonts().contains(fontFamily)){
			text = getRTFedIndicString(text);
        }
		
		return new DefaultMutableTreeNode(
				new TagElement("{\n" + PARD_PLAIN + colorIndex + indicLangCode + fontString 
						+ fontStyleString + " " + text, "\n}\n")
				);
	}
	
	public static DefaultMutableTreeNode getColorTable(LinkedHashMap<Color, String> colorTbl) {
		return getFontOrColorTable(COLOR_TBL + "\n", colorTbl);
	}
	
	public static DefaultMutableTreeNode getFontTable(LinkedHashMap<String, String> fontTbl) {
		return getFontOrColorTable(FONT_TBL + "\n", fontTbl);
	}
	
	/**
	 * 
	 * @param tableType : this can be either RTFConstants.FONT_TBL or RTFConstants.COLOR_TBL
	 * @param table : the font table or color table containing rtf-specific font or
	 * 				color info as values for keys
	 * 
	 */
	private static DefaultMutableTreeNode getFontOrColorTable(String tableType, LinkedHashMap table) {
		Iterator keys = table.keySet().iterator();
		StringBuffer sb = new StringBuffer(tableType);
		
		while (keys.hasNext()) {
			sb.append(table.get(keys.next()));
			sb.append("\n");
		}
		
		return new DefaultMutableTreeNode(new TagElement(sb.toString(), "}\n"));
	}
	
	/**
	 * Puts the given color in the LinkedHashMap <code>colorTbl</code> with the <code>colorToPut</code> as key
	 * and an rtf equivalent color string as the value. 
	 * 
	 * @param colorToPut
	 * @param colorTbl : the color table
	 * 
	 */
	public static void putColorInTable(Color colorToPut, LinkedHashMap<Color, String> colorTbl) {
		String colorString = RED + colorToPut.getRed() + GREEN + colorToPut.getGreen() 
			+ BLUE + colorToPut.getBlue() + ";";
		colorTbl.put(colorToPut, colorString);
	}
	
	/**
	 * Puts the given font in the LinkedHashMap <code>fontTbl</code> with the <code>fontToPut</code> as key
	 * and an rtf equivalent font string as the value. 
	 * 
	 * @param fontToPut
	 * @param fontTbl : the font table
	 * 
	 */
	public static void putFontInTable(String fontToPut, LinkedHashMap<String, String> fontTbl) {		
		String fontString = FONT + fontTbl.size() + " " + fontToPut + ";";
		fontTbl.put(fontToPut, fontString);
	}
	
	public static int getIndexOfKey(final Object key, final LinkedHashMap map) {
		Vector keyVec = new Vector(map.keySet());
		int index = keyVec.indexOf(key);
		keyVec.clear();
		keyVec = null;
		return index;
	}
	
	public static String getRTFedIndicString(String text) {
        
        char[] c = text.toCharArray();
        String rtfedStr = "";
        
        for(int i=0; i<c.length; i++){
            if ((c[i]>=0x980 && c[i]<=0x9ff) || (c[i]>=0x900 && c[i]<=0x97f)){
                rtfedStr += INDIC_CHAR_PREFIX + (int)c[i] + INDIC_CHAR_SUFFIX;
            } else {
                rtfedStr += c[i];
            }
        }
        
        return rtfedStr;
        
    }

}

