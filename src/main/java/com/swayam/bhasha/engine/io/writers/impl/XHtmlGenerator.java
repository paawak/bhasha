/*
 * XHtmlGenerator.java
 *
 * Created on Jun 23, 2007, 6:56:33 PM
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.text.StyleConstants;

import org.slf4j.Logger;

import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.model.html.HTMLDocModel;
import com.swayam.bhasha.model.html.Para;
import com.swayam.bhasha.model.html.ParaText;
import com.swayam.bhasha.utils.HTMLKeyWords;
import com.swayam.generic.utils.FontLoader;

/**
 * 
 * @author paawak
 * 
 */
public class XHtmlGenerator extends AbstractDocGenerator {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(XHtmlGenerator.class);

    XHtmlGenerator() {

    }

    public XHtmlGenerator(List<HTMLDocModel> pageModels) {
	setContent(pageModels);
    }

    public void generate(File fileName) throws DocGenerationException {

	try {
	    FileWriter fileoutputstream = new FileWriter(fileName);
	    fileoutputstream.append(getHtml());
	    fileoutputstream.flush();
	    fileoutputstream.close();
	} catch (FileNotFoundException e) {
	    log.error("in generate()", e);
	    throw new DocGenerationException(e.getMessage());
	} catch (IOException e) {
	    log.error("in generate()", e);
	    throw new DocGenerationException(e.getMessage());
	}

    }

    public String getHtml() throws DocGenerationException {
	StringBuilder html = new StringBuilder();

	html.append("<html><body>\n");

	for (HTMLDocModel model : pageModels) {
	    html.append(getHTMLForPage(model));
	}

	html.append("\n</body></html>");

	return html.toString();
    }

    private String getHTMLForPage(HTMLDocModel htmlModel) throws DocGenerationException {

	StringBuilder sb = new StringBuilder();

	for (Para para : htmlModel.getParaList()) {

	    String align;

	    switch (para.getAlignment()) {
	    case StyleConstants.ALIGN_CENTER:
		align = HTMLKeyWords.CENTER_ALIGNED;
		break;
	    case StyleConstants.ALIGN_RIGHT:
		align = HTMLKeyWords.RIGHT_ALIGNED;
		break;
	    case StyleConstants.ALIGN_JUSTIFIED:
	    case StyleConstants.ALIGN_LEFT:
	    default:
		align = HTMLKeyWords.LEFT_ALIGNED;
		break;
	    }

	    sb.append("\n<p align=\"").append(align).append("\">");

	    for (ParaText paraText : para.getParaTextList()) {
		Color color = paraText.getColor();
		String htmlColor = "#";
		String red = Integer.toHexString(color.getRed());
		if (red.length() == 1)
		    red = "0" + red;
		String green = Integer.toHexString(color.getGreen());
		if (green.length() == 1)
		    green = "0" + green;
		String blue = Integer.toHexString(color.getBlue());
		if (blue.length() == 1)
		    blue = "0" + blue;
		htmlColor = htmlColor + red + green + blue;

		sb.append("<").append(HTMLKeyWords.FONT).append(" ").append(HTMLKeyWords.FONT_FACE).append("=\"").append(paraText.getFontFamily()).append("\" ").append(HTMLKeyWords.FONT_SIZE)
			.append("=\"").append(paraText.getFontSize()).append("\" ").append(HTMLKeyWords.FONT_COLOR).append("=\"").append(htmlColor).append("\">");

		if (paraText.isUnderline()) {
		    sb.append("<").append(HTMLKeyWords.UNDERLINE).append(">");
		}

		if (paraText.isBold()) {
		    sb.append("<").append(HTMLKeyWords.BOLD).append(">");
		}

		if (paraText.isItalic()) {
		    sb.append("<").append(HTMLKeyWords.ITALIC).append(">");
		}

		if (FontLoader.getBanglaFonts().contains(paraText.getFontFamily()) || FontLoader.getHindiFonts().contains(paraText.getFontFamily())) {
		    sb.append(getIndicStringForHtml(paraText.getText()));
		} else {
		    sb.append(paraText.getText());
		}

		if (paraText.isItalic()) {
		    sb.append("</").append(HTMLKeyWords.ITALIC).append(">");
		}

		if (paraText.isBold()) {
		    sb.append("</").append(HTMLKeyWords.BOLD).append(">");
		}

		if (paraText.isUnderline()) {
		    sb.append("</").append(HTMLKeyWords.UNDERLINE).append(">");
		}

		sb.append("</").append(HTMLKeyWords.FONT).append(">\n");

	    }

	    sb.append("</p>\n");
	}

	return sb.toString();
    }

    private String getIndicStringForHtml(String indics) {

	char[] charArray = indics.toCharArray();

	StringBuilder sb = new StringBuilder();

	for (char indicChar : charArray) {

	    if ((indicChar >= 0x980 && indicChar <= 0x9ff) || (indicChar >= 0x900 && indicChar <= 0x97f)) {

		sb.append("&#x").append(Integer.toHexString(indicChar)).append(";");

	    } else {

		sb.append(indicChar);

	    }

	}

	return sb.toString();

    }

}
