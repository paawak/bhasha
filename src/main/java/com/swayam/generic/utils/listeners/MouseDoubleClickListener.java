/*
 * MouseDoubleClickListener.java
 *
 * Created on Aug 19, 2006, 7:11:52 PM
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

package com.swayam.generic.utils.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

/**
 * 
 * @author paawak
 */
public abstract class MouseDoubleClickListener extends MouseAdapter {
	
	/**
	 * Time-out for double-click in milli-seconds
	 */
	private final int doubleClickTimeOutMillis;
	
	private long previousClickTime = -1000;
	
	/**
	 * Creates an instance of MouseDoubleClickListener with a default time-out of
	 * 300 millis.
	 *
	 */
	public MouseDoubleClickListener() {
		this(300);
	}
	
	public MouseDoubleClickListener(int doubleClickTimeOutMillis) {
		this.doubleClickTimeOutMillis = doubleClickTimeOutMillis;
	}
	
	public final void mouseClicked(MouseEvent me) {
		long currentClickTime = me.getWhen();
		
		//START DEBUG
//		GregorianCalendar cal = new GregorianCalendar(); 
//		cal.setTimeInMillis(currentClickTime);
//		System.out.println(".mouseClicked()  sec = " + cal.get(Calendar.SECOND)
//				+ "\tmilli = " + cal.get(Calendar.MILLISECOND));
		//END DEBUG
		
		if (currentClickTime - previousClickTime <= doubleClickTimeOutMillis) {
			dispatchDoubleClickEvent(me);
		}
		
		previousClickTime = currentClickTime;
	}
	
	private void dispatchDoubleClickEvent(final MouseEvent me) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				mouseDoubleClicked(me);
			}
			
		});
	}
	
	public abstract void mouseDoubleClicked(MouseEvent me);

}

