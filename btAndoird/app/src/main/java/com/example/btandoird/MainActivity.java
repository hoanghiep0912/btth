package com.example.btandoird;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openUnitDirectory(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("type", "unit");
        startActivity(intent);
    }

    public void openStaffDirectory(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("type", "staff");
        startActivity(intent);
    }
}