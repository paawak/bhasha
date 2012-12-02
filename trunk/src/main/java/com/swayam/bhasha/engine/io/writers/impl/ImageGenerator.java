/*
 * ImageGenerator.java
 * 
 * Created on Jun 23, 2007, 7:21:57 PM
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.StyleConstants;

import org.apache.log4j.Logger;

import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.model.html.HTMLDocModel;
import com.swayam.bhasha.model.html.Para;
import com.swayam.bhasha.model.html.ParaText;

/**
 * 
 * @author paawak
 * 
 */
class ImageGenerator extends AbstractDocGenerator {

    static final String IMAGE_TYPE_JPEG = "JPG";

    static final String IMAGE_TYPE_PNG = "PNG";

    static final String IMAGE_TYPE_GIF = "GIF";

    private static final Logger log = Logger.getLogger(ImageGenerator.class);

    private final String imageType;

    ImageGenerator(String imageType) {
        this.imageType = imageType;
    }

    public void generate(File fileName) throws DocGenerationException {
        // for image format generate an image from each page
        String fileBaseName = fileName.getAbsolutePath();

        String extension = null;

        if (fileBaseName.endsWith(".jpg")) {
            extension = ".jpg";
        } else if (fileBaseName.endsWith(".jpeg")) {
            extension = ".jpeg";
        } else if (fileBaseName.endsWith(".png")) {
            extension = ".png";
        } else if (fileBaseName.endsWith(".gif")) {
            extension = ".gif";
        }

        fileBaseName = fileBaseName.substring(0, fileBaseName.length() - extension.length());

        // i dont want the pages to be numbered if its a single page
        int pageIndex = pageModels.size() == 1 ? -1 : 1;

        for (HTMLDocModel model : pageModels) {
            BufferedImage bufferedimage = getImage(pageDim, model);
            fileName = new File(fileBaseName + (pageIndex == -1 ? "" : pageIndex) + extension);

            try {
                ImageIO.write(bufferedimage, imageType, fileName);
                pageIndex++;
            } catch (IOException e) {
                log.error("in generate()", e);
                throw new DocGenerationException(e.getMessage());
            }

        }
    }

    BufferedImage getImage(Dimension dim, HTMLDocModel htmlDoc) throws DocGenerationException {

        BufferedImage bufferedimage = new BufferedImage(dim.width, dim.height * 10, BufferedImage.TYPE_INT_RGB);

        log.debug("height of original image: " + bufferedimage.getHeight());

        TextLayout textlayout = null;
        float xPos = 0;
        float yPos = 0;
        Graphics g = bufferedimage.getGraphics();
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight());
        List<StringMap> stringMap = new ArrayList<StringMap>();

        for (Para para : htmlDoc.getParaList()) {

            for (ParaText paraText : para.getParaTextList()) {
                String family = paraText.getFontFamily();

                Hashtable<Attribute, Object> hashtable = new Hashtable<Attribute, Object>();

                int size = paraText.getFontSize();
                Color color = paraText.getColor();

                hashtable.put(TextAttribute.FOREGROUND, color);

                if (paraText.isUnderline()) {
                    hashtable.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    hashtable.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
                }
                // the font style: bold/italic etc.
                // initially: plain
                int fontStyle = Font.PLAIN;

                if (paraText.isBold()) {
                    fontStyle |= Font.BOLD;
                }

                if (paraText.isItalic()) {
                    fontStyle |= Font.ITALIC;
                }

                String partialString = paraText.getText() + " ";

                hashtable.put(TextAttribute.FONT, new Font(family, fontStyle, size));

                stringMap.add(new StringMap(partialString, hashtable));

            }

            float pos = paintOnImage(stringMap, bufferedimage, textlayout, xPos, yPos, para.getAlignment(), graphics2d);

            log.debug("yPos: " + yPos);

            if (pos != -1) {
                yPos = pos;
            }

        }

        int imageHeight = yPos > dim.height ? (int) yPos : dim.height;

        log.debug("intermittent image height: " + imageHeight);

        bufferedimage = bufferedimage.getSubimage(0, 0, bufferedimage.getWidth(), imageHeight);

        return bufferedimage;
    }

    private float paintOnImage(List<StringMap> stringMap, BufferedImage bufferedimage, TextLayout textlayout, float xPos, float yPos, int alignmentCode, Graphics2D graphics2d) {

        String toPaint = "";

        for (int count = 0; count < stringMap.size(); count++) {
            toPaint += (stringMap.get(count)).string;
        }

        AttributedString attributedstring = new AttributedString(toPaint);

        for (int count = 0, startIndex = 0; count < stringMap.size(); count++) {
            int length = (stringMap.get(count)).string.length();

            attributedstring.addAttributes((stringMap.get(count)).attributes, startIndex, startIndex + length);
            startIndex += length;
        }

        AttributedCharacterIterator attributedcharacteriterator = attributedstring.getIterator();

        if (attributedcharacteriterator.getAllAttributeKeys().isEmpty()) {
            return -1;
        }

        int startPos = attributedcharacteriterator.getBeginIndex();
        int endPos = attributedcharacteriterator.getEndIndex();
        LineBreakMeasurer linebreakmeasurer = new LineBreakMeasurer(attributedcharacteriterator, new FontRenderContext(null, false, false));
        float width = bufferedimage.getWidth();
        linebreakmeasurer.setPosition(startPos);

        while (linebreakmeasurer.getPosition() < endPos) {
            textlayout = linebreakmeasurer.nextLayout(width);
            yPos += textlayout.getAscent();

            if (alignmentCode == StyleConstants.ALIGN_LEFT)
                xPos = 0;
            else if (alignmentCode == StyleConstants.ALIGN_CENTER)
                xPos = (width - textlayout.getAdvance()) / 2;
            else if (alignmentCode == StyleConstants.ALIGN_RIGHT)
                xPos = width - textlayout.getAdvance();

            textlayout.draw(graphics2d, xPos, yPos);
            yPos += textlayout.getDescent() + textlayout.getLeading();
        }

        stringMap.clear();

        return yPos;
    }

    /** stores a string and a hashtable containing its attributes */
    private static class StringMap {
        String string = null;
        Hashtable<Attribute, Object> attributes = null;

        StringMap(String string, Hashtable<Attribute, Object> attributes) {
            this.string = string;
            this.attributes = attributes;
        }

        @Override
        public String toString() {
            return string + ": :" + attributes.toString();
        }
    }

}
