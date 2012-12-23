/*
 * OpenTypeFontTableReader.java
 *
 * Created on Dec 23, 2012 6:32:58 PM
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

/**
 *  
 * @author paawak
 */
public abstract class OpenTypeFontTableReader {
    
    protected final RandomAccessFileOrArray rf;
    protected final int tableLocation;
    
    public OpenTypeFontTableReader(String fontFilePath, int tableLocation) throws IOException { 
        this.rf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(fontFilePath));
        this.tableLocation = tableLocation;
    }
    
    protected abstract void readLookupTable(int lookupTableLocation) throws IOException;
    
    protected void readLookupListTable() throws IOException {
        int lookupListTableLocation = tableLocation + readHeader().lookupListOffset;
        
        rf.seek(lookupListTableLocation);
        int lookupCount = rf.readShort();
        System.out.println("lookupCount=" + lookupCount);

        List<Integer> lookupTableOffsets = new ArrayList<Integer>();

        for (int i = 0; i < lookupCount; i++) {
            int lookupTableOffset = rf.readShort();
            lookupTableOffsets.add(lookupTableOffset);
        }
        
        for (int lookupTableOffset : lookupTableOffsets) {
            readLookupTable(lookupListTableLocation + lookupTableOffset);
        }
    }
    
    protected List<Integer> readCoverageFormat(int coverageLocation) throws IOException {
        rf.seek(coverageLocation);
        int coverageFormat = rf.readShort();

        List<Integer> glyphIds;

        if (coverageFormat == 1) {
            int glyphCount = rf.readShort();

            glyphIds = new ArrayList<Integer>(glyphCount);

            for (int i = 0; i < glyphCount; i++) {
                int coverageGlyphId = rf.readShort();
                glyphIds.add(coverageGlyphId);
            }

        } else if (coverageFormat == 2) {

            int rangeCount = rf.readShort();

            glyphIds = new ArrayList<Integer>();

            for (int i = 0; i < rangeCount; i++) {
                readRangeRecord(glyphIds);
            }

        } else {
            throw new UnsupportedOperationException("The coverage format " + coverageFormat + " is not yet supported");
        }

        return Collections.unmodifiableList(glyphIds);
    }

    private void readRangeRecord(List<Integer> glyphIds) throws IOException {
        int startGlyphId = rf.readShort();
        int endGlyphId = rf.readShort();
        //skip the `startCoverageIndex` field
        rf.skipBytes(2);

        for (int glyphId = startGlyphId; glyphId <= endGlyphId; glyphId++) {
            glyphIds.add(glyphId);
        }

    }
    
    private TableHeader readHeader() throws IOException {
        rf.seek(tableLocation);
        // 32 bit signed
        int version = rf.readInt();
        // 16 bit unsigned
        int scriptListOffset = rf.readUnsignedShort();
        int featureListOffset = rf.readUnsignedShort();
        int lookupListOffset = rf.readUnsignedShort();

        System.out.println("version=" + version);
        System.out.println("scriptListOffset=" + scriptListOffset);
        System.out.println("featureListOffset=" + featureListOffset);
        System.out.println("lookupListOffset=" + lookupListOffset);
        
        TableHeader header = new TableHeader(version, scriptListOffset, featureListOffset, lookupListOffset);
        
        return header;
    }

}
