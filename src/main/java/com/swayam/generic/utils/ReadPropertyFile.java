package com.swayam.generic.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author  paawak 
 */
public class ReadPropertyFile {
    
    private String fileName=null;
    
    private Locale locale=null;
    
    private ResourceBundle propertyFile;
    
    /** Creates a new instance of ReadPropertyFile */
    public ReadPropertyFile(String fileName) {
        
        propertyFile = ResourceBundle.getBundle(fileName);  
        this.fileName=fileName;
    }
    
    public ReadPropertyFile(String fileName,Locale locale) {
        
        propertyFile = ResourceBundle.getBundle(fileName,locale);    
        this.fileName=fileName;
        this.locale=locale;
        
    }    
        
    public String getVal(String key,Locale locale){
        
        ResourceBundle propertyFile = ResourceBundle.getBundle(fileName,locale);    
        
        return propertyFile.getString(key);
    }
    
    public String getVal(String key){
        if(locale==null)
            return propertyFile.getString(key);
        else
            return getVal(key,locale);
        
    }        
    
}
