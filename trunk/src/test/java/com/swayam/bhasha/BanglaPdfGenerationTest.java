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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
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

    private static final String INDIC_TEXT = "\u0986\u09ae\u09bf \u0995\u09cb\u09a8 \u09aa\u09a5\u09c7 \u0995\u09cd\u09b7\u09c0\u09b0\u09c7\u09b0 \u09b7\u09a8\u09cd\u09a1 "
	    + "\u09aa\u09c1\u09a4\u09c1\u09b2 \u09b0\u09c1\u09aa\u09cb \u0997\u0999\u09cd\u0997\u09be \u098b\u09b7\u09bf";

    private static final String INDIC_FONT = "/com/swayam/bhasha/font/Lohit-Bengali.ttf";

    // private static final String INDIC_FONT =
    // "/com/swayam/bhasha/font/LOHIT_14-04-2007.TTF";
    // private static final String INDIC_FONT =
    // "/com/swayam/bhasha/font/VRINDA.TTF";
    // private static final String INDIC_FONT =
    // "/com/swayam/bhasha/font/SolaimanLipi_22-02-2012.ttf";
    // private static final String INDIC_FONT =
    // "/com/swayam/bhasha/font/MANGAL.TTF";

    // private static final String INDIC_FONT =
    // "/com/swayam/bhasha/font/SolaimanLipi_22-02-2012.ttf";

    public void createPdf(String filename) throws DocumentException, IOException {
	// step 1
	Document document = new Document();
	// step 2
	PdfWriter.getInstance(document, new FileOutputStream(filename));
	// step 3
	document.open();
	// step 4
	Paragraph paragraph = new Paragraph();
	Font font = new Font(BaseFont.createFont(BanglaPdfGenerationTest.class.getResource(INDIC_FONT).getFile(), BaseFont.IDENTITY_H, false));
	font.setColor(BaseColor.BLUE);
	font.setSize(20);
	paragraph.add(new Phrase(INDIC_TEXT, font));
	document.add(paragraph);
	// step 5
	document.close();
    }

    @Test
    public void testGenerate() throws IOException, DocumentException {
	String fileName = System.getProperty("user.home") + "/a.pdf";
	System.out.println(Thread.getDefaultUncaughtExceptionHandler());
	new BanglaPdfGenerationTest().createPdf(fileName);
	System.out.println(Thread.getDefaultUncaughtExceptionHandler());
    }

}
