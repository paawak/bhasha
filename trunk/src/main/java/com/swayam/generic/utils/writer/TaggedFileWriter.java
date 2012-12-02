package com.swayam.generic.utils.writer;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This accepts a DefaultMutableTreeNode having TagElement as nodes
 * and writes them to the specified file-name.
 * @author paawak
 */
public class TaggedFileWriter implements Runnable {
    
	private final String fileToWrite;
	private final DefaultMutableTreeNode mainTreeNode;
	
    public TaggedFileWriter(final String fileToWrite, final DefaultMutableTreeNode mainTreeNode) {
    	this.fileToWrite = fileToWrite;
    	this.mainTreeNode = mainTreeNode;
    }
    
    public void run() {
    	String contents = extractContents(mainTreeNode);
        writeToFile(fileToWrite, contents);
    }
    
    protected String extractContents(DefaultMutableTreeNode tagTreeNode){        
        TagElement tag = (TagElement)tagTreeNode.getUserObject();
        String contents = tag.startTag;
        int childCount = tagTreeNode.getChildCount();
        for(int i=0; i<childCount; i++){
            contents += extractContents((DefaultMutableTreeNode) tagTreeNode.getChildAt(i));
        }
        contents += tag.endTag;
        return contents;
    }
    
    private void writeToFile(String fileName, String contents){
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            fos.write(contents.getBytes());
            System.out.println("written to file");
            fos.close();
            fos = null;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
