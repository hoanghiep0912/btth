package com.example.newbmicalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private RadioButton rdoMale, rdoFemale;
    private EditText edtAge, edtFeet, edtInches, edtWeight;
    private Button btnCalculate;
    private TextView txtBMI, txtMessage;

    @SuppressLint("MissingInflatedId")
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
        //tham chieu phan tu
        rdoMale = (RadioButton) findViewById(R.id.rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.rdo_female);
        edtAge = (EditText) findViewById(R.id.edt_age);
        edtFeet = (EditText) findViewById(R.id.edt_feet);
        edtInches = (EditText) findViewById(R.id.edt_inches);
        edtWeight = (EditText) findViewById(R.id.edt_weight);
        btnCalculate = (Button) findViewById(R.id.btn_calculate);
        txtBMI = (TextView) findViewById(R.id.txt_bmi);
        txtMessage = (TextView) findViewById(R.id.txt_message);
    }
}
