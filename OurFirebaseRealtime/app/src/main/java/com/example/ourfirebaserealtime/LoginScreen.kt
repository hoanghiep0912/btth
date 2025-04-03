package com.example.ourfirebaserealtime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(
    onNavigateToData: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Sử dụng Firebase.auth thay vì FirebaseAuth.getInstance()
    val auth = Firebase.auth
    // Sử dụng Firebase.database thay vì FirebaseDatabase.getInstance()
    val database = Firebase.database

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Đăng nhập / Đăng ký",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Error message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Đăng ký button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Lưu thông tin người dùng vào database
                                    val user = auth.currentUser
                                    user?.let { firebaseUser ->
                                        val userRef = database.reference.child("users").child(firebaseUser.uid)
                                        val userData = HashMap<String, Any>()
                                        userData["email"] = email
                                        userData["createdAt"] = System.currentTimeMillis()

                                        userRef.setValue(userData)
                                            .addOnSuccessListener {
                                                errorMessage = "Đăng ký thành công!"
                                            }
                                            .addOnFailureListener { e ->
                                                errorMessage = "Lỗi lưu dữ liệu: ${e.message}"
                                            }
                                    }
                                } else {
                                    errorMessage = "Đăng ký thất bại: ${task.exception?.message}"
                                }
                            }
                    } else {
                        errorMessage = "Vui lòng nhập email và mật khẩu"
                    }
                }
            ) {
                Text("Đăng ký")
            }

            // Đăng nhập button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    errorMessage = "Đăng nhập thành công!"
                                    onNavigateToData()
                                } else {
                                    errorMessage = "Đăng nhập thất bại: ${task.exception?.message}"
                                }
                            }
                    } else {
                        errorMessage = "Vui lòng nhập email và mật khẩu"
                    }
                }
            ) {
                Text("Đăng nhập")
            }
        }

        // Hiển thị dữ liệu button
        Button(
            onClick = onNavigateToData,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hiển thị dữ liệu")
        }
    }
}