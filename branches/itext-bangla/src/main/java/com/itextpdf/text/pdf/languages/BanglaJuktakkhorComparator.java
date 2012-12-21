package com.itextpdf.text.pdf.languages;

import java.util.Comparator;

/**
 * Looks for Bangla Juktakkhor-s or Composite Strings. It always places the Strings having higher number
 * of Characters before the one with lower no. This is necessay to properly display the following characters
 * when they occur side by side:
 * <ul>
 * <li><b>ঙ্গ</b></li>
 * <li><b>ঙ্</b></li>
 * <li><b>ক্ষ্ম</b></li>
 * <li><b>ক্ষ</b></li>
 * </ul>
 *  
 * @author paawak
 */
public class BanglaJuktakkhorComparator implements Comparator<String> {
    
    public int compare(String o1, String o2) {
        if (o2.length() > o1.length()) { 
            return 1;
        } else if (o1.length() > o2.length()) { 
            return -1;
        } else {
            return o1.compareTo(o2);
        }
    }

}
