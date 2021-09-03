package com.nyw.pets.pay;

import android.text.Editable;
import android.text.TextWatcher;

import com.nyw.pets.view.ClearEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayAmountTextChange implements TextWatcher {
    private ClearEditText ct_price;
   public PayAmountTextChange( ClearEditText ct_price){
        this.ct_price=ct_price;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int selectionStart = ct_price.getSelectionStart();
        int selectionEnd = ct_price.getSelectionEnd();
        if (!isOnlyPointNumber(s.toString()) && s.length() > 0) {
            //删除多余输入的字（不会显示出来）
            s.delete(selectionStart - 1, selectionEnd);
            ct_price.setText(s);
            ct_price.setSelection(s.length());
        }
    }

    /**
     * 通过正则表达式判断,保留一位小数  ，保留两位的话把1变成2；
     * @param number
     * @return
     */
    public  boolean isOnlyPointNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
