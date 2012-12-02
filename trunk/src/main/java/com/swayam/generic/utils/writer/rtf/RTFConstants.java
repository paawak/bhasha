package com.swayam.generic.utils.writer.rtf;


/**
 * This has all rtf-specific constants
 * @author paawak
 */
public interface RTFConstants {
    String DOCUMENT_HEADER = "{\\rtf1\\ansi\n";
    /**
     *this contains the entire command to display page nos.
     *put this either in \header or \footer alongwith other text
     */
    String INSERT_PAGE_NUMS = "{\\field{\\*\\fldinst PAGE}}";
    String HEADER = "{\\header\n";
    String FOOTER = "{\\footer\n";
    String FONT_TBL = "{\\fonttbl";
    String FONT = "\\f";
    String FONT_SIZE = "\\fs";
    /**control word for font family: Unknown or default fonts*/
    String FNIL = "\\fnil";
    String COLOR_TBL = "{\\colortbl";
    String FOREGROUND_COLOR = "\\cf";
    String HIGHLIGHT_COLOR = "\\highlight";
    String RED = "\\red";
    String GREEN = "\\green";
    String BLUE = "\\blue";
    String PARA = "\\par";
    String BOLD = "\\b";
    String ITALIC = "\\i";
    String UNDERLINE = "\\ul";
    String ALIGN_CENTER = "\\qc";
    String ALIGN_LEFT = "\\ql";
    String ALIGN_RIGHT = "\\qr";
    String TABLE_ROW_START = "\\trowd\n";
    String TABLE_ROW_END = "\\row";
    /**use this to remove all previous styles*/
    String PARD_PLAIN = "\\pard\\plain";
    String TABLE_CELL_START = PARD_PLAIN + "\\intbl";
    String TABLE_CELL_END = "\\cell";
    String TABLE_CELL_X = TABLE_CELL_END + "x";
    /**put this along with any Bnagla text for proper display of Bangla chars*/
    String BANGLA_LANG_ID = "\\lang1093";
    /**put this along with any Hindi text for proper display of Hindi chars*/
    String HINDI_LANG_ID = "\\lang1081";  
    String INDIC_CHAR_PREFIX = "\\u";
	String INDIC_CHAR_SUFFIX = "\\'3f";

}
