package com.swayam.generic.utils.writer;


/**
 * This class accepts the start tag and end tag of a rtf/html element.
 * @author paawak
 */
public class TagElement {
    public final String startTag;
    public final String endTag;
    
    public TagElement(String startTag, String endTag){
        this.startTag = startTag;
        this.endTag = endTag;
    }
    
    public String toString(){
        return "[startTag = " + startTag +", endTag = " + endTag + "]";
    }
}