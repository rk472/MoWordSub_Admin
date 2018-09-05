package studio.smartters.mowordsub_admin.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import studio.smartters.mowordsub_admin.Fragment.ViewBoothFragment;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.others.Constants;

public class EditDialogBooth extends Dialog {
    private EditText et_name;
    private Button btn_create;
    private Context c;
    private ViewBoothFragment fragment=ViewBoothFragment.getInstance();
    public EditDialogBooth(@NonNull Context context, final String id) {
        super(context);
        setContentView(R.layout.modal_create);
        et_name = findViewById(R.id.modal_create);
        btn_create = findViewById(R.id.modal_btn);
        c=context;
        btn_create.setText("Update");
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString().trim();

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(c, "Name can't be empty..", Toast.LENGTH_SHORT).show();
                }else {
                    fragment.p=new ProgressDialog(c);
                    fragment.p.setTitle("Please wait");
                    fragment.p.setMessage("please wait while we are updating the booth");
                    fragment.p.setCanceledOnTouchOutside(false);
                    fragment.p.setCancelable(false);
                    fragment.p.show();
                    EditBoothTask et=new EditBoothTask();
                    et.execute(Constants.URL+"editBooth?id="+id+"&name="+name);
                }
            }
        });
    }
    private class EditBoothTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url=new URL(strings[0]);
                URLConnection con=url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                String res="";
                while(data!=-1){
                    res+=(char)data;
                    data=ir.read();
                }
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fragment.p.dismiss();
            try {
                JSONObject json=new JSONObject(s);
                if(json.getBoolean("status")){
                    Toast.makeText(c, "Added Successfully", Toast.LENGTH_SHORT).show();
                    fragment.refreshBooth();
                    dismiss();
                }else{
                    Toast.makeText(c, "Some error occurred...try again later..", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
