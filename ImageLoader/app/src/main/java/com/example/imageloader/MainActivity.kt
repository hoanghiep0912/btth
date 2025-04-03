package com.example.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var editTextImageUrl: EditText
    private lateinit var buttonLoadImage: Button
    private lateinit var imageViewResult: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các thành phần UI
        editTextImageUrl = findViewById(R.id.editTextImageUrl)
        buttonLoadImage = findViewById(R.id.buttonLoadImage)
        imageViewResult = findViewById(R.id.imageViewResult)
        progressBar = findViewById(R.id.progressBar)

        // Thiết lập sự kiện click cho nút tải ảnh
        buttonLoadImage.setOnClickListener {
            val imageUrl = editTextImageUrl.text.toString().trim()

            if (imageUrl.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập URL ảnh", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo và thực thi AsyncTask để tải ảnh
            ImageDownloader().execute(imageUrl)
        }
    }

    private fun setContentView(activityMain: Int) {

    }

    /**
     * AsyncTask để tải ảnh từ URL
     * - String: Tham số đầu vào (URL ảnh)
     * - Integer: Tiến trình tải (phần trăm)
     * - Bitmap: Kết quả trả về (ảnh đã tải)
     */
    private inner class ImageDownloader : AsyncTask<String, Int, Bitmap?>() {

        override fun onPreExecute() {
            // Hiển thị progress bar trước khi bắt đầu tải
            progressBar.visibility = View.VISIBLE
            // Xóa ảnh hiện tại (nếu có)
            imageViewResult.setImageBitmap(null)
        }

        override fun doInBackground(vararg params: String): Bitmap? {
            val imageUrl = params[0]
            var bitmap: Bitmap? = null

            try {
                // Tạo kết nối HTTP
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                // Lấy kích thước file để tính toán tiến trình
                val contentLength = connection.contentLength
                var totalBytesRead = 0

                // Mở input stream và đọc dữ liệu
                val input: InputStream = connection.inputStream
                val buffer = ByteArray(8192) // 8KB buffer
                val output = java.io.ByteArrayOutputStream()

                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead

                    // Cập nhật tiến trình nếu biết kích thước file
                    if (contentLength > 0) {
                        val progress = (totalBytesRead * 100 / contentLength)
                        publishProgress(progress)
                    }
                }

                // Chuyển đổi dữ liệu thành Bitmap
                val imageData = output.toByteArray()
                bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

                // Đóng các stream
                output.close()
                input.close()
                connection.disconnect()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

        override fun onProgressUpdate(vararg values: Int?) {
            // Cập nhật tiến trình trên UI
            val progress = values[0] ?: 0
            progressBar.progress = progress
        }

        override fun onPostExecute(result: Bitmap?) {
            // Ẩn progress bar
            progressBar.visibility = View.GONE

            // Hiển thị ảnh hoặc thông báo lỗi
            if (result != null) {
                imageViewResult.setImageBitmap(result)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Không thể tải ảnh. Vui lòng kiểm tra URL và thử lại.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
