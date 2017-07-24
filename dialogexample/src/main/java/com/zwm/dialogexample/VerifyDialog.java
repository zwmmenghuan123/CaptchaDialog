package com.zwm.dialogexample;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwm on 2017/7/21.
 */

public class VerifyDialog extends Dialog implements TextWatcher,View.OnKeyListener{

    private Context context;
    private VerifyCallBack callBack;

    private EditText captcha1;
    private EditText captcha2;
    private EditText captcha3;
    private EditText captcha4;
    private int current = 0;
    private List<EditText> editTexts;

    ImageView iv_close;


    protected VerifyDialog(Context context, VerifyCallBack callBack) {
        super(context);
        this.context = context;
        this.callBack = callBack;
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView();

    }

    protected VerifyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private void initView() {
        setContentView(R.layout.layout_captchadialog);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        captcha1 = (EditText) findViewById(R.id.captcha1);
        captcha2 = (EditText) findViewById(R.id.captcha2);
        captcha3 = (EditText) findViewById(R.id.captcha3);
        captcha4 = (EditText) findViewById(R.id.captcha4);
        editTexts = new ArrayList<EditText>();

        editTexts.add(captcha1);
        editTexts.add(captcha2);
        editTexts.add(captcha3);
        editTexts.add(captcha4);

        captcha1.addTextChangedListener(this);
        captcha2.addTextChangedListener(this);
        captcha3.addTextChangedListener(this);
        captcha4.addTextChangedListener(this);

        captcha1.setOnKeyListener(this);
        captcha2.setOnKeyListener(this);
        captcha3.setOnKeyListener(this);
        captcha4.setOnKeyListener(this);

        focus(captcha1);


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if ("".equals(s.toString())) {
            return;
        }
        if (current > 2) {
            //回调Activity传来的接口进行校验
            callBack.getInput(
                    new String[] {
                            captcha1.getText().toString(),
                            captcha2.getText().toString(),
                            captcha3.getText().toString(),
                            captcha4.getText().toString(),
                    }
            );
            return;
        }
        focus(editTexts.get(++current));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
            editTexts.get(--current).setText("");
            focus(editTexts.get(current));
            return true;
        }else{

        }
        return false;
    }


    public interface VerifyCallBack {
        public void getInput(String[] inputNum);
    }

    private void focus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    //校验失败清空数据
    public void clear() {
        captcha1.setText("");
        captcha2.setText("");
        captcha3.setText("");
        captcha4.setText("");
        current = 0;
        focus(captcha1);
    }


}
