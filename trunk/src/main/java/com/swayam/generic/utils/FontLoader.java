/*
 * FontLoader.java
 *
 * Created on July 11, 2006, 12:30 AM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.swayam.generic.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.itextpdf.text.FontFactory;

/**
 * Loads the available fonts and segregates it according to the language. It
 * then makes these available to interested parties. Only a unmodifiable copy is
 * made available.
 * 
 * @author paawak
 * 
 */
public class FontLoader {

    private static final Set<String> BANGLA_FONTS;
    private static final Set<String> HINDI_FONTS;
    private static final Set<String> ENGLISH_FONTS;

    private FontLoader() {
    }

    static {

        // load available fonts into itext
        FontFactory.registerDirectories();

        // this comparator puts any string having unicode, at the top
        // this is a hack for Mac/Bangla as SolaimanLipi in Mac has Unicode
        // chars
        Set<String> banglaFontsUnicode = new TreeSet<String>();
        Set<String> banglaFontsGeneral = new TreeSet<String>();
        Set<String> hindiFonts = new TreeSet<String>();
        Set<String> englishFonts = new TreeSet<String>();

        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Font[] fonts = g.getAllFonts();

        for (int i = 0; i < fonts.length; i++) {
            String fontFamily = fonts[i].getFamily();

            // do this as pdf will not be generated correctly
            if (!FontFactory.isRegistered(fontFamily)) {
                continue;
            }

            boolean displayBangla = fonts[i].canDisplay(0x985) && fonts[i].canDisplay(0x9fa);

            // hack for Mac
            Pattern banglaPattern = Pattern.compile("[\u0985-\u09fa]");
            if (banglaPattern.matcher(fontFamily).find()) {

                banglaFontsUnicode.add(fontFamily);

            } else if (displayBangla) {

                banglaFontsGeneral.add(fontFamily);

            }

            boolean displayHindi = fonts[i].canDisplay(0x905) && fonts[i].canDisplay(0x96f);

            boolean displayEnglish = fonts[i].canDisplay((int) 'a') && fonts[i].canDisplay((int) 'z');

            // populate English fonts
            if (displayEnglish) {
                englishFonts.add(fontFamily);
            }

            // populate Devnagari fonts
            if (displayHindi) {
                hindiFonts.add(fontFamily);
            }

        }

        // populate Bangla font
        Set<String> banglaFonts = new LinkedHashSet<String>();
        banglaFonts.addAll(banglaFontsUnicode);
        banglaFonts.addAll(banglaFontsGeneral);

        BANGLA_FONTS = Collections.unmodifiableSet(banglaFonts);
        HINDI_FONTS = Collections.unmodifiableSet(hindiFonts);
        ENGLISH_FONTS = Collections.unmodifiableSet(englishFonts);

    }

    public static Set<String> getBanglaFonts() {
        return BANGLA_FONTS;
    }

    public static Set<String> getHindiFonts() {
        return HINDI_FONTS;
    }

    public static Set<String> getEnglishFonts() {
        return ENGLISH_FONTS;
    }

}
