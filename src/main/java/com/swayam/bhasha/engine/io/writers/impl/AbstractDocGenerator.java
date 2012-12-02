/*
 * AbstractDocGenerator.java
 *
 * Created on Oct 1, 2008 11:56:24 AM
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
package com.swayam.bhasha.engine.io.writers.impl;

import java.awt.Dimension;
import java.util.List;

import com.swayam.bhasha.model.html.HTMLDocModel;

/**
 * 
 * @author paawak
 */
abstract class AbstractDocGenerator implements WritableDocGenerator {

    List<HTMLDocModel> pageModels;
    Dimension pageDim;

    public void setContent(List<HTMLDocModel> pageModels) {
        this.pageModels = pageModels;
    }

    public void setDimension(Dimension pageDim) {
        this.pageDim = pageDim;
    }

}
