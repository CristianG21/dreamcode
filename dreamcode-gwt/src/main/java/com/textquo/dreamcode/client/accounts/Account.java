package com.textquo.dreamcode.client.accounts;

import com.textquo.dreamcode.client.DreamcodeCallback;

import java.util.Map;

public class Account {
    /**
     * Sign up
     * @param email
     * @param password
     * @param callback
     */
    public void signUp(String email, String password, DreamcodeCallback callback){

    }

    /**
     * Sign in
     * @param email
     * @param password
     * @param callback
     */
    public void signIn(String email, String password, DreamcodeCallback callback){

    }

    /**
     * Sign in via oAuth, e.g. 'twitter' or 'persona'
     * @param provider
     * @param callback
     */
    public void signInWith(String provider, DreamcodeCallback callback){

    }

    /**
     * Change password
     * @param currentPassword
     * @param newPassword
     * @param callback
     */
    public void changePassword(String currentPassword, String newPassword, DreamcodeCallback callback){

    }

    /**
     * Change username
     * @param currentPassword
     * @param newUsername
     * @param callback
     */
    public void changeUsername(String currentPassword, String newUsername, DreamcodeCallback callback){

    }

    /**
     * Reset password
     * @param email
     * @param callback
     */
    public void resetPassword(String email, DreamcodeCallback callback){

    }

    /**
     * Destroy accoutn and all its data
     * @param currentPassword
     * @param callback
     */
    public void destroy(String currentPassword, DreamcodeCallback callback){

    }

    /**
     * Sign up, that accepts a parameter hash
     * that would also allow for additional user info
     * @param email
     * @param password
     * @param parameterHash
     * @param callback
     */
    public void signUp(String email, String password, Map<String, String> parameterHash, DreamcodeCallback callback){

    }

    /**
     * User is signed in
     * @param callback
     */
    public void isSignedIn(DreamcodeCallback callback){

    }

    /**
     * User is signed in as 'email'
     * @param email
     * @param callback
     */
    public void isSignedIn(String email, DreamcodeCallback callback){

    }

    /**
     * Change a parameter value for this account
     * @param parameterHash
     * @param callback
     */
    public void change(Map<String, String> parameterHash, DreamcodeCallback callback){

    }
}
