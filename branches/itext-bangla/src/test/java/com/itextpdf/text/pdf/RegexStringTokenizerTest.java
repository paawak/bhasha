package com.itextpdf.text.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 *  
 * @author paawak
 */
public class RegexStringTokenizerTest {
    
    @Test
    public void testTokenize_happyPath() {
        //given
        RegexStringTokenizer tokenizer = new RegexStringTokenizer("(aZx)|(b2c)|(bc2)");
        String text = "12345aZxxabbccb2cxxxcfb1245678bc2889000";
        
        //when
        String[] tokens = tokenizer.tokenize(text);
        
        //then
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token);
        }
        
        assertEquals(text, sb.toString());
    }
    
    @Test
    public void testTokenize_regexAtStart() {
        //given
        RegexStringTokenizer tokenizer = new RegexStringTokenizer("(aZx)|(b2c)|(bc2)");
        String text = "bc2e12345aZxxabbccb2cxxxcfb1245678bc2889000";
        
        //when
        String[] tokens = tokenizer.tokenize(text);
        
        //then
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token);
        }
        
        assertEquals(text, sb.toString());
        
        List<String> tokenList = Arrays.asList(tokens);
        
        assertTrue(tokenList.contains("b2c")); 
    }
    
    @Test
    public void testTokenize_regexAtEnd() {
        //given
        RegexStringTokenizer tokenizer = new RegexStringTokenizer("(aZx)|(b2c)|(bc2)");
        String text = "bc2e12345aZxxabbccb2cxxxcfb1245678bc2889000aZx";
        
        //when
        String[] tokens = tokenizer.tokenize(text);
        
        //then
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token);
        }
        
        assertEquals(text, sb.toString());
    }
    
    @Test
    public void testTokenize_Bangla() {
        //given
        RegexStringTokenizer tokenizer = new RegexStringTokenizer("(\u0995\u09cd\u09b7)|(পু)");
        String text = "আমি কোন পথে ক্ষীরের ষন্ড পুতুল রুপো গঙ্গা ঋষি";
        
        //when
        String[] tokens = tokenizer.tokenize(text);
        
        //then
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token);
        }
        
        assertEquals(text, sb.toString());
        
        List<String> tokenList = Arrays.asList(tokens);
        
        assertTrue(tokenList.contains("ক্ষ"));
        assertTrue(tokenList.contains("পু")); 
    }

}
