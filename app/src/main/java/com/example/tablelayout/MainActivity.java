package com.example.tablelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private TableView tableView;
    private String[] headTitles = {"类型","金额","单价","日期"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableView = findViewById(R.id.table);
        tableView.setTable(4, 4, new OnTableClick() {
            @Override
            public void onTableClickListener(int row, int col) {
                Toast.makeText(MainActivity.this,"row:"+row+"col:"+col,Toast.LENGTH_SHORT).show();
            }
        });//几行几列
        tableView.setTableHead(headTitles);
        tableView.setTableContent();
    }
}