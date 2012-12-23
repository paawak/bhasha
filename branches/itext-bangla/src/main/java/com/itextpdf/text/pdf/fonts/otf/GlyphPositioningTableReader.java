package com.itextpdf.text.pdf.fonts.otf;

import java.io.IOException;

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
    protected void readLookupTable(int lookupTableLocation) throws IOException {
        rf.seek(lookupTableLocation);
        int lookupType = rf.readShort();
        System.out.println("*********lookupType=" + lookupType);
        
        if (lookupType == 1) {
            readLookUpTypeOne();
        }
        
    }
    
    private void readLookUpTypeOne() throws IOException {
        int coverage = rf.readShort();
        System.out.println("coverage=" + coverage); 
        int valueFormat = rf.readShort();
        System.out.println("valueFormat=" + valueFormat); 
    }

}
