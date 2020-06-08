package com.example.exampleapplication.custom_views;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.exampleapplication.R;
import com.example.exampleapplication.interfaces.CreditCardListener;
import com.example.exampleapplication.pojo.CardInfo;
import com.example.exampleapplication.pojo.CardInfoEnum;

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


        editText.addTextChangedListener(textWatcher);

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
            int length = start - lengthBefore + lenghtAfter;
            Log.d(TAG, "onTextChanged: " + editText.getSelectionStart());
            if (start > length) isDeleting = true;
            else isDeleting = false;

            if (isDeleting) return;
            if (length >= 2) {
                String formattedString = s.toString();
                String unformattedString = removeSeparator(formattedString);

                for (CardInfo info : cardInfoList) {
                    Matcher matcher = info.getPatternS().matcher(unformattedString);
                    if (matcher.find()) {
                        cardType = info;
                        impl.getCardType(cardType);
                        setupInputFilterByCardType();
                        break;
                    } else {
                        cardType = defaultCard();
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String unformattedString = s.toString().replace(" ", "");
            impl.getCardNumber(unformattedString);
            if (isDeleting) return;
            StringBuilder formattedString = new StringBuilder();
            switch (cardType.getCardType()) {
                case AMEX:
                case DINER:
                    if (unformattedString.length() == 4) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                    } else if (unformattedString.length() > 4 && unformattedString.length() <= 10) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(4, unformattedString.length()));
                        formattedString.append(" ");
                    } else if (unformattedString.length() > 10) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(4, 10));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(10, unformattedString.length()));
                    }
                    break;

                default:
                case MASTER:
                case UNKNOWN:
                    if (unformattedString.length() == 4) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                    } else if (unformattedString.length() > 4 && unformattedString.length() <= 8) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(4, unformattedString.length()));
                        formattedString.append(" ");
                    } else if (unformattedString.length() > 8 && unformattedString.length() <= 12) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(4, 8));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(8, unformattedString.length()));
                        formattedString.append(" ");
                    } else if (unformattedString.length() > 12) {
                        formattedString.append(unformattedString.substring(0, 4));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(4, 8));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(8, 12));
                        formattedString.append(" ");
                        formattedString.append(unformattedString.substring(12, unformattedString.length()));
                    }
                    break;
            }

            if (formattedString.length() > 0) {
                editText.removeTextChangedListener(this);
                editText.setText(formattedString.toString());
                editText.setSelection(formattedString.toString().length());
                editText.addTextChangedListener(this);
            }

            if (formattedString.length() == cardType.getMaxLength())
                impl.isValid(isCardValid(unformattedString));
        }
    };

    private String getDefaultStringByCardType() {
        switch (cardType.getCardType()) {
            case AMEX:
                return "XXXXXXXXXXXXXXX";

            case DINER:
                return "XXXXXXXXXXXXXX";

            default:
            case MASTER:
            case UNKNOWN:
                return "XXXXXXXXXXXXXXXX";
        }
    }

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

    private String removeSeparator(String formattedString) {
        return formattedString.replace(" ", "");
    }

    private void setupInputFilterByCardType() {
        int maxLen = 19;
        if (cardType != null)
            maxLen = cardType.getMaxLength();

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxLen);
        editText.setFilters(filterArray);

    }
}
