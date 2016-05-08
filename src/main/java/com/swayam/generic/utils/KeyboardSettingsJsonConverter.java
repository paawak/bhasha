package com.swayam.generic.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;
import java.util.Properties;

public class KeyboardSettingsJsonConverter {

    private final Properties keyboardSettings;

    public KeyboardSettingsJsonConverter(Properties keyboardSettings) {
	this.keyboardSettings = keyboardSettings;
    }

    public void writeAsJson(Writer writer) throws IOException {

	try (BufferedWriter bufferedWriter = new BufferedWriter(writer);) {
	    for (Entry<Object, Object> entry : keyboardSettings.entrySet()) {
		StringBuilder sb = new StringBuilder(20);
		sb.append('"').append(entry.getKey()).append('"').append(" : ").append('"');
		String unicodeStrings = (String) entry.getValue();
		for (String singleUnicodeChar : unicodeStrings.split("\\s")) {
		    if (singleUnicodeChar.length() != 3) {
			throw new IllegalArgumentException("Expecting a length of 3 only, but found " + singleUnicodeChar.length());
		    }
		    sb.append("\\u0").append(singleUnicodeChar);
		}
		sb.append('"').append(",\n");
		bufferedWriter.write(sb.toString());
	    }
	}

    }

    public static void main(String[] a) throws IOException {
	Properties keyboardSettingsProps = new Properties();
	keyboardSettingsProps.load(KeyboardSettingsJsonConverter.class.getResourceAsStream("/keymappings/KeyboardSettings_bn_IN.def"));
	new KeyboardSettingsJsonConverter(keyboardSettingsProps).writeAsJson(new FileWriter("indic_mappings.js"));
    }

}
