/*
 * HTMLModelFactory.java
 * 
 * Created on Sep 16, 2007, 8:59:02 PM
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

package com.swayam.bhasha.engine.io.parsers;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import org.apache.log4j.Logger;

import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.model.html.HTMLDocModel;
import com.swayam.bhasha.model.html.Para;
import com.swayam.bhasha.model.html.ParaText;

/**
 * 
 * Gets a HTMLDocModel from a javax.swing.text.Document
 * 
 * @author paawak
 * 
 */

public class JavaDocumentReader {

    private static final String PARAGRAPH = "paragraph";
    private static final String CONTENT = "content";
    private static final String OS_WINDOWS = "Win";

    private final static Logger log = Logger.getLogger(JavaDocumentReader.class);

    private JavaDocumentReader() {

    }

    public static HTMLDocModel getHtmlModel(Document doc, String typedText) throws DocGenerationException {

        HTMLDocModelImpl htmlModel = new HTMLDocModelImpl();

        List<Element> elementDump = getElementDump(doc);
        /*
         * adjustment factor for compensating for the new line characters
         */
        int newLineAdjust = -1;

        ParaImpl para = null;

        for (Element element : elementDump) {

            log.debug("getHtmlModel   1111111111111");

            if (element.getName().equals(PARAGRAPH)) {

                newLineAdjust++;

                Integer alignment = (Integer) getAttributeValue(element, StyleConstants.Alignment);

                log.debug("getHtmlModel   22222222222");

                if (alignment == null) {
                    alignment = StyleConstants.ALIGN_LEFT;
                }

                para = new ParaImpl(alignment);

                log.debug("getHtmlModel   3333333333333");
                htmlModel.addPara(para);

            } else if (element.getName().equals(CONTENT)) {

                String family = (String) getAttributeValue(element, StyleConstants.FontFamily);

                log.debug("getHtmlModel   4444444444444");

                if (family != null) {
                    int size = Integer.parseInt(getAttributeValue(element, StyleConstants.FontSize).toString());

                    log.debug("getHtmlModel   55555555555555555");

                    Color color = (Color) getAttributeValue(element, StyleConstants.Foreground);

                    log.debug("getHtmlModel   66666666666666666666");

                    if (color == null) {
                        color = new Color(0, 0, 0);
                    }

                    boolean bold = (Boolean) getAttributeValue(element, StyleConstants.Bold);
                    boolean italic = (Boolean) getAttributeValue(element, StyleConstants.Italic);
                    boolean underline = (Boolean) getAttributeValue(element, StyleConstants.Underline);

                    log.debug("getHtmlModel   7777777777777777");

                    // the font style: bold/italic etc.
                    // initially: plain
                    int fontStyle = Font.PLAIN;

                    if (bold) {
                        fontStyle |= Font.BOLD;
                    }

                    if (italic) {
                        fontStyle |= Font.ITALIC;
                    }

                    log.debug("getHtmlModel   88888888888888888");

                    String partialString = getSubString(typedText, element, newLineAdjust);

                    log.debug("getHtmlModel   99999999999999999");

                    ParaText paraText = JavaDocumentReader.getParaText(partialString, family, size, color, bold, italic, underline);

                    log.debug("getHtmlModel   aaaaaaaaaaaa");

                    para.addParaText(paraText);

                    log.debug("getHtmlModel   bbbbbbbbbbbbbbbbbbb");
                }
            }
        }

        return htmlModel;
    }

    private static ParaText getParaText(final String text, final String fontFamily, final int fontSize, final Color color, final boolean bold, final boolean italic,
            final boolean underline) {

        return new ParaText() {

            public Color getColor() {
                return color;
            }

            public String getFontFamily() {
                return fontFamily;
            }

            public int getFontSize() {
                return fontSize;
            }

            public String getText() {
                return text;
            }

            public boolean isBold() {
                return bold;
            }

            public boolean isItalic() {
                return italic;
            }

            public boolean isUnderline() {
                return underline;
            }

        };

    }

