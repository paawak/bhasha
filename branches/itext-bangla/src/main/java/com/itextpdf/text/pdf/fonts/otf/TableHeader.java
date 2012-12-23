/*
 * TableHeader.java
 *
 * Created on Dec 23, 2012 6:41:59 PM
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
package com.itextpdf.text.pdf.fonts.otf;

/**
 *  
 * @author paawak
 */
public class TableHeader {
    
    public int version;
    public int scriptListOffset;
    public int featureListOffset;
    public int lookupListOffset;
    
    public TableHeader(int version, int scriptListOffset, int featureListOffset, int lookupListOffset) {
        this.version = version;
        this.scriptListOffset = scriptListOffset;
        this.featureListOffset = featureListOffset;
        this.lookupListOffset = lookupListOffset;
    }
    
}
