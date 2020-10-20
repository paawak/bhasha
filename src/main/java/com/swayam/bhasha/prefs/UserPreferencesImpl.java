/*
 * UserPreferencesImpl.java
 *
 * Created on Sep 27, 2008 3:37:03 AM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.swayam.bhasha.prefs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.slf4j.Logger;

/**
 * 
 * @author paawak
 */
public class UserPreferencesImpl implements UserPreferences, Serializable {

    private static final long serialVersionUID = -5738130059795606595L;

    private static final String PREFERENCE_NODE = "/javaapp/swayam/bhasha/user/preference";

    private static final String PREFERENCE_KEY = UserPreferencesImpl.class.getSimpleName();

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserPreferencesImpl.class);

    private Locale defaultLanguage;

    private int preferredFontSize;

    private Map<Locale, String> languageToFontMap;

    private transient Locale[] preferredLanguages;

    public String getDefaultFontName(Locale language) {
        return languageToFontMap.get(language);
    }

    public Locale getDefaultLanguage() {
        return defaultLanguage;
    }

    public int getPreferredFontSize() {
        return preferredFontSize;
    }

    public Locale[] getPreferredLanguages() {

        if (preferredLanguages == null) {
            preferredLanguages = languageToFontMap.keySet().toArray(new Locale[0]);
        }

        return preferredLanguages;
    }

    public UserPreferences load() {

        UserPreferences prefs = null;
        Preferences userPrefs = Preferences.userRoot().node(PREFERENCE_NODE);
        byte[] bytes = userPrefs.getByteArray(PREFERENCE_KEY, null);

        if (bytes != null) {
            try {
                // de-serialize
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(inputStream);

                prefs = (UserPreferences) ois.readObject();
            } catch (Exception e) {
                log.error("Error while loading preferences", e);
            }
        }

        return prefs;
    }

    public void save() {
        // serialize
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(this);
            oos.close();

            byte[] bytes = outputStream.toByteArray();
            Preferences userPrefs = Preferences.userRoot().node(PREFERENCE_NODE);
            userPrefs.putByteArray(PREFERENCE_KEY, bytes);

        } catch (IOException e) {
            log.error("Error while saving preferences", e);
        }

    }

    public boolean reset() {

        Preferences userPrefs = Preferences.userRoot().node(PREFERENCE_NODE);

        try {
            userPrefs.removeNode();
            return true;
        } catch (BackingStoreException e) {
            log.error("Error while deleting preferences", e);
            return false;
        }

    }

    void setLanguageToFontMap(Map<Locale, String> languageToFontMap) {
        this.languageToFontMap = languageToFontMap;
    }

    void setDefaultLanguage(Locale defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    void setPreferredFontSize(int preferredFontSize) {
        this.preferredFontSize = preferredFontSize;
    }

}
