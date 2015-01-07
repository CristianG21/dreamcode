package com.textquo.dreamcode.client.payments;

import com.textquo.dreamcode.client.DreamcodeCallback;

/**
 * Created by kmartino on 1/7/15.
 */
public class Payments {
    /**
     * Purchase a product with a credit card
     * @param cc
     * @param valid
     * @param csc
     * @param callback
     */
    public void purchaseUsingCreditCard(String cc, String valid, String csc, DreamcodeCallback callback){

    }

    /**
     * Purchase a product with Paypal
     * @param productId
     * @param callback
     */
    public void purchaseUsingPaypal(String productId, DreamcodeCallback callback){

    }

    /**
     * Purchase a product with Google Checkout
     * @param productId
     * @param callback
     */
    public void purchaseUsingGoogleCheckout(String productId, DreamcodeCallback callback){

    }


}
