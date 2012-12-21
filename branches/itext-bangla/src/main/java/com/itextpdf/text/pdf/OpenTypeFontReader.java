package com.itextpdf.text.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

/**
 * 
 * @author paawak
 */
public class OpenTypeFontReader {

    private static final Logger LOG = LoggerFactory.getLogger(OpenTypeFontReader.class);

    private final RandomAccessFileOrArray rf;
    private final int[] glyphWidthByIndex;
    private final Map<Integer, Character> glyphToCharacterMap;
    private Map<Integer, List<Integer>> rawLigatureSubstitutionMap;

    public OpenTypeFontReader(String fontFilePath, Map<Integer, Character> glyphToCharacterMap, int[] glyphWidthByIndex) throws IOException {
        rf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(fontFilePath));
        this.glyphWidthByIndex = glyphWidthByIndex;
        this.glyphToCharacterMap = glyphToCharacterMap;
    }

    public Map<Integer, List<Integer>> getRawLigatureSubstitutionMap(int gsubTableLocation) throws IOException {
        readGsubTable(gsubTableLocation);
        return Collections.unmodifiableMap(rawLigatureSubstitutionMap);
    }
    
    public Map<String, Glyph> getGlyphSubstitutionMap(int gsubTableLocation) throws IOException {
        
        readGsubTable(gsubTableLocation);
        
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
    
    private void readGsubTable(int gsubTableLocation) throws IOException {

        rawLigatureSubstitutionMap = new HashMap<Integer, List<Integer>>();
        
        rf.seek(gsubTableLocation);
        // 32 bit signed
        int version = rf.readInt();
        // 16 bit unsigned
        int scriptListOffset = rf.readUnsignedShort();
        int featureListOffset = rf.readUnsignedShort();
        int lookupListOffset = rf.readUnsignedShort();

        LOG.debug("version=" + version);
        LOG.debug("scriptListOffset=" + scriptListOffset);
        LOG.debug("featureListOffset=" + featureListOffset);
        LOG.debug("lookupListOffset=" + lookupListOffset);

        LOG.debug("************************************");

        readLookupListTable(gsubTableLocation + lookupListOffset);

        LOG.debug("************************************");

        // Map<String, Integer> scriptRecords =
        // readScriptListTable(gsubTableLocationOffset + scriptListOffset);
        //
        // // read the Script tables
        // for (String scriptName : scriptRecords.keySet()) {
        // readScriptTable(scriptRecords.get(scriptName));
        // }

    }

    private void readLookupListTable(int lookupListTableLocation) throws IOException {
        rf.seek(lookupListTableLocation);
        final int lookupCount = rf.readShort();
        LOG.debug("lookupCount=" + lookupCount);

        List<Integer> lookupTableOffsets = new ArrayList<Integer>();

        for (int i = 0; i < lookupCount; i++) {
            int lookupTableOffset = rf.readShort();
            lookupTableOffsets.add(lookupTableOffset);
        }

        for (int lookupTableOffset : lookupTableOffsets) {
            LOG.debug("lookupTableOffset=" + lookupTableOffset);
            LOG.debug("--------------------");
            readLookupTable(lookupListTableLocation + lookupTableOffset);
            LOG.debug("--------------------");
        }

    }

    private void readLookupTable(int lookupTableLocation) throws IOException {
        rf.seek(lookupTableLocation);
        int lookupType = rf.readShort();
        LOG.debug("lookupType=" + lookupType);

        // right now i am handling only the LookupType4: Ligature Substitution
        // Subtable
        if (lookupType == 4) {
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

    private List<Integer> readCoverageFormat(int coverageLocation) throws IOException {
        rf.seek(coverageLocation);
        int coverageFormat = rf.readShort();

        List<Integer> glyphIds;

        if (coverageFormat == 1) {
            int glyphCount = rf.readShort();

            LOG.debug("^^^^^^^^^coverageCount=" + glyphCount);

            glyphIds = new ArrayList<Integer>(glyphCount);

            for (int i = 0; i < glyphCount; i++) {
                int coverageGlyphId = rf.readShort();
                LOG.debug("############################coverageGlyphId=" + coverageGlyphId);
                glyphIds.add(coverageGlyphId);
            }

        } else {

            int rangeCount = rf.readShort();

            LOG.debug("rangeCount=" + rangeCount);

            glyphIds = new ArrayList<Integer>();

            for (int i = 0; i < rangeCount; i++) {
                readRangeRecord(glyphIds);
            }

        }

        return Collections.unmodifiableList(glyphIds);
    }

    private void readRangeRecord(List<Integer> glyphIds) throws IOException {
        int startGlyphId = rf.readShort();
        LOG.debug("startGlyphId=" + startGlyphId);
        int endGlyphId = rf.readShort();
        LOG.debug("endGlyphId=" + endGlyphId);
        int startCoverageIndex = rf.readShort();
        LOG.debug("startCoverageIndex=" + startCoverageIndex);

        for (int glyphId = startGlyphId; glyphId <= endGlyphId; glyphId++) {
            glyphIds.add(glyphId);
        }

    }

    // private Map<String, Integer> readScriptListTable(final int
    // scriptListTableLocationOffset) throws IOException {
    // rf.seek(scriptListTableLocationOffset);
    // // Number of ScriptRecords
    // int scriptCount = rf.readShort();
    //
    // Map<String, Integer> scriptRecords = new HashMap<String,
    // Integer>(scriptCount);
    //
    // LOG.debug("scriptCount=" + scriptCount);
    //
    // for (int scriptRecord = 1; scriptRecord <= scriptCount; scriptRecord++) {
    // readScriptRecord(scriptListTableLocationOffset, scriptRecords);
    // }
    //
    // return scriptRecords;
    //
    // }
    //
    // private void readScriptRecord(final int scriptListTableLocationOffset,
    // Map<String, Integer> scriptRecords) throws IOException {
    // String scriptTag = readStandardString(4);
    // LOG.debug("scriptTag=" + scriptTag);
    //
    // int scriptOffset = rf.readShort();
    // LOG.debug("scriptOffset=" + scriptOffset);
    //
    // scriptRecords.put(scriptTag, scriptListTableLocationOffset +
    // scriptOffset);
    //
    // }
    //
    // private void readScriptTable(final int scriptTableLocationOffset) throws
    // IOException {
    // rf.seek(scriptTableLocationOffset);
    // int defaultLangSys = rf.readShort();
    // LOG.debug("defaultLangSys=" + defaultLangSys);
    // int langSysCount = rf.readShort();
    // LOG.debug("langSysCount=" + langSysCount);
    //
    // for (int langSysRecord = 1; langSysRecord <= langSysCount;
    // langSysRecord++) {
    // readLangSysRecord();
    // }
    // }
    //
    // private void readLangSysRecord() throws IOException {
    // String langSysTag = readStandardString(4);
    // LOG.debug("langSysTag=" + langSysTag);
    // int langSys = rf.readShort();
    // LOG.debug("langSys=" + langSys);
    // }

}
