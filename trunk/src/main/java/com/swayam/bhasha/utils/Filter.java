package com.swayam.bhasha.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class Filter extends FileFilter {
    
    private String validExtension = null;
    private String extensionDesc = null;
    
/**
 *Creates a filter and allows only files having extension <i>validExtension</i>
 *@param String validExtension: cant be null, throws NullPointerException
 *@param String extensionDesc: can be null
 */
    public Filter(String validExtension, String extensionDesc){
        if(validExtension == null)
            throw new NullPointerException("utils.File: validExtension can't be null in constructor");
        this.validExtension = validExtension;
        this.extensionDesc = extensionDesc;
    }
    
    /** Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @see FileFilter#accepts
     *
     *
     */
    public boolean accept(File f) {
        if(f != null) {
            if(f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            if(extension != null 
                    && extension.equalsIgnoreCase(validExtension)) {
                return true;
            };
        }
        return false;
    }
    
    /** Returns the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see FileFilter#getDescription
     *
     *
     */
    public String getDescription() {        
        return extensionDesc;
    }
    
    /** Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     *
     *
     *
     */
    public static String getExtension(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase();
            };
        }
        return null;
    }
    
}

