package com.itextpdf.text.pdf.fonts.otf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            readLookUpType_1(subTableLocation);
        } else if (lookupType == 4) {
            readLookUpType_4(subTableLocation);
        } else {
        	System.err.println("The lookupType " + lookupType + " is not yet supported");  
        }
        
    }
    
    private void readLookUpType_1(int lookupTableLocation) throws IOException {
    	rf.seek(lookupTableLocation);
    	int posFormat = rf.readShort();
    	
    	if (posFormat != 1) {
    		System.err.println("The PosFormat " + posFormat + " is not yet supported!");
    	}
    	
        int coverageOffset = rf.readShort();
        int valueFormat = rf.readShort();
        System.out.println("valueFormat=" + valueFormat); 
        
        //check if XPlacement should be read
        if  ((valueFormat & 1) == 1) {
        	int xPlacement = rf.readShort();
        }
        
       //check if YPlacement should be read
        if  ((valueFormat & 2) ==2) {
        	int yPlacement = rf.readShort();
        }
    }
    
    private void readLookUpType_4(int lookupTableLocation) throws IOException {
        rf.seek(lookupTableLocation);
        
        int posFormat = rf.readShort();
        
        if (posFormat != 1) {
            throw new IllegalArgumentException("The posFormat is expected to be 1");
        }
        
        int markCoverageOffset = rf.readShort();
        System.out.println("markCoverageOffset=" + markCoverageOffset); 
        int baseCoverageOffset = rf.readShort();
        System.out.println("baseCoverageOffset=" + baseCoverageOffset); 
        int classCount = rf.readShort();
        System.out.println("classCount=" + classCount); 
        int markArrayOffset = rf.readShort();
        System.out.println("markArrayOffset=" + markArrayOffset); 
        int baseArrayOffset = rf.readShort();
        System.out.println("baseArrayOffset=" + baseArrayOffset); 
        
        List<Integer> markCoverages = readCoverageFormat(lookupTableLocation + markCoverageOffset);
        System.out.println("markCoverages=" + markCoverages.size());
        
        List<Integer> baseCoverages = readCoverageFormat(lookupTableLocation + baseCoverageOffset);
        System.out.println("baseCoverages=" + baseCoverages.size());
        
        readMarkArrayTable(lookupTableLocation + markArrayOffset);
        
        readBaseArrayTable(lookupTableLocation + baseArrayOffset, classCount);
        
    }
    
    private void readMarkArrayTable(int markArrayLocation) throws IOException {
    	rf.seek(markArrayLocation);
    	int markCount = rf.readShort();
    	List<MarkRecord> markRecords = new ArrayList<GlyphPositioningTableReader.MarkRecord>();
    	
    	for (int i = 0; i < markCount; i++) {
    		markRecords.add(readMarkRecord());
    	}
    	
    	for (MarkRecord markRecord : markRecords) {
    		readAnchorTable(markArrayLocation + markRecord.markAnchorOffset);
    	}
    }
    
    private MarkRecord readMarkRecord() throws IOException {
    	int markClass = rf.readShort();
    	int markAnchorOffset = rf.readShort();
    	return new MarkRecord(markClass, markAnchorOffset);
    }
    
    private void readAnchorTable(int anchorTableLocation) throws IOException {
    	rf.seek(anchorTableLocation);
    	int anchorFormat = rf.readShort();
    	
    	if  (anchorFormat != 1) {
    		System.err.println("The extra features of the AnchorFormat " + anchorFormat + " will not be used");
    	}
    	
    	int x = rf.readShort();
    	int y = rf.readShort();
    	
    }
    
    private void readBaseArrayTable(int baseArrayTableLocation, int classCount) throws IOException {
    	rf.seek(baseArrayTableLocation);
    	int baseCount = rf.readShort();
    	Set<Integer> baseAnchors = new HashSet<Integer>();
    	
    	for (int i = 0; i < baseCount; i++) {
    		//read BaseRecord
    		for (int k = 0; k < classCount; k++) {
    			int baseAnchor = rf.readShort();
    			baseAnchors.add(baseAnchor);
    		}
    	}
    	
    	System.out.println(baseAnchors.size()); 
    	
    	for (int baseAnchor : baseAnchors) {
    		readAnchorTable(baseArrayTableLocation + baseAnchor);
    	}
    	
    }
    
    static class MarkRecord {
    	final int markClass;
    	final int markAnchorOffset;
    	
		public MarkRecord(int markClass, int markAnchorOffset) {
			this.markClass = markClass;
			this.markAnchorOffset = markAnchorOffset;
		}
		
    }

}
