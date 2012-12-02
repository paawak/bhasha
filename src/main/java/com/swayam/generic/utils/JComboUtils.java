/*
 * JComboUtils.java
 *
 * Created on January 14, 2003, 12:35 PM
 *
 * Modified on December 15,2003, 03:03 AM
 * the method removeComboElements() deleted.
 * all error messages in the exception block removed
 **/

package com.swayam.generic.utils;

/**
 *
 * @author  paawak
 */

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Collection;
import javax.swing.JComboBox;

import org.apache.log4j.Logger;

public class JComboUtils {
	
	private static final Logger log = Logger.getLogger(JComboUtils.class);
    
    public static void fillComboWithNewArray(JComboBox combo,Object[] array){
        
        if(combo == null
                || array == null){
        	log.warn("JComboUtils.fillComboWithNewArray: got null object. Returning....");
            return ;
        }
        
        //get all registered listeners
        ActionListener[] actionListeners = combo.getActionListeners();
        ItemListener[] itemListeners = combo.getItemListeners();
                
        //remove the listeners
        if(actionListeners != null){
            for(int i=0; i<actionListeners.length; i++){
                combo.removeActionListener(actionListeners[i]);
            }
        }
        
        if(itemListeners != null){
            for(int i=0; i<itemListeners.length; i++){
                combo.removeItemListener(itemListeners[i]);
            }
        }
        
        if(combo.getItemCount() > 0){
            combo.removeAllItems();
        }        
        
        for(int i=0;i<array.length;i++){
            combo.addItem(array[i]);
        }
        
        //add the listeners back
        if(actionListeners != null){
            for(int i=0; i<actionListeners.length; i++){
                combo.addActionListener(actionListeners[i]);
            }
        }
        
        if(itemListeners != null){
            for(int i=0; i<itemListeners.length; i++){
                combo.addItemListener(itemListeners[i]);
            }
        }
        
    }
    
    
    public static void fillComboWithNewArray(JComboBox combo, Collection<?> list){
        if (combo == null || list == null) {
        	log.warn("JComboUtils.fillComboWithNewArray: got null object. Returning....");
            return ;
        }
        fillComboWithNewArray(combo, list.toArray());
    }
    
}