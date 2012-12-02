/*
 * HTMLDoc.java
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

package com.swayam.bhasha.model.html;

import java.util.List;

/**
 * Models a javax.swing.text.Document in HTML.
 * 
 * @author paawak
 *
 */

public interface HTMLDocModel {
	
	List<Para> getParaList();

}

