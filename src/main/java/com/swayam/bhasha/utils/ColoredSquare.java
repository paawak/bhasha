package com.swayam.bhasha.utils;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class ColoredSquare implements Icon,java.io.Serializable {
    
    private static final long serialVersionUID = -1752041688024278020L;
	private Color color;
    private int width = 12;
    private int height = 12;
    
    public ColoredSquare(Color c) {
        this.color = c;
    }
    
    public ColoredSquare(Color c, int width, int height) {
        this.color = c;
        this.width = width; 
        this.height = height; 
    }    
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fill3DRect(x,y,getIconWidth(), getIconHeight(), true);
        g.setColor(oldColor);
    }
    
    public ImageIcon getColoredSquare(){
        BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        return new ImageIcon(im);
    }
    
    public int getIconWidth() { 
        return width; 
    }
    
    public int getIconHeight() { 
        return height; 
    }
    
    public void setIconWidth(int width) { 
        this.width = width; 
    }
    
    public void setIconHeight(int height) { 
        this.height = height; 
    }    
    
}

