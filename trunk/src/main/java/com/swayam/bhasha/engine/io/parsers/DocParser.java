/*
 * DocParser.java
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

import javax.swing.text.Document;

/**
 * Interface to load a file into the Bhasah editor.
 * 
 * Created on Jun 23, 2007, 11:52:48 PM
 * 
 * @author paawak
 * 
 */
public interface DocParser {

    /**
     * Loads the file in a {@link javax.swing.text.Document} and returns it.
     * 
     * @param xmlFilePath
     *            Path of the file to be loaded.
     * 
     */
    Document getDocument(String xmlFilePath);

}
