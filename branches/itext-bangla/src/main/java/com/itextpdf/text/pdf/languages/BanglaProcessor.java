/*
 * BanglaProcessor.java
 *
 * Created on Nov 25, 2012 10:06:30 PM
 * 
 */
package com.itextpdf.text.pdf.languages;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public class BanglaProcessor implements LanguageProcessor {

    private static final char HOSSHIEKAAR = '\u09BF';
    private static final char EKAAR = '\u09C7';
    private static final char OIKAAR = '\u09C8';
    private static final char OKAAR = '\u09CB';
    private static final char OUKAAR = '\u09CC';

    private static final Pattern SWAP_CHAR_POSITION_REGEX;

    private static final List<Character> CHARS_TO_BE_SWAPPED;

    static {
        CHARS_TO_BE_SWAPPED = Arrays.asList(HOSSHIEKAAR, EKAAR, OIKAAR, OKAAR, OUKAAR);
        SWAP_CHAR_POSITION_REGEX = Pattern.compile("[" + HOSSHIEKAAR + EKAAR + OIKAAR + OKAAR + OUKAAR + "]");
    }

    public String process(final String s) {

        return s;

        // char[] text = s.toCharArray();
        //
        // for (int index = 0; index < text.length; index++) {
        //
        // char currentChar = text[index];
        //
        // if (CHARS_TO_BE_SWAPPED.contains(currentChar)) {
        // char previousChar = text[index - 1];
        // text[index] = previousChar;
        // text[index - 1] = currentChar;
        // }
        //
        // }
        //
        // return new String(text);

        // palash: the below code does not work for some reason, though the
        // JUnit passes
        // Matcher matcher = SWAP_CHAR_POSITION_REGEX.matcher(s);
        // StringBuilder sb = new StringBuilder(s);
        //
        // while (matcher.find()) {
        // int indexOfMatchedChar = matcher.start();
        // char currentChar = s.charAt(indexOfMatchedChar);
        // char prevChar = s.charAt(indexOfMatchedChar - 1);
        // sb.replace(indexOfMatchedChar - 1, indexOfMatchedChar + 1, "" +
        // currentChar + prevChar);
        // }
        //
        // return sb.toString();

    }

    public boolean isRTL() {
        // TODO Auto-generated method stub
        return false;
    }

}
