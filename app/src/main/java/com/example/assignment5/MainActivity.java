package com.example.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    TextInputEditText UserIP, PassIP;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UserIP = findViewById(R.id.UserIP);
        PassIP = findViewById(R.id.PassIP);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.onUpgrade(myDatabaseHelper.getWritableDatabase(),2,2); // ตรงนี้ถ้าตัวแปร Database_Version เป็นตัวไหนก็ให้ใส่เลขนั้นทั้ง 2 parameters
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLogin){
            String userStr = UserIP.getText().toString().trim();
            String passStr = PassIP.getText().toString().trim();
            if(checkUserName(userStr) && checkPassWord(passStr)){
                if(userStr.equals("admin") && passStr.equals("123456")){
                    Intent launchAdd = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(launchAdd);
                }
            }
        }
    }

    private boolean checkPassWord(String passStr) {
        if(TextUtils.isEmpty(passStr)){
            PassIP.setError("Please enter a password");
            return false;
        }
        return true;
    }

    private boolean checkUserName(String userStr) {
        if(TextUtils.isEmpty(userStr)){
            UserIP.setError("Please enter a username");
            return false;
        }
        return true;
    }
}