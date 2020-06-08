package com.example.exampleapplication.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.exampleapplication.R;
import com.example.exampleapplication.extraclasses.CreditCardFormatTextWatcher;
import com.example.exampleapplication.interfaces.CreditCardListener;
import com.example.exampleapplication.pojo.CardInfo;
import com.example.exampleapplication.pojo.CardInfoEnum;
import com.example.exampleapplication.pojo.CardText;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.exampleapplication.constants.Statics.AMEX;
import static com.example.exampleapplication.constants.Statics.DINER;
import static com.example.exampleapplication.constants.Statics.MASTER;
import static com.example.exampleapplication.constants.Statics.UNKNOWN;
import static com.example.exampleapplication.extraclasses.DataSource.getCardInfoList;
import static com.example.exampleapplication.pojo.CardInfo.defaultCard;

/**
 * Created by Ayush on 06/06/20.
 */
public class CreditCardView extends RelativeLayout {
    private static final String TAG = "CreditCardView";
    private Context context;
    private AttributeSet attrs;

    private View rootView;
    private AppCompatEditText editText;
    private AppCompatImageView ivCardType;

    private CardInfo cardType;
    private List<CardInfo> cardInfoList;
    private boolean isDeleting;
    private CreditCardListener impl;

    public CreditCardView(Context context) {
        super(context);
    }

    public CreditCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init(context, attrs);
    }


    public CreditCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init(context, attrs);
    }

    public CreditCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _init(context, attrs);
    }

    private void _init(Context context, AttributeSet attrs) {
        this.context = context;
        this.attrs = attrs;
        cardInfoList = getCardInfoList();
        cardType = defaultCard();

        rootView = LayoutInflater.from(context)
                .inflate(R.layout.view_credit_card, this, true);

        ivCardType = rootView.findViewById(R.id.iv_card_type);
        editText = rootView.findViewById(R.id.et_credit_card);

        editText.post(() -> {
            editText.setShowSoftInputOnFocus(true);
            editText.requestFocus();
            editText.setPressed(true);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
        });


        editText.addTextChangedListener(new CreditCardFormatTextWatcher(editText));

    }

    public void setCardListener(CreditCardListener listener) {
        this.impl = listener;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int lengthBefore, int lenghtAfter) {
            int lenOfString = start - lengthBefore + lenghtAfter;
            Log.d(TAG, "onTextChanged: " + editText.getSelectionStart());
            if (start > lenOfString) isDeleting = true;
            else isDeleting = false;

            if (isDeleting) {
                resetMaxLen();
                return;
            }
            if (lenOfString >= 2) {
                String formattedString = s.toString();
                String unformattedString = removeSeparatorAndPlaceHolders(formattedString);

                //detect card type
                for (CardInfo info : cardInfoList) {
                    Matcher matcher = info.getPatternS().matcher(unformattedString);
                    if (matcher.find()) {
                        cardType = info;
                        impl.getCardType(cardType);
                        break;
                    } else cardType = defaultCard();

                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String unformattedString = removeSeparatorAndPlaceHolders(s.toString());
            impl.getCardNumber(unformattedString);
            if (isDeleting) return;
            CardText cardText = CardText.getFormattedCardTet(cardType, unformattedString);
            StringBuilder formattedString = new StringBuilder(cardText.getFormattedString());

            if (formattedString.length() > 0) {
                editText.removeTextChangedListener(this);
                editText.setText(formattedString.toString());
                editText.setSelection(cardText.getLastDigitPointer());
                editText.addTextChangedListener(this);
            }

            if (cardText.getNumberString().length() == cardType.getMaxLength()) {
                impl.isValid(isCardValid(unformattedString));
                setupInputFilterByCardType();
            }
        }
    };

    private boolean isCardValid(String unformattedString) {
        int lastDigit = Character.digit(unformattedString.charAt(unformattedString.length() - 1), 10);
        String withoutLastDigit = unformattedString
                .substring(0, unformattedString.length() - 1);
        StringBuilder reversed = new StringBuilder(withoutLastDigit)
                .reverse();
        String reversedString = reversed.toString();
        boolean isSecond = true;
        int sum = 0;
        for (int i = 0; i < reversedString.length(); i++) {
            int intAt = reversedString.charAt(i) - '0';
            if (isSecond) {
                intAt = (intAt * 2);
                if (intAt > 9) intAt = intAt - 9;
            }
            sum = sum + intAt;
            isSecond = !isSecond;
        }

        return (sum + lastDigit) % 10 == 0;
    }

    private String removeSeparatorAndPlaceHolders(String formattedString) {
        return formattedString
                .replace(" ", "")
                .replace("X", "");
    }

    private void setupInputFilterByCardType() {
        int maxLen = 19;
        if (cardType != null)
            maxLen = cardType.getMaxLength();

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxLen);
        editText.setFilters(filterArray);

    }

    private void resetMaxLen() {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(100);
        editText.setFilters(filterArray);
    }

    public static void formatCardNumber(@NonNull Editable ccNumber, int paddingPx, int maxLength) {
        int textLength = ccNumber.length();
        // first remove any previous span
        CreditCardFormatTextWatcher.PaddingRightSpan[] spans = ccNumber.getSpans(0, ccNumber.length(), CreditCardFormatTextWatcher.PaddingRightSpan.class);
        for (int i = 0; i < spans.length; i++) {
            ccNumber.removeSpan(spans[i]);
        }
        // then truncate to max length
        if (maxLength > 0 && textLength > maxLength - 1) {
            ccNumber.replace(maxLength, textLength, "");
        }
        // finally add margin spans
        for (int i = 1; i <= ((textLength - 1) / 4); i++) {
            int end = i * 4;
            int start = end - 1;
            CreditCardFormatTextWatcher.PaddingRightSpan marginSPan = new CreditCardFormatTextWatcher.PaddingRightSpan(paddingPx);
            ccNumber.setSpan(marginSPan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public static class PaddingRightSpan extends ReplacementSpan {

        private int mPadding;

        public PaddingRightSpan(int padding) {
            mPadding = padding;
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            float[] widths = new float[end - start];
            paint.getTextWidths(text, start, end, widths);
            int sum = mPadding;
            for (int i = 0; i < widths.length; i++) {
                sum += widths[i];
            }
            return sum;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
            canvas.drawText(text, start, end, x, y, paint);
        }

    }
}
