/*
 * BanglaPdfGenerator.java
 *
 * Created on Nov 25, 2012 4:48:06 PM
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
package com.swayam.bhasha;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Inspired from http://itextpdf.com/examples/iia.php?id=158
 * 
 * @author paawak
 */
public class BanglaPdfGenerationTest {

    /**
     * The unicode of this is given below:
     * 
     * \u0986\u09ae\u09bf \u0995\u09cb\u09a8 \u09aa\u09a5\u09c7
     * \u0995\u09cd\u09b7\u09c0\u09b0\u09c7\u09b0 \u09b7\u09a8\u09cd\u09a1
     * \u09aa\u09c1\u09a4\u09c1\u09b2 \u09b0\u09c1\u09aa\u09cb
     * \u0997\u0999\u09cd\u0997\u09be \u098b\u09b7\u09bf
     * 
     */
    private static final String BANGLA_TEXT = "আমি কোন পথে ক্ষীরের ষন্ড পুতুল রুপো গঙ্গা ঋষি";

    // private static final String BANGLA_TEXT =
    // "\u0986\u09bf\u09ae \u0995\u09cb\u09a8 \u09aa\u09a5\u09c7";

    public void createPdf(String filename) throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(BANGLA_TEXT, new Font(BaseFont.createFont("/usr/share/fonts/lohit-bengali/Lohit-Bengali.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
        document.add(paragraph);
        // step 5
        document.close();
    }

    @Test
    public void testGenarate() throws IOException, DocumentException {
        FontFactory.registerDirectories();

        System.out.println(FontFactory.getRegisteredFonts());
        System.out.println(FontFactory.getRegisteredFamilies());
        String fileName = System.getProperty("user.home") + "/a.pdf";
        new BanglaPdfGenerationTest().createPdf(fileName);
    }

}
