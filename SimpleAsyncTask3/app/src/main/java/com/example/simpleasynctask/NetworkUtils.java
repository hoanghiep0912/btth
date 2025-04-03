package com.example.simpleasynctask;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";

    public static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            // Xây dựng URI cho request
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            // Chuyển đổi URI thành URL
            URL requestURL = new URL(builtURI.toString());
            Log.d(LOG_TAG, "URL: " + requestURL);

            // Mở kết nối HTTP
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Lấy InputStream từ kết nối
            InputStream inputStream = urlConnection.getInputStream();

            // Tạo BufferedReader từ InputStream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Sử dụng StringBuilder để đọc phản hồi
            StringBuilder builder = new StringBuilder();

            // Đọc phản hồi dòng theo dòng
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            // Kiểm tra nếu builder trống
            if (builder.length() == 0) {
                return null;
            }

            // Chuyển đổi StringBuilder thành String
            bookJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Trả về chuỗi JSON
        return bookJSONString;
    }
}
