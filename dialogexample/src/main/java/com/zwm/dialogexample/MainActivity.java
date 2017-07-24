package com.zwm.dialogexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    VerifyDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tx = (TextView) findViewById(R.id.test);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new VerifyDialog(MainActivity.this, new VerifyDialog.VerifyCallBack() {
                    @Override
                    public void getInput(String[] inputNum) {
                        Toast.makeText(MainActivity.this, inputNum.toString(), Toast.LENGTH_LONG).show();
                        if (inputNum[0].equals("1")) {
                            dialog.clear();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }

        });
//        EditText edit = (EditText) findViewById(R.id.edit);
//        edit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(MainActivity.this, "get", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }
}
