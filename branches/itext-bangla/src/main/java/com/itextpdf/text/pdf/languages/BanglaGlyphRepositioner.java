package com.itextpdf.text.pdf.languages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.pdf.Glyph;

/**
 * 
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public class BanglaGlyphRepositioner extends IndicGlyphRepositioner {
	
	private static final String[] CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1 = new String[] {
			"\u09BF", "\u09C7", "\u09C8"};
	
	private final Map<Integer, int[]> cmap31;
	private final Map<String, Glyph> glyphSubstitutionMap;

	public BanglaGlyphRepositioner(Map<Integer, int[]> cmap31, Map<String, Glyph> glyphSubstitutionMap) {
		this.cmap31 = cmap31;
		this.glyphSubstitutionMap = glyphSubstitutionMap;
	}
	
	@Override
	public void repositionGlyphs(List<Glyph> glyphList) {
		
		super.repositionGlyphs(glyphList);
		
		for (int i = 0; i < glyphList.size(); i++) {
			Glyph glyph = glyphList.get(i);
			
			if (glyph.chars.equals("\u09CB")) {
				handleOKaarAndOUKaar(i, glyphList, '\u09C7', '\u09BE');
			} /*else if (glyph.chars.equals("\u09CC")) {
				handleOKaarAndOUKaar(i, glyphList, '\u09C7', '\u09D7');
			}*/
		}
	}

	@Override
	public List<String> getCharactersToBeShiftedLeftByOnePosition() {
		return Arrays.asList(CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1);
	}
	
	private void handleOKaarAndOUKaar(int currentIndex, List<Glyph> glyphList, char first, char second) {
        Glyph g1 = getGlyph(first);
        Glyph g2 = getGlyph(second);
        glyphList.set(currentIndex, g2);
        glyphList.add(currentIndex - 1, g1);
	}
	
	private Glyph getGlyph(char c) {
		
		Glyph glyph = glyphSubstitutionMap.get(String.valueOf(c));
		
		if (glyph != null) {
			return glyph;
		}
		
		int[] metrics = cmap31.get(Integer.valueOf(c));
        int glyphCode = metrics[0];
        int glyphWidth = metrics[1];
        return new Glyph(glyphCode, glyphWidth, String.valueOf(c));
	}

}
