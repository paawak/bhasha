package com.itextpdf.text.pdf.languages;

import java.util.Comparator;

/**
 * <p>
 * This works on CompositeCharcaters or Juktakshar-s of Indian languages like Bangla, Hindi, etc. CompositeCharcters
 * are single glyphs consisting of more than one characters.
 * <p>
 * <p>
 * This class works on these CompositeCharacters and places the Strings having higher number
 * of Characters before the one with lower no. This is necessay to properly display the CompositeCharacters
 * when they occur side by side.
 * </p>
 * <p>
 * <h3>Examples of CompositeCharactes from Bangla</h3>
 * <ul>
 * <li><b>à¦™à§?à¦—</b></li>
 * <li><b>à¦™à§?</b></li>
 * <li><b>à¦•à§?à¦·à§?à¦®</b></li>
 * <li><b>à¦•à§?à¦·</b></li>
 * </ul>
 * </p>
 *  
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public class IndicCompositeCharacterComparator implements Comparator<String> {
    
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
