package com.swayam.bhasha.utils.page;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class PageableIndicPane extends IndicPane implements Page {
    
    private final Vector<PageListener> pageListenerVec = new Vector<PageListener>();
    
    public PageableIndicPane(Locale locale, Dimension pageDim, Insets insets){
        super(locale, pageDim, insets);
        makePaneFixedSize();
    }        
    
    public void addPageListener(PageListener pageListener) {
        pageListenerVec.addElement(pageListener);
    }
    
    public void removePageListener(PageListener pageListener) {
        pageListenerVec.removeElement(pageListener);
    }
    
    /**
     *adds DocumentFilter to the IndicPane such that text can be added
     *only upto the pageHeight...
     */
    private void makePaneFixedSize(){
        StyledDocument styleDoc = getStyledDocument();
        if (styleDoc instanceof AbstractDocument){
            ((AbstractDocument) styleDoc).setDocumentFilter(new DocumentFilter(){
                public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if(isCursorBeyondPageEnd()){
                        super.insertString(fb, offset, string, attr);
                    }
                }
                
                public void replace(DocumentFilter.FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
                    
                    if(isCursorBeyondPageEnd()){
                        super.replace(fb, offs, length, str, a);
                    }
                    
                }
                    
                }
            );
        }
        
        addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
            }
        });
    }
    
    private boolean isCursorBeyondPageEnd(){
        Caret caret = getCaret();
        Point cursorPos = caret.getMagicCaretPosition();
        System.out.println("************ cursorPos = " + cursorPos);
        int writableHeight = getSize().height
                    - getInsets().bottom - StyleConstants.getFontSize(getCurrentAttributeSet());
        System.out.println("************* writableHeight = " + writableHeight);
        
        if (cursorPos != null && cursorPos.y >= writableHeight){
        	//FIXME:: this dummy
            Toolkit.getDefaultToolkit().beep();
            PageEvent pe = new PageEvent(PageEvent.PAGE_EVENT_END_OF_PAGE,
                        this, 1);
            fireEndOfPageEvent(pe);
            return false;
        }
        
        return true;
    }
    
    private void fireEndOfPageEvent(final PageEvent pe){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for (int i = 0; i<pageListenerVec.size(); i++){
                    pageListenerVec.elementAt(i).endOfPageReached(pe);
                }
            }
            });
    }
    
//    private void firePageEvents(){
//        /*
//        addKeyListener(new KeyAdapter(){
//            public void keyReleased(KeyEvent evt) {
//                Point caretPos = indicPane.getCaret().getMagicCaretPosition();
//                if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//                    caretPos.y += indicPane.getFont().getSize()+pageTopMargin;
//                /*
//         *the cursor must go to a new page. if this is the last page,
//         *add a new page. otherwise go to an existing page
//         */
//        /*
//                    if(!indicPane.contains(caretPos)){
//                        if(currentPageIndex == pageVector.size()-1){
//                            addPage();
//                        } else {
//                            pageVector.elementAt(currentPageIndex + 1).requestFocus();
//                        }
//                    }
//                } else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
//                    caretPos.y -= indicPane.getFont().getSize() + pageBottomMargin;
//                    //go to the previous page
//                    if(!indicPane.contains(caretPos)){
//                        if(currentPageIndex > 0){
//                            pageVector.elementAt(currentPageIndex - 1).requestFocus();
//                        }
//                    }
//                }
//            }
//        });//*/
//        
//    }
    
}