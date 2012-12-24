package com.itextpdf.text.pdf.fonts.otf;

/**
 *  
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
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
