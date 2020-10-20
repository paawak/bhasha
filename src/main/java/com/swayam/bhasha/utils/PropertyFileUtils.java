
package com.swayam.bhasha.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;

public class PropertyFileUtils {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    private String fileName = null;

    private Locale locale = null;

    private ResourceBundle propertyFile;

    private final String USER_SETTINGS_FILE_DIR = "bhasha_keymappings_dir/";
    private final String DEF_SETTINGS_PACKAGE = "/keymappings";
    private final String SETTINGS_FILE_PREFIX = "KeyboardSettings";
    private final String DEFAULT_FILE_EXTENSION = ".def";
    private final String USER_FILE_EXTENSION = ".usr";

    /**
     * Creates a new instance of PropertyFileUtils
     */
    public PropertyFileUtils() {
    }

    public PropertyFileUtils(String fileName) {
	propertyFile = ResourceBundle.getBundle(fileName);
	this.fileName = fileName;
    }

    public PropertyFileUtils(String fileName, Locale locale) {

	propertyFile = ResourceBundle.getBundle(fileName, locale);
	this.fileName = fileName;
	this.locale = locale;

    }

    public String getVal(String key, Locale locale) {

	ResourceBundle propertyFile = ResourceBundle.getBundle(fileName, locale);

	return propertyFile.getString(key);
    }

    public String getVal(String key) {
	if (locale == null)
	    return propertyFile.getString(key);
	else
	    return getVal(key, locale);

    }

