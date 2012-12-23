package com.itextpdf.text.pdf.fonts.otf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.Glyph;

/**
 * <p>
 * Parses an OpenTypeFont file and reads the Glyph Substitution Table. This table governs how two or more Glyphs should be merged
 * to a single Glyph. This is especially useful for Asian languages like Bangla, Hindi, etc.
 * </p>
 * <p>
 * This has been written according to the OPenTypeFont specifications. This may be found <a href="http://www.microsoft.com/typography/otspec/gsub.htm">here</a>.
 * </p>
 * 
 * @author <a href="mailto:paawak@gmail.com">Palash Ray</a>
 */
public class GlyphSubstitutionTableReader extends OpenTypeFontTableReader {

    private static final Logger LOG = LoggerFactory.getLogger(GlyphSubstitutionTableReader.class);

    private final int[] glyphWidthByIndex;
    private final Map<Integer, Character> glyphToCharacterMap;
    private Map<Integer, List<Integer>> rawLigatureSubstitutionMap;

    public GlyphSubstitutionTableReader(String fontFilePath, int gsubTableLocation, Map<Integer, Character> glyphToCharacterMap, int[] glyphWidthByIndex) throws IOException {
        super(fontFilePath, gsubTableLocation);
        this.glyphWidthByIndex = glyphWidthByIndex;
        this.glyphToCharacterMap = glyphToCharacterMap;
    }

    public Map<String, Glyph> getGlyphSubstitutionMap() throws IOException {
        
        readGsubTable();
        
        Map<String, Glyph> glyphSubstitutionMap = new HashMap<String, Glyph>();
        
        for (Integer glyphIdToReplace : rawLigatureSubstitutionMap.keySet()) {
            List<Integer> constituentGlyphs = rawLigatureSubstitutionMap.get(glyphIdToReplace);
            StringBuilder chars = new StringBuilder(constituentGlyphs.size());
            
            for (Integer constituentGlyphId : constituentGlyphs) {
                chars.append(getTextFromGlyph(constituentGlyphId, glyphToCharacterMap));
            }
            
            Glyph glyph = new Glyph(glyphIdToReplace, glyphWidthByIndex[glyphIdToReplace], chars.toString());
            
            glyphSubstitutionMap.put(glyph.chars, glyph);
        }
        
        return Collections.unmodifiableMap(glyphSubstitutionMap);
        
    }
    
    private String getTextFromGlyph(int glyphId, Map<Integer, Character> glyphToCharacterMap) {
        
        StringBuilder chars = new StringBuilder(1);
        
        Character c = glyphToCharacterMap.get(glyphId);
        
        if (c == null) {
            // it means this represents a compound glyph
            List<Integer> constituentGlyphs = rawLigatureSubstitutionMap.get(glyphId);
            
            if (constituentGlyphs == null || constituentGlyphs.isEmpty()) {
                throw new IllegalArgumentException("No corresponding character or simple glyphs found for GlyphID=" + glyphId);
            }
            
            for (int constituentGlyphId : constituentGlyphs) {
                chars.append(getTextFromGlyph(constituentGlyphId, glyphToCharacterMap));
            }
            
        } else {
            chars.append(c.charValue());
        }
        
        return chars.toString();
    }
    
    private void readGsubTable() throws IOException {
        rawLigatureSubstitutionMap = new HashMap<Integer, List<Integer>>();
        readLookupListTable();
    }

