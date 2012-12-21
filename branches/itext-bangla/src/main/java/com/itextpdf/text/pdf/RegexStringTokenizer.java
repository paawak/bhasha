package com.itextpdf.text.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes the given <i>text</i> into an array of <i>token</i> based on a <i>regex</i>. On assembling the array, you should be 
 * able to get back the original text.
 * 
 * @author paawak
 */
public class RegexStringTokenizer {
    
    private final Pattern regex;
    
    public RegexStringTokenizer(String regexPattern) {
        this.regex = Pattern.compile(regexPattern);
    }
    
    public String[] tokenize(String text) {
        
        List<String> tokens = new ArrayList<String>();
        
        Matcher matcher = regex.matcher(text);
        
        int endIndexOfpreviousMatch = 0;
        
        while (matcher.find()) {
            
            int startIndexOfMatch = matcher.start();
            
            String previousToken = text.substring(endIndexOfpreviousMatch, startIndexOfMatch);
            
            if (previousToken.length() > 0) {
                tokens.add(previousToken);
            }
            
            String currentMatch = matcher.group();
            
            System.out.println("currentMatch=" + currentMatch); 
            
            tokens.add(currentMatch);
            
            endIndexOfpreviousMatch = matcher.end();
            
        }
        
        String tail = text.substring(endIndexOfpreviousMatch, text.length());
        
        if (tail.length() > 0) {
            tokens.add(tail);
        }
        
        return tokens.toArray(new String[0]);
        
    }

}
