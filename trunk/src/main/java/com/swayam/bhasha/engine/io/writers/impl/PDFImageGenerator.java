/*
 * PDFImageWriter.java
 * 
 * Created on Oct 7, 2007, 4:30:25 PM
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

package com.swayam.bhasha.engine.io.writers.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.model.html.HTMLDocModel;

/**
 * 
 * A dirty trick to get Indic languages on PDF. Converts the model to an image
 * and then embeds the image in a PDF. Use of this became necessary as I could
 * not get iText to write out Indic characters.
 * 
 * Note that this class will soon be deprecated.
 * 
 * @author paawak
 * 
 */
class PDFImageGenerator extends ImageGenerator {

    private static final int MARGINS = 50;

    public PDFImageGenerator() {
        super(ImageGenerator.IMAGE_TYPE_JPEG);
    }

    @Override
    public void generate(File fileName) throws DocGenerationException {

        for (HTMLDocModel model : pageModels) {
            try {
                // FIXME: TODO: handle multiple pages
                makePDFPage(model, fileName.getAbsolutePath());
            } catch (IOException e) {
                throw new DocGenerationException(e);
            }
        }

    }

    private void makePDFPage(HTMLDocModel htmlDoc, String fileName) throws DocGenerationException, IOException {

        BufferedImage image = getImage(pageDim, htmlDoc);

        /*
         * ByteArrayOutputStream bos = new ByteArrayOutputStream();
         * 
         * ImageIO.write(image, "JPG", bos);
         * 
         * byte[] imageData = bos.toByteArray();
         * 
         * System.out.println("PDFImageGenerator.makePDFPage() " +
         * imageData.length);
         */

        Rectangle pageSize = PageSize.A4;

        if (image.getHeight() > pageSize.getHeight()) {
            pageSize = new Rectangle(image.getWidth(), image.getHeight());
        }

        Document pdfDoc = new Document(pageSize, MARGINS, MARGINS, MARGINS, MARGINS);

        FileOutputStream pdfStream = null;

        try {
            pdfStream = new FileOutputStream(fileName);

            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDoc, pdfStream);

            pdfDoc.addAuthor("Bhasha PDF Generator (Powered by IText)");

            pdfDoc.open();

            PdfContentByte contentByte = pdfWriter.getDirectContent();

            Image pdfImage = Image.getInstance(image, null);

            pdfImage.setAbsolutePosition(0, 0);
            contentByte.addImage(pdfImage);

        } catch (Exception e) {
            throw new DocGenerationException(e);
        } finally {

            pdfDoc.close();

            if (pdfStream != null) {
                try {
                    pdfStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
