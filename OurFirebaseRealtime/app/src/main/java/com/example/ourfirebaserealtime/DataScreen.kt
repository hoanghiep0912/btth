package com.example.ourfirebaserealtime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class User(
    val email: String = "",
    val createdAt: Long = 0
)

@Composable
fun DataScreen(
    onNavigateBack: () -> Unit
) {
    var users by remember { mutableStateOf<List<Pair<String, User>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val database = Firebase.database
    val usersRef = database.reference.child("users")

    // Load data from Firebase
    LaunchedEffect(Unit) {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersList = mutableListOf<Pair<String, User>>()

                for (userSnapshot in snapshot.children) {
                    val userId = userSnapshot.key ?: continue
                    val userData = userSnapshot.getValue(User::class.java)
                    if (userData != null) {
                        usersList.add(Pair(userId, userData))
                    }
                }

                users = usersList
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                errorMessage = "Lỗi: ${error.message}"
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Danh sách người dùng",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        } else if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else if (users.isEmpty()) {
            Text(
                text = "Không có dữ liệu người dùng",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(users) { (id, user) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "ID: $id")
                            Text(text = "Email: ${user.email}")
                            Text(text = "Tạo lúc: ${java.util.Date(user.createdAt)}")
                        }
                    }
                }
            }
        }

        Button(
            onClick = onNavigateBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Quay lại")
        }
    }
}
