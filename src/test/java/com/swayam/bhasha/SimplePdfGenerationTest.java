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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Inspired from http://itextpdf.com/examples/iia.php?id=158
 * 
 * @author paawak
 */
public class SimplePdfGenerationTest {

    private static final String TEXT = "Hello World!";

    public void createPdf(String filename) throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(TEXT));
        document.add(paragraph);
        // step 5
        document.close();
    }

    @Test
    public void testGenerate() throws IOException, DocumentException {
        String fileName = System.getProperty("user.home") + "/a.pdf";
        new SimplePdfGenerationTest().createPdf(fileName);
    }

}
