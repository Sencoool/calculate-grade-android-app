package com.example.assignment5;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button btnClose;
    ListView listView; // ปรับ width เป็น Match_parent
    TextView gpa;
    MyDatabaseHelper myDatabaseHelper;
    String [] items = new String[5], getAllscore = new String[5];
    String showGPA = "";
    Double [] getAllPoint = new Double[5];
    double total_point = 0, total_credit = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gpa = findViewById(R.id.showGrade);
        // ปุ่มปิดโปรแกรม
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnClose){
                    finishAffinity();
                }
            }
        });
        // เอาข้อมูลจาก DB
        myDatabaseHelper = new MyDatabaseHelper(this); // ลืม Instance แล้วโปรแกรมเด้ง !!!
        Cursor res = myDatabaseHelper.getAllData(); // cursor res เริ่มต้นที่ -1 ต้อง moveToNext() ก่อนดึงค่า

        for (int i = 0; i < 5; i++) {
            String codeStr, nameStr, scoreStr;
            if (res != null && res.getCount() > 0) {
                res.moveToNext();

                codeStr = res.getString(1);
                nameStr = res.getString(2);
                scoreStr = res.getString(3);
                getAllPoint[i] = checkScore(scoreStr);
                getAllscore[i] = res.getString(3);
                // ทำข้อมูลเกรดแต่ละวิชาที่รวมรหัสวิชา,ชื่อ,แล้วก็คะแนนเป็นตัวอักษรอังกฤษเอาไปใส่ ListView
                items[i] = codeStr + " : " + nameStr + " : " + checkGrade(scoreStr);
            }
        }

        // เอาข้อมูลใส่ ListView
        listView = findViewById(R.id.gradeView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        showGPA = "Total Points : " + ((total_point * total_credit) / items.length) + "\n";
        showGPA += "Total Credits : " + total_credit + "\n";
        showGPA += String.format("Grade Point Average(GPA) : %.2f", ((total_point * total_credit) / items.length) / total_credit);
        gpa.setText(showGPA);
    }

    private String checkGrade(String scoreStr) {
        int score = Integer.parseInt(scoreStr);
        if (score >= 80){
            return "A";
        } else if (score >= 75) {
            return "B+";
        } else if (score >= 70) {
            return "B";
        } else if (score >= 65) {
            return "C+";
        } else if (score >= 60) {
            return "C";
        } else if (score >= 55) {
            return "D+";
        } else if (score >= 50) {
            return "D";
        }
        return "F";
    }

    private double checkScore(String scoreStr) {
        int score = Integer.parseInt(scoreStr);
        double point = 0.0;
        if (score >= 80) {
            point = 4.0;
        } else if (score >= 75) {
            point = 3.5;
        } else if (score >= 70) {
            point = 3.0;
        } else if (score >= 65) {
            point = 2.5;
        } else if (score >= 60) {
            point = 2.0;
        } else if (score >= 55) {
            point = 1.5;
        } else if (score >= 50) {
            point = 1.0;
        }
        total_point += point;
        return point;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String itemValue = (String) listView.getItemAtPosition(i);
        String[] data = itemValue.split(" : "); // แยกข้อมูล listView ด้วย :

        String toastMessage = data[0] + " : ";
        toastMessage += data[1] + " : ";
        toastMessage += getAllscore[i] + " : ";
        toastMessage += data[2] + " : ";
        toastMessage += getAllPoint[i];

        Toast.makeText(this,"Data : " + toastMessage,Toast.LENGTH_SHORT).show();
    }
}