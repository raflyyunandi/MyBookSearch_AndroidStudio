package id.ac.unpas.booksearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mBookInput;
    private TextView mAuthorText, mTitleText;
    private Button btnHelp;
    private Button btnInput;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText) findViewById(R.id.bookInput);
        mAuthorText = (TextView) findViewById(R.id.authorText);
        mTitleText = (TextView) findViewById(R.id.titleText);

    }
    public void searchBooks(View view){
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0){
            new FetchBook(mTitleText, mAuthorText).execute(queryString);
            mAuthorText.setText("");
            mTitleText.setText("Loading..");
        } else {
            if (queryString.length() == 0){
                mAuthorText.setText("");
                mTitleText.setText("Please Input Title Book");
            } else {
                mTitleText.setText("");
                mAuthorText.setText("Please Check Your Connection");
            }
        }
    }
    public void helpBooks(View view){
        Toast.makeText(getApplicationContext(), "1. Apliaksi ini dapat digunakan " + "Untuk Mencari Buku" + "\n2. Pencarian hanya mengeluarkan 1 hasil, " + "Pastikan mencari judul dengan tepat.", Toast.LENGTH_LONG).show();
    }
}
