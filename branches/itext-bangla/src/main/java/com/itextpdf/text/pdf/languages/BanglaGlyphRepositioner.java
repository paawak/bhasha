package com.itextpdf.text.pdf.languages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public class BanglaGlyphRepositioner extends IndicGlyphRepositioner {
	
	private static final String[] CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1 = new String[] {
			"\u09BF", "\u09C7", "\u09C8", "\u09CB" };
	
	private final Map<Integer, int[]> cmap31;

	public BanglaGlyphRepositioner(Map<Integer, int[]> cmap31) {
		this.cmap31 = cmap31;
	}

	@Override
	List<String> getCharactersToBeShiftedLeftByOnePosition() {
		return Arrays.asList(CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1);
	}

}
