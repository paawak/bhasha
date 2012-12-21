package com.itextpdf.text.pdf.languages;

import java.util.Comparator;

/**
 * Looks for a Hasanto(<b>্</b>) character at the end of the String and pushes it back,
 * so that the non-Hasanto ending Strings get preference. This is needed for the correct display of 
 * characters like <b>ঙ্গ</b> and <b>ঙ্</b> when they occur together.
 *  
 * @author paawak
 */
public class BanglaHasantoComparator implements Comparator<String> {
    
    private static char HASANTO = '\u09CD';

    public int compare(String o1, String o2) {
        if (stringEndsWithHasanto(o1) && !stringEndsWithHasanto(o2)) {
            return o2.compareTo(o1);
        } else {
            return o1.compareTo(o2);
        }
    }
    
    private boolean stringEndsWithHasanto(String input) {
        return (input.charAt(input.length() - 1) == HASANTO);
    }

}