    /**
     * Reads the property file according to the given locale, and returns raw
     * string
     */
    private String readPropertiesFromFile(Locale currentLocale) {

	URL mappingFileUrl = null;
	InputStream mappingFileStream = null;

	try {
	    mappingFileUrl = getUserKeyMappingFile(currentLocale).toURI().toURL();
	    mappingFileStream = mappingFileUrl.openStream();
	} catch (Exception e) {
	    logger.warn("readPropertiesFromFile:  User settings file not found: reading from the default file.....", e);
	}

	StringBuffer fileContents = new StringBuffer();

	try {

	    if (mappingFileStream == null || mappingFileStream.available() == 0) {
		mappingFileUrl = getDefaultMappingFile(currentLocale);
		try {
		    mappingFileStream = mappingFileUrl.openStream();
		} catch (IOException e) {
		    logger.error("could not read from the default file", e);
		}
	    }

	    int c = 0;
	    while (c != -1) {
		c = mappingFileStream.read();
		fileContents.append((char) c);
	    }
	    mappingFileStream.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

	return fileContents.toString();

    }

    public Map<String, String> getKeyMap(Locale currentLocale, boolean indicKeys) {
	String fileContent = readPropertiesFromFile(currentLocale);
	return getKeyMap(fileContent, indicKeys);
    }

    /**
     * gets key mapping with English keys
     */
    public Map<String, String> getKeyMap(String fileContent, boolean indicKeys) {

	TreeMap<String, String> charMap = new TreeMap<>();

	String[] lines = fileContent.split("\n");
	for (int i = 0; i < lines.length; i++) {
	    String[] keyVal = lines[i].split("=");
	    if (keyVal.length == 2) {
		String[] indicChars = keyVal[1].split(" ");
		String completeIndicString = "";
		// convert the raw hex string to a char
		for (int index = 0; index < indicChars.length; index++) {
		    try {
			completeIndicString += (char) Integer.parseInt(indicChars[index], 16);
		    } catch (NumberFormatException e) {
		    }
		}

		if (indicKeys) {
		    // index of EnglishChar = 0
		    // index of IndicChar = 1
		    // in the property file it exists as EnglishChar ->
		    // IndicChar
		    // i.e, EnglishChar is the key: we need to swap it here
		    // for duplicate IndicChar, we need to separate the two
		    // EnglishChars
		    // by comma(,)

		    if (charMap.containsKey(completeIndicString)) {// this
								   // IndicChar
								   // already
								   // exists as
								   // key?
			// overwrite the existing value by a comma separated
			// [old value], [new value]
			charMap.put(completeIndicString, charMap.get(completeIndicString) + "," + keyVal[0]);
		    } else {
			charMap.put(completeIndicString, keyVal[0]);
		    }
		} else {
		    // index of EnglishChar = 0
		    // index of IndicChar = 1
		    // in the property file it exists as EnglishChar ->
		    // IndicChar
		    // i.e, EnglishChar is the key
		    charMap.put(keyVal[0], completeIndicString);
		}
	    }
	}

	return charMap;
    }

    /**
     * writes the given TreeMap as key->val in the user file of the particular
     * locale. Note that the given TreeMap has IndicChars as the keys. But this
     * file will have English Chars as the keys.
     * 
     * @parameters Map, Locale
     * @return true on successful writing, false otherwise
     */
    public boolean writeKeyMapToUserFile(Map<String, String> userMap, Locale locale) {
	boolean written = false;
	// get the Map as inverted key->val in a StringBuffer
	Set<String> keys = userMap.keySet();
	Iterator<String> keyIterator = keys.iterator();
	StringBuffer fileContents = new StringBuffer(1);
	while (keyIterator.hasNext()) {
	    String key = keyIterator.next().toString();
	    String val = userMap.get(key).toString();
	    // convert the key, which is a hex char, to a hex int
	    String indicVal = getHexCodeFromIndics(key);
	    // the val contains comma separated values of english chars
	    String[] englishKeys = val.split(",");
	    for (int i = 0; i < englishKeys.length; i++) {
		fileContents.append(englishKeys[i].trim()).append("=").append(indicVal).append("\n");
	    }
	}

	try {
	    FileOutputStream fos = new FileOutputStream(getUserKeyMappingFile(locale));
	    fos.write(fileContents.toString().getBytes());
	    written = true;
	    fos.close();
	} catch (IOException e) {
	    logger.error("writeKeyMapToUserFile: EXCEPTION: could not write to file", e);
	    e.printStackTrace();
	}

	return written;
    }

    /**
     * gets the absolute location of keymappings folder located in the same
     * folder as the jar file
     */
    private URL getDefaultMappingFile(Locale locale) {

	String path = DEF_SETTINGS_PACKAGE + "/" + getKeyMapFileName(locale, DEFAULT_FILE_EXTENSION);

	return getClass().getResource(path);

    }

    private File getUserKeyMappingFile(Locale locale) {

	File userKeyMappingsDir = new File(System.getProperty("user.dir"), USER_SETTINGS_FILE_DIR);

	if (!userKeyMappingsDir.exists()) {
	    if (userKeyMappingsDir.mkdir()) {
		logger.warn("Could not create user mapping directory " + userKeyMappingsDir.getAbsolutePath());
	    }
	}

	File userKeyMappingsFile = new File(userKeyMappingsDir, getKeyMapFileName(locale, USER_FILE_EXTENSION));

	if (!userKeyMappingsFile.exists()) {
	    try {
		userKeyMappingsFile.createNewFile();
	    } catch (IOException e) {
		logger.warn("Could not create user mapping file", e);
	    }
	}

	return userKeyMappingsFile;

    }

    private String getKeyMapFileName(Locale locale, String extension) {

	return SETTINGS_FILE_PREFIX + "_" + locale.getLanguage() + "_" + locale.getCountry() + extension;

    }

    /**
     * deletes the .usr file for the given locale
     * 
     * @param locale:
     *            the locale of file to delete
     * @return boolean: user file deleted successfully?
     */
    public boolean deleteUserSettingsFile(Locale locale) {
	File userFile = getUserKeyMappingFile(locale);
	return userFile.delete();
    }

    /**
     * converts a given (Indic) String into Hex-Codes separated by space
     */
    public static String getHexCodeFromIndics(String indicString) {
	if (indicString == null)
	    return "IndicIsNull";
	StringBuffer indicVal = new StringBuffer(1);
	for (int i = 0; i < indicString.length(); i++) {
	    try {
		indicVal.append(Integer.toHexString(indicString.charAt(i))).append(" ");
	    } catch (NumberFormatException e) {
	    }
	}
	return indicVal.toString();
    }

}
