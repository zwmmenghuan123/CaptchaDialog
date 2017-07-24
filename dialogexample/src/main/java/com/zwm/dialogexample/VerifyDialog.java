package com.zwm.dialogexample;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zwm on 2017/7/21.
 */

public class VerifyDialog extends Dialog {

    private Context context;
    private VerifyCallBack callBack;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;


    protected VerifyDialog(Context context, VerifyCallBack callBack) {
        super(context);
        this.context = context;
        this.callBack = callBack;
        setCancelable(false);
        initView();

    }

    protected VerifyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected VerifyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void initView() {
        setContentView(R.layout.verifydialog);
        editText1 = (EditText) findViewById(R.id.confirmation1);
        editText2 = (EditText) findViewById(R.id.confirmation2);
        editText3 = (EditText) findViewById(R.id.confirmation3);
        editText4 = (EditText) findViewById(R.id.confirmation4);
        focus(editText1);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                focus(editText2);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                focus(editText3);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                focus(editText4);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String[] inputNum = {
                        editText1.getText().toString(),
                        editText2.getText().toString(),
                        editText3.getText().toString(),
                        editText4.getText().toString(),
                };
                if (!"".equals(s.toString())) {
                    callBack.getInput(inputNum);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
        focus(editText1);
    }


}
