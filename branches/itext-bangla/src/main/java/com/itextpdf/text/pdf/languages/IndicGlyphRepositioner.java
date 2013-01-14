package com.itextpdf.text.pdf.languages;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.Glyph;

/**
 * 
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
abstract class IndicGlyphRepositioner implements GlyphRepositioner {

	public List<Glyph> repositionGlyphs(Glyph[] glyphList) {

		List<Glyph> repositionedGlyphs = new ArrayList<Glyph>(glyphList.length);

		for (int i = 0; i < glyphList.length; i++) {
			Glyph glyph = glyphList[i];
			Glyph nextGlyph = getNextGlyph(glyphList, i);

			if ((nextGlyph != null)
					&& getCharactersToBeShiftedLeftByOnePosition().contains(
							nextGlyph.chars)) {
				repositionedGlyphs.add(nextGlyph);
				repositionedGlyphs.add(glyph);
				i++;
				continue;
			} else {
				repositionedGlyphs.add(glyph);
			}
		}

		return repositionedGlyphs;

	}

	abstract List<String> getCharactersToBeShiftedLeftByOnePosition();

	private Glyph getNextGlyph(Glyph[] glyphs, int currentIndex) {
		if (currentIndex + 1 < glyphs.length) {
			return glyphs[currentIndex + 1];
		} else {
			return null;
		}
	}

}
