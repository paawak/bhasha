/*
 * RTFGenerator.java
 *
 * Created on July 3, 2006, 12:30 AM
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
import java.util.LinkedHashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import com.swayam.bhasha.engine.io.writers.DocGenerationException;
import com.swayam.bhasha.model.html.HTMLDocModel;
import com.swayam.bhasha.model.html.Para;
import com.swayam.bhasha.model.html.ParaText;
import com.swayam.generic.utils.writer.TaggedFileWriter;
import com.swayam.generic.utils.writer.rtf.RTFUtils;

/**
 * 
 * @author paawak
 */
class RTFGenerator extends AbstractDocGenerator {

    /**
     * Contains java.awt.Color as key and the corresponding RTF representation
     * as value. Note that a LinkedHashMap is used as it maintains insertion
     * order. In this case, the order is very important. A Hashtable and Hashmap
     * is grossly unsuiatble as it is unordered.
     */
    private final LinkedHashMap<Color, String> colorTbl = new LinkedHashMap<Color, String>();
    private final LinkedHashMap<String, String> fontTbl = new LinkedHashMap<String, String>();
    private final DefaultMutableTreeNode rtfRootNode = RTFUtils.getDefaultRTFDocumentHeader();

    public RTFGenerator() {

    }

    public void generate(File fileName) throws DocGenerationException {

        for (HTMLDocModel model : pageModels) {
            makeRTFContent(model);
        }

        // add the color table and font tables
        rtfRootNode.insert(RTFUtils.getColorTable(colorTbl), 0);
        rtfRootNode.insert(RTFUtils.getFontTable(fontTbl), 1);

        new TaggedFileWriter(fileName.getAbsolutePath(), rtfRootNode).run();
    }

    /**
     * Translates the given Document into rtf-specific DefaultMutableTreeNode
     * and adds it to the root node.
     * 
     * @throws DocGenerationException
     */
    private void makeRTFContent(HTMLDocModel htmlModel) throws DocGenerationException {

        for (Para para : htmlModel.getParaList()) {
            // the paraNode contains the alignment as String
            int alignment = para.getAlignment();
            DefaultMutableTreeNode rtfParaNode = RTFUtils.getParagraph(alignment);
            rtfRootNode.add(rtfParaNode);

            // extract the contents of the paragraph
            for (ParaText paraText : para.getParaTextList()) {
                // the contentNode contains a StringMap
                DefaultMutableTreeNode rtfContent = RTFUtils.getStyledText(paraText.getText(), paraText.getFontFamily(), paraText.getFontSize(), paraText.getColor(), paraText
                        .isBold(), paraText.isItalic(), paraText.isUnderline(), colorTbl, fontTbl);
                rtfParaNode.add(rtfContent);
            }

        }
    }

}
