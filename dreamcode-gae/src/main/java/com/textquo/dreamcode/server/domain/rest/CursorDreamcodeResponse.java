package com.textquo.dreamcode.server.domain.rest;

public class CursorDreamcodeResponse extends DreamcodeResponse {
    public void setPrev(String prevCursor){
        put("prev", prevCursor);
    }
    public void setHasNext(boolean hasNext){
        put("hasNext", hasNext);
    }
    public void setNext(String nextCursor){
        put("prev", nextCursor);
    }
    public void setHasPrev(boolean hasPrev){
        put("hasNext", hasPrev);
    }
    public void setTotal(Long total){
        put("total", total);
    }
}
