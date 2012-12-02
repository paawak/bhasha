package com.swayam.bhasha;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class LigatureTest {

    private static final String BANGLA_TEXT = "ক্ষ";

    @Test
    public void testApp() throws FontFormatException, IOException {
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("/usr/share/fonts/lohit-bengali/Lohit-Bengali.ttf")).deriveFont(40f);
        FontRenderContext context = new FontRenderContext(new AffineTransform(), true, true);
        GlyphVector glyphVector = font.layoutGlyphVector(context, BANGLA_TEXT.toCharArray(), 0, BANGLA_TEXT.length(), Font.LAYOUT_LEFT_TO_RIGHT);

        int glyphNum = glyphVector.getNumGlyphs();

        float[] glyphPositions = glyphVector.getGlyphPositions(0, glyphNum, null);

        for (float pos : glyphPositions) {
            System.out.println(pos);
        }

        Rectangle2D rect = glyphVector.getLogicalBounds();
        System.out.println(rect);

        int[] charIndices = glyphVector.getGlyphCharIndices(0, glyphNum, null);

        System.out.println("************************");

        for (int charIndex : charIndices) {
            System.out.println(charIndex);
        }
    }
}
