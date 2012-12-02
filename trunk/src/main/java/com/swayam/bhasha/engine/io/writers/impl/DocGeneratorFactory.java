/*
 * DocGeneratorFactory.java
 *
 * Created on Oct 1, 2008 7:58:52 PM
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

import java.awt.Dimension;
import java.util.List;

import com.swayam.bhasha.engine.io.writers.DocGenerator;
import com.swayam.bhasha.engine.io.writers.SaveFormat;
import com.swayam.bhasha.model.html.HTMLDocModel;

/**
 * Gets an instance of {@link DocGenerator}
 * 
 * @author paawak
 */
public final class DocGeneratorFactory {

    private final List<HTMLDocModel> pageModels;
    private final Dimension pageDim;

    public DocGeneratorFactory(List<HTMLDocModel> pageModels, Dimension pageDim) {
        this.pageModels = pageModels;
        this.pageDim = pageDim;
    }

    public DocGenerator getGenerator(SaveFormat saveFormat) {

        WritableDocGenerator docGenerator = null;

        switch (saveFormat) {

        case IMAGE_JPEG:
            docGenerator = new ImageGenerator(ImageGenerator.IMAGE_TYPE_JPEG);
            break;
        case IMAGE_GIF:
            docGenerator = new ImageGenerator(ImageGenerator.IMAGE_TYPE_GIF);
            break;
        case IMAGE_PNG:
            docGenerator = new ImageGenerator(ImageGenerator.IMAGE_TYPE_PNG);
            break;
        case RTF:
            docGenerator = new RTFGenerator();
            break;
        case XHTML:
            docGenerator = new XHtmlGenerator();
            break;
        case PDF:
            // docGenerator = new PDFImageGenerator();
            docGenerator = new PDFGenerator();
            break;

        }

        docGenerator.setContent(pageModels);
        docGenerator.setDimension(pageDim);

        return docGenerator;

    }

}
