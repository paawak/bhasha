package com.swayam.bhasha.utils.page;



/**
 *A PageEvent occurs when one of the following conditions are satisfied:
 *1.> User has reached end of the current page.
 *2.> User is at the beginning of the current page and is trying to go further back
 *3.> User is inserting text in a pre-existing one containing text, which causes the excess
 *      content  to go to the next page.
 *4.> A new page has been added.
 *5.> A page has been deleted.
 */
public class PageEvent {
    public static final int PAGE_EVENT_END_OF_PAGE = 1;
    public static final int PAGE_EVENT_BEGIN_OF_PAGE = 2;
    public static final int PAGE_EVENT_PAGE_OVERFLOW = 3;
    public static final int PAGE_EVENT_PAGE_ADDED = 4;
    public static final int PAGE_EVENT_PAGE_DELETED = 5;
    
    public final int pageEventType;
    public final Page source;
    public final int pageIndex;        
    
    public PageEvent(int pageEventType, Page source, int pageIndex){
        this.pageEventType = pageEventType;
        this.source = source;
        this.pageIndex = pageIndex;
    }
}