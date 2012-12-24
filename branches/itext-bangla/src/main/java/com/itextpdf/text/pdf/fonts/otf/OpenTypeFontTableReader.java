package com.itextpdf.text.pdf.fonts.otf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

/**
 *  
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public abstract class OpenTypeFontTableReader {
    
    protected final RandomAccessFileOrArray rf;
    protected final int tableLocation;
    
    public OpenTypeFontTableReader(String fontFilePath, int tableLocation) throws IOException { 
        this.rf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(fontFilePath));
        this.tableLocation = tableLocation;
    }
    
    /**
     * This is the starting point of the class. A sub-class must call this method to start getting call backs
     * to the {@link #readSubTable(int, int)} method.
     */
    protected final void readLookupListTable() throws IOException {
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
    
    protected abstract void readSubTable(int lookupType, int subTableLocation) throws IOException;
    
    private void readLookupTable(int lookupTableLocation) throws IOException {
        rf.seek(lookupTableLocation);
        int lookupType = rf.readShort();
        System.out.println("lookupType=" + lookupType);

        int lookupFlag = rf.readShort();
        System.out.println("lookupFlag=" + lookupFlag);
        int subTableCount = rf.readShort();
        System.out.println("subTableCount=" + subTableCount);

        List<Integer> subTableOffsets = new ArrayList<Integer>();

        for (int i = 0; i < subTableCount; i++) {
            int subTableOffset = rf.readShort();
            subTableOffsets.add(subTableOffset);
        }

        for (int subTableOffset : subTableOffsets) {
            System.out.println("subTableOffset=" + subTableOffset);
            readSubTable(lookupType, lookupTableLocation + subTableOffset);
        }
    }
    
    protected final List<Integer> readCoverageFormat(int coverageLocation) throws IOException {
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
            throw new UnsupportedOperationException("Invalid coverage format: " + coverageFormat);
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
