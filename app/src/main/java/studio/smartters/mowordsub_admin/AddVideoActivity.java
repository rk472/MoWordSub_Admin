package studio.smartters.mowordsub_admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddVideoActivity extends AppCompatActivity {
    private EditText etCaption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        etCaption = findViewById(R.id.caption_video);
    }

    public void chooseVideo(View view) {
    }

    public void uploadVideo(View view) {
    }
}
