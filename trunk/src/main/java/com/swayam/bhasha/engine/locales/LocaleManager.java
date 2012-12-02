/*
 * LocaleManager.java
 *
 * Created on Oct 5, 2008 6:42:08 PM
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
package com.swayam.bhasha.engine.locales;

import java.util.Locale;

/**
 * Responsible for managing fonts for given language/s.
 * 
 * @author paawak
 */
public interface LocaleManager {

    Locale getLocale();

    String[] getAvailableFonts();

    String getDefaultFont();

    void setDefaultFont(String font);

    boolean isActive();

    void setActive(boolean active);

}
