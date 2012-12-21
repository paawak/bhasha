package com.itextpdf.text.pdf;

/**
 *  
 * @author paawak
 */
public class Glyph {
    
    /**
     * The <i>code</i> or <i>id</i> by which this is represented in the Font File
     */
    public final int code;
    
    /**
     * The normalized width of this Glyph.
     */
    public final int width;
    
    /**
     * The Unicode text represented by this Glyph
     */
    public final String chars;
    
    public Glyph(int code, int width, String chars) {
        this.code = code;
        this.width = width;
        this.chars = chars;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chars == null) ? 0 : chars.hashCode());
        result = prime * result + code;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Glyph other = (Glyph) obj;
        if (chars == null) {
            if (other.chars != null)
                return false;
        } else if (!chars.equals(other.chars))
            return false;
        if (code != other.code)
            return false;
        if (width != other.width)
            return false;
        return true;
    }



    @Override
    public String toString() {
        return Glyph.class.getSimpleName() + " [id=" + code + ", width=" + width + ", chars=" + chars + "]";
    }
    
}
