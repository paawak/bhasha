/*
 * PageListener.java
 *
 * Created on March 7, 2006, 1:37 AM
 *
 *
 * Copyright (c) 2002 - 2006 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 *
 */

package com.swayam.bhasha.utils.page;

/**
 *
 * @author paawak
 */
public interface PageListener {
    
    void endOfPageReached(PageEvent pageEvent);
    void beginOfPageReached(PageEvent pageEvent);
    void textOverflowHappened(PageEvent pageEvent);
    void pageAdded(PageEvent pageEvent);
    void pageDeleted(PageEvent pageEvent);

}
