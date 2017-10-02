package  <%= appPackage %>;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devknigthzz.androidtemplete.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