    @Override
    protected void readLookupTable(int lookupTableLocation) throws IOException {
        rf.seek(lookupTableLocation);
        int lookupType = rf.readShort();
        LOG.debug("lookupType=" + lookupType);

        if (lookupType == 1) {// LookupType 1: Single Substitution Subtable
            
            int coverage = rf.readShort();
            LOG.debug("coverage=" + coverage);
            
            int deltaGlyphID = rf.readShort();
            LOG.debug("deltaGlyphID=" + deltaGlyphID);
            
            List<Integer> coverageGlyphIds = readCoverageFormat(lookupTableLocation + coverage);
            
            for (int coverageGlyphId : coverageGlyphIds) {
                int substituteGlyphId = coverageGlyphId + deltaGlyphID;
                rawLigatureSubstitutionMap.put(substituteGlyphId, Arrays.asList(coverageGlyphId)); 
            }
            
        } else if (lookupType == 4) {// LookupType4: Ligature Substitution Subtable
            
            int lookupFlag = rf.readShort();
            LOG.debug("lookupFlag=" + lookupFlag);
            int subTableCount = rf.readShort();
            LOG.debug("subTableCount=" + subTableCount);

            List<Integer> subTableOffsets = new ArrayList<Integer>();

            for (int i = 0; i < subTableCount; i++) {
                int subTableOffset = rf.readShort();
                subTableOffsets.add(subTableOffset);
            }

            for (int subTableOffset : subTableOffsets) {
                LOG.debug("subTableOffset=" + subTableOffset);
                LOG.debug("^^^^^^^^^^^^^");
                readLigatureSubstitutionSubtable(lookupTableLocation + subTableOffset);
                LOG.debug("^^^^^^^^^^^^^");
            }
        } else {
            System.err.println("The lookup type " + lookupType + " is not yet handled");
        }

    }

    private void readLigatureSubstitutionSubtable(int ligatureSubstitutionSubtableLocation) throws IOException {
        rf.seek(ligatureSubstitutionSubtableLocation);
        int substFormat = rf.readShort();
        LOG.debug("substFormat=" + substFormat);

        if (substFormat != 1) {
            throw new IllegalArgumentException("The expected SubstFormat is 1");
        }

        int coverage = rf.readShort();
        LOG.debug("coverage=" + coverage);

        int ligSetCount = rf.readShort();
        LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^ligSetCount=" + ligSetCount);

        List<Integer> ligatureOffsets = new ArrayList<Integer>(ligSetCount);

        for (int i = 0; i < ligSetCount; i++) {
            int ligatureOffset = rf.readShort();
            ligatureOffsets.add(ligatureOffset);
        }

        LOG.debug("::::::::::::::::::::::::::::::::::");

        List<Integer> coverageGlyphIds = readCoverageFormat(ligatureSubstitutionSubtableLocation + coverage);

        if (ligSetCount != coverageGlyphIds.size()) {
            throw new IllegalArgumentException("According to the OpenTypeFont specifications, the coverage count should be equal to the no. of LigatureSetTables");
        }

        for (int i = 0; i < ligSetCount; i++) {

            int coverageGlyphId = coverageGlyphIds.get(i);
            int ligatureOffset = ligatureOffsets.get(i);
            LOG.debug("ligatureOffset=" + ligatureOffset);
            readLigatureSetTable(ligatureSubstitutionSubtableLocation + ligatureOffset, coverageGlyphId);
        }

    }

    private void readLigatureSetTable(int ligatureSetTableLocation, int coverageGlyphId) throws IOException {
        rf.seek(ligatureSetTableLocation);
        int ligatureCount = rf.readShort();
        LOG.debug("ligatureCount=" + ligatureCount);

        List<Integer> ligatureOffsets = new ArrayList<Integer>(ligatureCount);

        for (int i = 0; i < ligatureCount; i++) {
            int ligatureOffset = rf.readShort();
            ligatureOffsets.add(ligatureOffset);
        }

        for (int ligatureOffset : ligatureOffsets) {
            readLigatureTable(ligatureSetTableLocation + ligatureOffset, coverageGlyphId);
        }
    }

    private void readLigatureTable(int ligatureTableLocation, int coverageGlyphId) throws IOException {
        rf.seek(ligatureTableLocation);
        int ligGlyph = rf.readShort();
        LOG.debug("@@@@@@@@@@@@@@ ligGlyph=" + ligGlyph);
        int compCount = rf.readShort();

        List<Integer> glyphIdList = new ArrayList<Integer>();

        glyphIdList.add(coverageGlyphId);

        for (int i = 0; i < compCount - 1; i++) {
            int glyphId = rf.readShort();
            glyphIdList.add(glyphId);
            LOG.debug("############################glyphId=" + glyphId);
        }

        rawLigatureSubstitutionMap.put(ligGlyph, glyphIdList);
    }

}
