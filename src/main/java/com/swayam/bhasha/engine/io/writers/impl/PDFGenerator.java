/*
 * PDFGenerator.java
 * 
 * Created on Sep 29, 2007, 11:43:27 PM
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.model.html.HTMLDocModel;
import com.swayam.bhasha.model.html.Para;
import com.swayam.bhasha.model.html.ParaText;

/**
 * Writes Indic characters in a PDF.
 * 
 * @author paawak
 * 
 */
class PDFGenerator extends AbstractDocGenerator {

    private static final int MARGINS = 50;

    public PDFGenerator() {

    }

    public void generate(File fileName) throws DocGenerationException {

        for (HTMLDocModel model : pageModels) {
            // FIXME: TODO: handle multiple pages
            makePDFPage(model, fileName.getAbsolutePath());
        }
    }

    private void makePDFPage(HTMLDocModel htmlDoc, String fileName) throws DocGenerationException {

        Rectangle pageSize = PageSize.A4;

        if (pageDim.height > pageSize.getHeight()) {
            pageSize = new Rectangle(pageDim.width, pageDim.height);
        }

        Document pdfDoc = new Document(pageSize, MARGINS, MARGINS, MARGINS, MARGINS);

        FileOutputStream pdfStream = null;

        try {
            pdfStream = new FileOutputStream(fileName);

            PdfWriter.getInstance(pdfDoc, pdfStream);

            pdfDoc.addAuthor("Bhasha PDF Generator (Powered by IText)");

            pdfDoc.open();

            List<Para> paraList = htmlDoc.getParaList();

            for (Para para : paraList) {
                pdfDoc.add(getParagraph(para));
            }

            pdfDoc.close();

        } catch (Exception e) {
            throw new DocGenerationException(e);
        } finally {

            if (pdfStream != null) {
                try {
                    pdfStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private Paragraph getParagraph(Para para) throws DocumentException, IOException, DocGenerationException {

        int align = para.getAlignment();
        int pdfAlign;

        switch (align) {
        case Para.CENTER:
            pdfAlign = Paragraph.ALIGN_CENTER;
            break;

        case Para.RIGHT:
            pdfAlign = Paragraph.ALIGN_RIGHT;
            break;

        case Para.JUSTIFIED:
            pdfAlign = Paragraph.ALIGN_JUSTIFIED;
            break;

        case Para.LEFT:
        default:
            pdfAlign = Paragraph.ALIGN_LEFT;
            break;
        }

        List<ParaText> paraTextList = para.getParaTextList();
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(pdfAlign);

        for (ParaText paraText : paraTextList) {

            int fontStyle = Font.NORMAL;

            if (paraText.isBold()) {
                fontStyle |= Font.BOLD;
            }

            if (paraText.isUnderline()) {
                fontStyle |= Font.UNDERLINE;
            }

            if (paraText.isItalic()) {
                fontStyle |= Font.ITALIC;
            }

            Font font = FontFactory.getFont(paraText.getFontFamily(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED, paraText.getFontSize(), fontStyle, new BaseColor(paraText.getColor()
                    .getRGB()));

            // its very important to set the leading every time the font is set
            // otherwise, the chunks/phrases overlap.
            // paragraph.setLeading(font.getSize() * 1.5f);

            paragraph.add(new Phrase(paraText.getText(), font));
        }

        return paragraph;

    }
}
