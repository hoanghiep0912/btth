package com.example.tlustudents;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

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

        Student[] students = {
                new Student("001", "Le Van A", R.drawable.image_1),
                new Student("002", "Nguyen Thi B", R.drawable.image_1),
                new Student("003", "Tran Minh C", R.drawable.image_1),
                new Student("004", "Pham Duc D", R.drawable.image_1),
                new Student("005", "Hoang Tuan E", R.drawable.image_1),
                new Student("006", "Vu Anh F", R.drawable.image_1),
                new Student("007", "Dang Thuy G", R.drawable.image_1),
                new Student("008", "Bui Linh H", R.drawable.image_1),
                new Student("009", "Do Quang I", R.drawable.image_1),
                new Student("010", "Huynh Huu K", R.drawable.image_1)
        };

        recyclerView = (RecyclerView) findViewById(R.id.rcv_students);
        GridLayoutManager myGrid = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(myGrid);
        StudentsAdapter myAdapter = new StudentsAdapter(students);
        recyclerView.setAdapter(myAdapter);
    }
}