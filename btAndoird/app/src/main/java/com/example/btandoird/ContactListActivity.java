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


class ContactListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList = new ArrayList<>();
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBar = findViewById(R.id.searchBar);

        String type = getIntent().getStringExtra("type");
        if (type.equals("unit")) {
            loadUnitContacts();
        } else {
            loadStaffContacts();
        }

        adapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadUnitContacts() {
        contactList.add(new Contact("Phòng Đào tạo", "024-3852-2201", "daotao@tlu.edu.vn", "Hà Nội", "", "TLU"));
        contactList.add(new Contact("Phòng Công tác Sinh viên", "024-3852-2202", "ctsv@tlu.edu.vn", "Hà Nội", "", "TLU"));
    }

    private void loadStaffContacts() {
        contactList.add(new Contact("Nguyễn Văn A", "0987654321", "nva@tlu.edu.vn", "Hà Nội", "Giảng viên", "Khoa CNTT"));
        contactList.add(new Contact("Trần Thị B", "0971122334", "ttb@tlu.edu.vn", "Hà Nội", "Nhân viên", "Phòng Hành chính"));
    }

    private void filterList(String query) {
        List<Contact> filteredList = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase()) || contact.getPhone().contains(query)) {
                filteredList.add(contact);
            }
        }
        adapter = new ContactAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);
    }
}
