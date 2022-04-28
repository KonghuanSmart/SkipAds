package com.konghuan.skipads.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.konghuan.skipads.R;
import com.konghuan.skipads.entity.Account;
import com.konghuan.skipads.exception.LoginException;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etAccount,etPassword;
    private CheckBox cbRember,cbRember2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("登陆");

        initView();
        initData();

        cbRember2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                cbRember.setChecked(true);
            }
        });

        cbRember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked){
                cbRember2.setChecked(false);
            }
        });
    }

    public void login(View view) {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();

        Account loginAccount = Account.createAccount(account, password);
        try {
            loginAccount.login(this);
            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
            //记住密码
            if (cbRember.isChecked()){
                SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putString("account",account);
                edit.putString("password",password);
                edit.putBoolean("isRemember",true);
                if (cbRember2.isChecked()){
                    edit.putBoolean("isLogin",true);
                }else{
                    edit.putBoolean("isLogin",false);
                }
                edit.apply();

            }else{
                SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putBoolean("isRemember",false);
                edit.apply();
            }
            //登录成功 跳转到主页
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        } catch (LoginException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRember = findViewById(R.id.et_remember);
        cbRember2 = findViewById(R.id.et_auto_login);
    }

    //判断是否记住密码
    private void initData() {
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        boolean isRemember = spf.getBoolean("isRemember",false);
        boolean isLogin = spf.getBoolean("isLogin",false);
        String account = spf.getString("account","");
        String password = spf.getString("password","");

        //模拟
        if (isLogin){
            Account loginAccount = Account.createAccount(account, password);
            try {
                loginAccount.login(this);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            } catch (LoginException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            }
        }

        if (isRemember){
            etAccount.setText(account);
            etPassword.setText(password);
            cbRember.setChecked(true);
        }
    }

    public void toRegister(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        //自动填充
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0 && data != null){
            //打包的方式
            Bundle extas = data.getExtras();

            String account = extas.getString("account","");
            String password = extas.getString("password","");

            etAccount.setText(account);
            etPassword.setText(password);

            //假定用户
        }
    }
}