package com.hbbsolution.owner.utils.messagedialog;

/**
 * Created by Kanet Tran on 4/25/2017.
 */

public enum TypeMessageDialog {
    //CODE return about button pressed
    /**
     * region MESSAGEDIALOG_TYPE_YESNO
     * require button: MESSAGEDIALOG_BUTTON_YES, MESSAGEDIALOG_BUTTON_NO
     */
    MESSAGEDIALOG_TYPE_YESNO,

    /**
     * region MESSAGEDIALOG_TYPE_WARNING
     * require button: MESSAGEDIALOG_BUTTON_OK, MESSAGEDIALOG_BUTTON_BACKGROUND
     */
    MESSAGEDIALOG_TYPE_WARNING,

    /**
     * region MESSAGEDIALOG_TYPE_ERROR
     * require button: MESSAGEDIALOG_BUTTON_OK, MESSAGEDIALOG_BUTTON_BACKGROUND
     */
    MESSAGEDIALOG_TYPE_ERROR,

    /**
     * region MESSAGEDIALOG_TYPE_TOAST
     * not require
     */
    MESSAGEDIALOG_TYPE_TOAST,

    /**
     * region MESSAGEDIALOG_TYPE_SUCCESS
     * require button: MESSAGEDIALOG_BUTTON_OK, MESSAGEDIALOG_BUTTON_BACKGROUND
     */
    MESSAGEDIALOG_TYPE_SUCCESS,

    /**
     * region MESSAGEDIALOG_TYPE_TRYAGAIN
     * require button: MESSAGEDIALOG_BUTTON_OK, MESSAGEDIALOG_BUTTON_BACKGROUND
     */
    MESSAGEDIALOG_TYPE_TRYAGAIN,

    /**
     * region MESSAGEDIALOG_TYPE_LOADING
     * not require
     */
    MESSAGEDIALOG_TYPE_LOADING,

    /*

     */
    MESSAGE_DIALOG_TYPE_CHOOSE_ITEM

}
