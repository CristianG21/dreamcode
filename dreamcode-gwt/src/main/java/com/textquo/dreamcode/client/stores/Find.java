package com.textquo.dreamcode.client.stores;

import com.textquo.dreamcode.client.DreamcodeCallback;

/**
 * Created by kmartino on 1/7/15.
 */
public class Find {

    private final Store store;
    private final String type;

    public Find(Store store, String type){
        this.type = type;
        this.store = store;
    }

    /**
     * Add a new share for type
     * @param callback
     */
    public void share(DreamcodeCallback callback){

    }

    /**
     * Share all objects of type to the share
     * @param shareId
     * @param callback
     */
    public void shareAt(String shareId, DreamcodeCallback callback){

    }

    public void shareAt(String shareId){
    }

    public void unshare(){}
    public void unshareAt(String shareId){}
    public void unshareAt(String shareId, DreamcodeCallback callback){}
}
