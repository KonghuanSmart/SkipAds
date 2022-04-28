package com.konghuan.skipads.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.konghuan.skipads.R;
import com.konghuan.skipads.exception.LoginException;
import com.konghuan.skipads.service.AccountService;
import com.konghuan.skipads.service.Impl.AccountDaoImpl;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etAccount,etPassword,etPassconfirm;
    private CheckBox cBtn;

    private static AccountService service = new AccountService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("注册");

        btnRegister = findViewById(R.id.btn_register);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        etPassconfirm = findViewById(R.id.et_password_confirm);
        cBtn = findViewById(R.id.cbtn);


    }

    public void register(View view) {
        String name = etAccount.getText().toString();
        String pass = etPassword.getText().toString();
        String passConfirm = etPassconfirm.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pass)){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        if (!TextUtils.equals(pass,passConfirm)){
            Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
            return;
        }

        if (!cBtn.isChecked()){
            Toast.makeText(RegisterActivity.this,"请同意用户协议",Toast.LENGTH_LONG).show();
            return;
        }

        //向数据库中保存
        try {
            service.register(this, name, pass);
        } catch (LoginException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //存储注册的用户名 密码
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putString("account",name);
        edit.putString("password", pass);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("account", name);
        bundle.putString("password",pass);
        intent.putExtras(bundle);
        setResult(0,intent);
        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();

        this.finish();
    }
}