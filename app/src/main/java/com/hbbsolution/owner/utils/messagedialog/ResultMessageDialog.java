package com.hbbsolution.owner.utils.messagedialog;

/**
 * Created by Kanet Tran on 4/25/2017.
 */

public enum ResultMessageDialog {
    //CODE return about button pressed

    /**
     * region MESSAGEDIALOG_BUTTON_OK
     * Suport for TYPEMESSAGE: MESSAGEDIALOG_TYPE_OK,MESSAGEDIALOG_TYPE_WARNING,MESSAGEDIALOG_TYPE_ERROR,
     * MESSAGEDIALOG_TYPE_SUCCESS,MESSAGEDIALOG_TYPE_TRYAGAIN
     */
    MESSAGEDIALOG_BUTTON_OK,

    /**
     * region MESSAGEDIALOG_BUTTON_YES
     * Suport for TYPEMESSAGE: Suport for TYPEMESSAGE = MESSAGEDIALOG_TYPE_YESNO
     */
    MESSAGEDIALOG_BUTTON_YES,

    /**
     * region MESSAGEDIALOG_BUTTON_NO
     * Suport for TYPEMESSAGE: Suport for TYPEMESSAGE = MESSAGEDIALOG_TYPE_YESNO
     */
    MESSAGEDIALOG_BUTTON_NO,

    /**
     * region MESSAGEDIALOG_BUTTON_BACKGROUND
     * Suport for All of TYPEMESSAGE but they have background.
     */
    MESSAGEDIALOG_BUTTON_BACKGROUND,

    /**
     * region MESSAGEDIALOG_BUTTON_TRY
     * Suport for All of TYPEMESSAGE: MESSAGEDIALOG_TYPE_TRYAGAIN.
     */
    MESSAGEDIALOG_BUTTON_TRY,

    /**
     * region MESSAGEDIALOG_BUTTON_CLOSE
     * Suport for All of TYPEMESSAGE but they have button close.
     */
    MESSAGEDIALOG_BUTTON_CLOSE,
    /**
     * region MESSAGEDIALOG_ITEM
     * Support some message that click item.
     */
    MESSAGEDIALOG_ITEM
}
