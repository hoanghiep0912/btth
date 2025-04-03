package com.example.oursharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    // Khai báo các biến UI
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonShow: Button
    private lateinit var textViewResult: TextView

    // Khai báo SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferenceHelper: PreferenceHelper

    // Các key để lưu trữ dữ liệu
    private val PREF_NAME = "UserPrefs"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceHelper = PreferenceHelper.getInstance(this)

        // Khởi tạo các thành phần UI
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSave = findViewById(R.id.buttonSave)
        buttonClear = findViewById(R.id.buttonClear)
        buttonShow = findViewById(R.id.buttonShow)
        textViewResult = findViewById(R.id.textViewResult)

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Xử lý sự kiện khi nhấn nút Lưu
        buttonSave.setOnClickListener {
            saveUserData()
        }

        // Xử lý sự kiện khi nhấn nút Xóa
        buttonClear.setOnClickListener {
            clearUserData()
        }

        // Xử lý sự kiện khi nhấn nút Hiển thị
        buttonShow.setOnClickListener {
            showUserData()
        }

        // Tải dữ liệu đã lưu (nếu có) khi khởi động ứng dụng
        loadSavedData()
    }

    // Hàm lưu dữ liệu người dùng
    private fun saveUserData() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        // Lưu dữ liệu vào SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()

        Toast.makeText(this, "Đã lưu thông tin thành công", Toast.LENGTH_SHORT).show()
    }

    // Hàm xóa dữ liệu người dùng
    private fun clearUserData() {
        // Xóa dữ liệu từ SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Xóa dữ liệu từ các trường nhập
        editTextUsername.text.clear()
        editTextPassword.text.clear()
        textViewResult.text = ""

        Toast.makeText(this, "Đã xóa thông tin", Toast.LENGTH_SHORT).show()
    }

    // Hàm hiển thị dữ liệu người dùng
    private fun showUserData() {
        // Đọc dữ liệu từ SharedPreferences
        val username = sharedPreferences.getString(KEY_USERNAME, "")
        val password = sharedPreferences.getString(KEY_PASSWORD, "")

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            textViewResult.text = "Không có dữ liệu được lưu trữ"
        } else {
            textViewResult.text = "Tên người dùng: $username\nMật khẩu: $password"
        }
    }

    // Hàm tải dữ liệu đã lưu
    private fun loadSavedData() {
        val username = sharedPreferences.getString(KEY_USERNAME, "")
        val password = sharedPreferences.getString(KEY_PASSWORD, "")

        if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            editTextUsername.setText(username)
            editTextPassword.setText(password)
        }
    }
}