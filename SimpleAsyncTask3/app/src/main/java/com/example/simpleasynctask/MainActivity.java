package com.example.simpleasynctask; // Thay đổi tên package phù hợp với dự án mới của bạn

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private TextView mResultText;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
        mResultText = findViewById(R.id.textView1);
        mProgressBar = findViewById(R.id.progressBar);
    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();

        if (!queryString.isEmpty()) {
            mTitleText.setText("");
            mAuthorText.setText("");
            mResultText.setText("Searching...");
            mProgressBar.setVisibility(View.VISIBLE);

            new FetchBook(mTitleText, mAuthorText, mResultText, mProgressBar).execute(queryString);
        } else {
            mTitleText.setText("");
            mAuthorText.setText("");
            mResultText.setText("Please enter a search term");
        }
    }

    private static class FetchBook extends AsyncTask<String, Void, String> {
        private WeakReference<TextView> mTitleText;
        private WeakReference<TextView> mAuthorText;
        private WeakReference<TextView> mResultText;
        private WeakReference<ProgressBar> mProgressBar;

        FetchBook(TextView titleText, TextView authorText, TextView resultText, ProgressBar progressBar) {
            this.mTitleText = new WeakReference<>(titleText);
            this.mAuthorText = new WeakReference<>(authorText);
            this.mResultText = new WeakReference<>(resultText);
            this.mProgressBar = new WeakReference<>(progressBar);
        }

        @Override
        protected String doInBackground(String... strings) {
            return NetworkUtils.getBookInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Get references to the UI elements
            TextView titleTextView = mTitleText.get();
            TextView authorTextView = mAuthorText.get();
            TextView resultTextView = mResultText.get();
            ProgressBar progressBar = mProgressBar.get();

            try {
                if (s != null && titleTextView != null && authorTextView != null && resultTextView != null) {
                    JSONObject jsonObject = new JSONObject(s);

                    // Check if the JSON has items array
                    if (jsonObject.has("items")) {
                        JSONArray itemsArray = jsonObject.getJSONArray("items");

                        // Initialize variables to store title and author
                        int i = 0;
                        String title = null;
                        String author = null;

                        // Initialize counter for the while loop


                        // Iterate through the itemsArray array, checking each book for title and author information.
                        while (i < itemsArray.length() &&
                                (author == null && title == null)) {
                            // Get the current item information.
                            JSONObject currentBook = itemsArray.getJSONObject(i);
                            JSONObject currentVolumeInfo = currentBook.getJSONObject("volumeInfo");

                            // Try to get the author and title from the current item
                            try {
                                title = currentVolumeInfo.getString("title");

                                // Handle authors as JSONArray
                                if (currentVolumeInfo.has("authors")) {
                                    JSONArray authorsArray = currentVolumeInfo.getJSONArray("authors");
                                    if (authorsArray.length() > 0) {
                                        author = authorsArray.getString(0);
                                    }
                                }
                            } catch (Exception e) {
                                // If onPostExecute does not receive a proper JSON string,
// update the UI to show failed results.
                                mTitleText.get().setText(R.string.no_results);
                                mAuthorText.get().setText("");
                                e.printStackTrace();
                            }

                            // Move to the next item.
                            i++;
                        }

                        // If a matching response is found, update the UI with that response.
                        // Because the references to the TextView objects are WeakReference objects,
                        // you have to dereference them using the get() method.
                        if (title != null && author != null) {
                            mTitleText.get().setText(title);
                            mAuthorText.get().setText(author);
                        } else {
                            titleTextView.setText(R.string.no_results);
                            authorTextView.setText("");
                            resultTextView.setText("No complete results");
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing JSON", e);
                if (titleTextView != null) titleTextView.setText("No results found");
                if (authorTextView != null) authorTextView.setText("");
                if (resultTextView != null) resultTextView.setText("Error parsing data");
            } finally {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
            }
        }


    }
}
