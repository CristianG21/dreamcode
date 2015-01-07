package com.textquo.dreamcode.client.emails;

import com.textquo.dreamcode.client.DreamcodeCallback;

public class Emails {
    /**
     * Send text email
     * @param subject
     * @param text
     * @param to
     * @param callback
     */
    public void sendEmail(String subject, String text, String to, String[] cc, String[] bcc, DreamcodeCallback callback){

    }

    /**
     * Send multipart text / html email
     * @param subject
     * @param text
     * @param html
     * @param to
     * @param callback
     */
    public void sendEmail(String subject, String text, String html, String to, String[] cc, String[] bcc, DreamcodeCallback callback){

    }

    /**
     * Send multipart with attachment
     * @param subject
     * @param text
     * @param html
     * @param to
     * @param attachments
     * @param callback
     */
    public void sendEmail(String subject, String text, String html, String to, String[] cc, String[] bcc, String[] attachments, DreamcodeCallback callback){

    }
}
