package studio.smartters.mowordsub_admin.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import studio.smartters.mowordsub_admin.R;

public class CreateDialogBooth extends Dialog {
    private EditText et_name;
    private Button btn_create;
    public CreateDialogBooth(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.modal_create);
        et_name = findViewById(R.id.modal_create);
        btn_create = findViewById(R.id.modal_btn);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "In Booth Fragment", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
