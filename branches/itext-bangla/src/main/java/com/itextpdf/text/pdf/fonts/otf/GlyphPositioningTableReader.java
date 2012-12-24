package com.itextpdf.text.pdf.fonts.otf;

import java.io.IOException;
import java.util.List;

/**
 *  
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public class GlyphPositioningTableReader extends OpenTypeFontTableReader {
    
    public GlyphPositioningTableReader(String fontFilePath, int gposTableLocation) throws IOException {
        super(fontFilePath, gposTableLocation);
    }
    
    public void read() throws IOException { 
        readLookupListTable();
    }
    
    @Override
    protected void readSubTable(int lookupType, int subTableLocation) throws IOException {
        
        if (lookupType == 1) {
//            readLookUpType_1();
        } else if (lookupType == 4) {
            readLookUpType_4(subTableLocation);
        } else if (lookupType == 8) {
//            readLookUpType_8();
        }
        
    }
    
    private void readLookUpType_1() throws IOException {
        int coverageOffset = rf.readShort();
        System.out.println("coverageOffset=" + coverageOffset); 
        int valueFormat = rf.readShort();
        System.out.println("valueFormat=" + valueFormat); 
    }
    
    private void readLookUpType_4(int lookupTableLocation) throws IOException {
        rf.seek(lookupTableLocation);
        
        int posFormat = rf.readShort();
        
        if (posFormat != 1) {
            throw new IllegalArgumentException("The posFormat is expected to be 1");
        }
        
        int markCoverageOffset = rf.readShort();
        System.out.println("******markCoverageOffset=" + markCoverageOffset); 
        int baseCoverageOffset = rf.readShort();
        System.out.println("******baseCoverageOffset=" + baseCoverageOffset); 
        int classCount = rf.readShort();
        System.out.println("********classCount=" + classCount); 
        int markArrayOffset = rf.readShort();
        System.out.println("*******markArrayOffset=" + markArrayOffset); 
        int baseArrayOffset = rf.readShort();
        System.out.println("*******baseArrayOffset=" + baseArrayOffset); 
        
        List<Integer> markCoverages = readCoverageFormat(lookupTableLocation + markCoverageOffset);
        System.out.println("markCoverages=" + markCoverages.size());
        
        List<Integer> baseCoverages = readCoverageFormat(lookupTableLocation + baseCoverageOffset);
        System.out.println("baseCoverages=" + baseCoverages.size());
        
    }
    
    private void readLookUpType_8() throws IOException {
        int coverageOffset = rf.readShort();
        System.out.println("coverageOffset=" + coverageOffset); 
        int chainPosRuleSetCount = rf.readShort();
        System.out.println("chainPosRuleSetCount=" + chainPosRuleSetCount); 
    }

}
