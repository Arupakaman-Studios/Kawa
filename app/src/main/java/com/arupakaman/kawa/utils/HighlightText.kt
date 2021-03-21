package com.arupakaman.kawa.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.widget.TextView

    fun highlightString(input: String, mTextView: TextView) {
        //Get the text from text view and create a spannable string
        val spannableString = SpannableString(mTextView.text)

        //Get the previous spans and remove them
        val backgroundSpans =
            spannableString.getSpans(0, spannableString.length, BackgroundColorSpan::class.java)
        for (span in backgroundSpans) {
            spannableString.removeSpan(span)
        }

        //Search for all occurrences of the keyword in the string
        var indexOfKeyword = spannableString.toString().indexOf(input)
        while (indexOfKeyword > 0) {
            //Create a background color span on the keyword
            spannableString.setSpan(
                BackgroundColorSpan(Color.YELLOW),
                indexOfKeyword,
                indexOfKeyword + input.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            //Get the next index of the keyword
            indexOfKeyword =
                spannableString.toString().indexOf(input, indexOfKeyword + input.length)
        }
        mTextView.text = spannableString
    }

    fun String.toHighlightedText(textToHighlight: String): String {
        //String oldString = mTextView.getText().toString();
        return replace(textToHighlight.toRegex(), "<font color='black'>$textToHighlight</font>")
        //mTextView.setText(Html.fromHtml(newString));
    }
