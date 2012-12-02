/*
 * XHtmlDocParser.java
 * 
 * Created on Aug 21, 2005
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
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.swayam.bhasha.utils.HTMLKeyWords;

/**
 * @author paawak
 * 
 */
public class XHtmlDocParser implements DocParser {

    public XHtmlDocParser() {
    }

    /**
     * Expects the file to be in xml/xhtml. Loads the file in a
     * {@link javax.swing.text.Document} and returns it.
     */
    public Document getDocument(String xmlFilePath) {
        DefaultStyledDocument styleDoc = new DefaultStyledDocument();
        Node htmlNode = getMainNode(xmlFilePath);
        Node bodyNode = getNodes(htmlNode, HTMLKeyWords.BODY)[0];
        Node[] paraNodes = getNodes(bodyNode, HTMLKeyWords.PARA);
        for (int i = 0; i < paraNodes.length; i++) {
            setParagraph(styleDoc, paraNodes[i]);
        }
        return styleDoc;
    }

    /**
     *loads the xml document and returns the main parent node
     */
    private Node getMainNode(String xmlFilePath) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new File(xmlFilePath));
            return doc.getChildNodes().item(0);
        } catch (ParserConfigurationException e) {
            System.out.println("EXCEPTION IN ModelGenerator.loadDocument(): " + e.getMessage());
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("EXCEPTION IN ModelGenerator.loadDocument(): " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("EXCEPTION IN ModelGenerator.loadDocument(): " + e.getMessage());
            e.printStackTrace();
        }

        return null;

    }

    /**
     *get the given node by its tag-name
     */
    private Node[] getNodes(Node parentNode, String tag) {
        NodeList list = parentNode.getChildNodes();
        Vector<Node> nodeVec = new Vector<Node>();
        for (int i = 0; i < list.getLength(); i++) {
            // if(list.item(i).getNodeType() != Node.ELEMENT_NODE)
            // continue;
            if (list.item(i).getNodeName().equalsIgnoreCase(tag))
                nodeVec.addElement(list.item(i));
        }

        return nodeVec.toArray(new Node[0]);
    }

    /**
     *get the text contained b/w a tag: <tag>value</tag> returns value. this
     * will also return any Cdata contained within
     */
    private String getNodeTextValue(Node node) {
        NodeList list = node.getChildNodes();
        String buff = "";
        for (int i = 0; i < list.getLength(); i++) {
            if ((list.item(i).getNodeType() == Node.TEXT_NODE) || (list.item(i).getNodeType() == Node.CDATA_SECTION_NODE)) {
                buff += list.item(i).getNodeValue();
            }
        }
        return buff.trim();
    }

    /**
     *returns the value of an attribute of an element: example <p
     * align='left'></p>: should return 'left'. if attribute not found, returns
     * null
     */
    private String getNodeAttributeValue(Node parentNode, String attributeName) {
        NamedNodeMap attrs = parentNode.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Node attrNode = attrs.item(i);
            if (attrNode.getNodeName().equalsIgnoreCase(attributeName)) {
                return attrNode.getTextContent();
            }
        }
        return null;
    }

    /** returns the modified offset */
    private void setParagraph(DefaultStyledDocument styleDoc, Node para) {

        String alignment = getNodeAttributeValue(para, HTMLKeyWords.ALIGN);

        if (alignment == null) {
            alignment = HTMLKeyWords.LEFT_ALIGNED;
        }

        int align = StyleConstants.ALIGN_LEFT;

        if (alignment.equalsIgnoreCase(HTMLKeyWords.RIGHT_ALIGNED)) {
            align = StyleConstants.ALIGN_RIGHT;
        } else if (alignment.equalsIgnoreCase(HTMLKeyWords.CENTER_ALIGNED)) {
            align = StyleConstants.ALIGN_CENTER;
        }

        StyleContext styleContext = new StyleContext();
        Style def = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        String fontStyleName = "font-style";
        Node[] fontNodes = getNodes(para, HTMLKeyWords.FONT);

        for (int i = 0; i < fontNodes.length; i++) {
            Style fontStyle = styleContext.addStyle(fontStyleName, def);
            StyleConstants.setAlignment(fontStyle, align);
            // font face
            String fontFace = getNodeAttributeValue(fontNodes[i], HTMLKeyWords.FONT_FACE);
            String fontSize = getNodeAttributeValue(fontNodes[i], HTMLKeyWords.FONT_SIZE);
            String fontColor = getNodeAttributeValue(fontNodes[i], HTMLKeyWords.FONT_COLOR);
            int size = 12;
            int colorCode = 0;

            try {
                size = Integer.parseInt(fontSize);
                colorCode = Integer.decode(fontColor).intValue();
            } catch (NumberFormatException e) {
            }

            Color color = new Color(colorCode);
            StyleConstants.setFontFamily(fontStyle, fontFace);
            StyleConstants.setFontSize(fontStyle, size);
            StyleConstants.setForeground(fontStyle, color);

            // query for children under font in the following order :
            // u, b, i for underline, bold, italic respectively
            // please note that this is very Bhasha specific

            try {
                fontNodes[i] = getNodes(fontNodes[i], HTMLKeyWords.UNDERLINE)[0];
                StyleConstants.setUnderline(fontStyle, true);
            } catch (ArrayIndexOutOfBoundsException e) {
                StyleConstants.setUnderline(fontStyle, false);
            }

            try {
                fontNodes[i] = getNodes(fontNodes[i], HTMLKeyWords.BOLD)[0];
                StyleConstants.setBold(fontStyle, true);
            } catch (ArrayIndexOutOfBoundsException e) {
                StyleConstants.setBold(fontStyle, false);
            }

            try {
                fontNodes[i] = getNodes(fontNodes[i], HTMLKeyWords.ITALIC)[0];
                StyleConstants.setItalic(fontStyle, true);
            } catch (ArrayIndexOutOfBoundsException e) {
                StyleConstants.setItalic(fontStyle, false);
            }

            String text = getNodeTextValue(fontNodes[i]);

            try {
                styleDoc.insertString(styleDoc.getLength(), text, fontStyle);
                styleDoc.insertString(styleDoc.getLength(), " ", null);
                Style ls = styleContext.getStyle(fontStyleName);
                styleDoc.setLogicalStyle(styleDoc.getLength() - 1, ls);
                if (i == fontNodes.length - 1) {
                    styleDoc.insertString(styleDoc.getLength(), "\n", null);
                }
            } catch (BadLocationException e) {
            }
        }
    }

}
