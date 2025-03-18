package com.example.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSave, btnNext;
    TextInputEditText CodeIP, NameIP, ScoreIP;
    TextView countSubject;
    Integer count = 0;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnSave = findViewById(R.id.btnSave);
        btnNext = findViewById(R.id.btnNext);
        countSubject = findViewById(R.id.countSubject);
        CodeIP = findViewById(R.id.CodeIP);
        NameIP = findViewById(R.id.NameIP);
        ScoreIP = findViewById(R.id.ScoreIP);
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnNext.setEnabled(false); //เปลี่ยนตรงนี้เป็น True เพื่อจะทดสอบหน้า 3
        myDatabaseHelper = new MyDatabaseHelper(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNext){
            Intent launchDisplay = new Intent(AddActivity.this, DisplayActivity.class);
            startActivity(launchDisplay);
        } else if (view.getId() == R.id.btnSave) {
            String codeStr, nameStr,scoreStr ;
            codeStr = CodeIP.getText().toString();
            nameStr = NameIP.getText().toString();
            scoreStr = ScoreIP.getText().toString();
            if(!codeStr.isEmpty() && !nameStr.isEmpty() && !scoreStr.isEmpty()){
                boolean results = myDatabaseHelper.insertData(codeStr,nameStr,scoreStr);
                if (results){
                    CodeIP.setText("");
                    NameIP.setText("");
                    ScoreIP.setText("");
                    count += 1;
                    countSubject.setText(count + " Subject");
                }
            }


            if (count == 5){
                btnSave.setEnabled(false);
                btnNext.setEnabled(true);
            }
        }
    }
}