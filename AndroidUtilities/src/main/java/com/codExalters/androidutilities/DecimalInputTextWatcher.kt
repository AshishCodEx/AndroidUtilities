package com.codExalters.androidutilities

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Pattern

/**
 * Created by codexalters on 13/3/18.
 */
class DecimalInputTextWatcher(editText: EditText, digitsAfterZero: Int) : TextWatcher {


    private var mPreviousValue: String? = null
    private var mCursorPosition: Int = 0
    private var mRestoringPreviousValueFlag: Boolean = false
    private var mDigitsAfterZero: Int = 0
    private var mEditText: EditText

    init {
        mDigitsAfterZero = digitsAfterZero
        mEditText = editText
        mPreviousValue = ""
        mRestoringPreviousValueFlag = false
    }

    override fun afterTextChanged(s: Editable?) {
        if (!mRestoringPreviousValueFlag) {

            if (!isValid(s.toString())) {
                mRestoringPreviousValueFlag = true
                restorePreviousValue()
            }

        } else {
            mRestoringPreviousValueFlag = false
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (!mRestoringPreviousValueFlag) {
            mPreviousValue = s.toString()
            mCursorPosition = mEditText.selectionStart
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun restorePreviousValue() {
        mEditText.setText(mPreviousValue)
        mEditText.setSelection(mCursorPosition)
    }

    private fun isValid(s: String): Boolean {
        val patternWithDot = Pattern.compile("[0-9]*((\\.[0-9]{0,$mDigitsAfterZero})?)||(\\.)?")
        val patternWithComma = Pattern.compile("[0-9]*((,[0-9]{0,$mDigitsAfterZero})?)||(,)?")

        val matcherDot = patternWithDot.matcher(s)
        val matcherComa = patternWithComma.matcher(s)

        return matcherDot.matches() || matcherComa.matches()
    }
}