    private static Object getAttributeValue(Element element, Object attr) {
        Object attrVal = null;
        AttributeSet attributeset = element.getAttributes().copyAttributes();
        if (attributeset == null)
            return attrVal;
        attrVal = attributeset.getAttribute(attr);
        return attrVal;
    }

    /**
     * Utility method to get all the element with its children and nested
     * children in a Vector. The parent is added first followed by the children.
     */
    private static List<Element> getElementDump(Document doc) {
        List<Element> elementVec = new Vector<Element>();
        // doc.getRootElements() returns an array of root elements
        // we are interested only in the first known as the "section"
        // so we neglect the others(there is only one more root element
        // "bidi root")
        Element sectionElement = doc.getRootElements()[0];
        addElementToVector(sectionElement, elementVec);
        // chuck the first element, i.e, "section"
        elementVec.remove(sectionElement);

        return elementVec;
    }

    /**
     * Recursively adds the given elemnt and then its children to the given
     * vector.
     * 
     * @param element
     * @param elementVec
     */
    private static void addElementToVector(Element element, List<Element> elementVec) {
        elementVec.add(element);
        int childCount = element.getElementCount();

        for (int count = 0; count < childCount; count++) {
            addElementToVector(element.getElement(count), elementVec);
        }

    }

    private static String getSubString(String typed, Element element, int newLineAdjustIndex) throws DocGenerationException {
        int elementStartOffset = element.getStartOffset();
        int elementEndOffset = element.getEndOffset();
        String userOs = System.getProperty("os.name");

        if (log.isDebugEnabled()) {

            StringBuilder debug = new StringBuilder();
            debug.append("getSubString() :: trying to do a substring of ").append(getIndicStringForDebug(typed)).append(", with total length = ").append(typed.length()).append(
                    ", elementStartOffset = ").append(elementStartOffset).append(", elementEndOffset = ").append(elementEndOffset).append(", newLineAdjustIndex = ").append(
                    newLineAdjustIndex).append(", userOs = ").append(userOs);

            log.debug(debug);

        }

        if (userOs.startsWith(OS_WINDOWS)) {

            elementStartOffset += newLineAdjustIndex;
            elementEndOffset += newLineAdjustIndex;

        }

        try {

            String sub = typed.substring(elementStartOffset, elementEndOffset);
            if (log.isDebugEnabled()) {
                log.debug("getSubString() :: substring = " + getIndicStringForDebug(sub));
            }

            return sub;

        } catch (IndexOutOfBoundsException e) {
            log.error("in getSubString()", e);
        }

        throw new DocGenerationException("Please make the class " + JavaDocumentReader.class.getName()
                + " specific to your OS: you are getting IndexOutOfBoundsException while trying to do a substring.");
    }

    private static final String getIndicStringForDebug(String indics) {

        char[] charArray = indics.toCharArray();

        StringBuilder sb = new StringBuilder("[");

        for (char indicChar : charArray) {

            if ((indicChar >= 0x980 && indicChar <= 0x9ff) || (indicChar >= 0x900 && indicChar <= 0x97f)) {

                sb.append("&#").append((int) indicChar).append(";");

            } else if (indicChar == '\r') {

                sb.append(indicChar).append("*******************\\r");

            } else if (indicChar == '\n') {

                sb.append(indicChar).append("*******************\\n");

            } else {

                sb.append(indicChar);

            }

        }

        sb.append("]");
        return sb.toString();
    }

    private static class ParaImpl implements Para {

        private final int alignment;
        private final List<ParaText> paraList;

        ParaImpl(int alignment) {
            this.alignment = alignment;
            paraList = new ArrayList<ParaText>();
        }

        public int getAlignment() {
            return alignment;
        }

        public List<ParaText> getParaTextList() {
            return Collections.unmodifiableList(paraList);
        }

        void addParaText(ParaText paraText) {
            paraList.add(paraText);
        }

    }

    private static class HTMLDocModelImpl implements HTMLDocModel {

        private final List<Para> paraList;

        HTMLDocModelImpl() {
            paraList = new ArrayList<Para>();
        }

        public List<Para> getParaList() {
            return Collections.unmodifiableList(paraList);
        }

        void addPara(Para para) {
            paraList.add(para);
        }

    }

}
