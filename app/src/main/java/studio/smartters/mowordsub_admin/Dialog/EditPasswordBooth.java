package studio.smartters.mowordsub_admin.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import studio.smartters.mowordsub_admin.ViewSurveyManActivity;
import studio.smartters.mowordsub_admin.others.Constants;

public class EditPasswordBooth extends Dialog {
    private EditText et_pass,et_own_pass;
    private Button btn_create;
    private AppCompatActivity c;
    private String id;
    private ProgressDialog p;
    private ViewSurveyManActivity fragment=ViewSurveyManActivity.getInstance();
    public EditPasswordBooth(@NonNull AppCompatActivity context, final String id) {
        super(context);
        setContentView(R.layout.modal_password);
        et_pass = findViewById(R.id.modal_password);
        et_own_pass = findViewById(R.id.modal_own_password);
        btn_create = findViewById(R.id.modal_btn);
        c=context;
        this.id=id;
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=et_pass.getText().toString().trim();
                String ownpassword=et_own_pass.getText().toString().trim();
                if(TextUtils.isEmpty(password)||TextUtils.isEmpty(ownpassword)) {
                    Toast.makeText(c, "Passwords can't be empty..", Toast.LENGTH_SHORT).show();
                }else {
                    fragment.p=new ProgressDialog(c);
                    fragment.p.setTitle("Please wait");
                    fragment.p.setMessage("We are updating the Password");
                    fragment.p.setCanceledOnTouchOutside(false);
                    fragment.p.setCancelable(false);
                    fragment.p.show();
                    EditBoothTask et=new EditBoothTask();
                    String myId=c.getSharedPreferences("login",Context.MODE_PRIVATE).getString("id","0");
                    et.execute(Constants.URL+"changeSurveyPassword?myId="+myId+"&id="+id+"&myPass="+ownpassword+"&pass="+password);
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
            } catch (IOException e) {
                return "Can't reach Server ";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fragment.p.dismiss();
            try {
                JSONObject json=new JSONObject(s);
                if(json.getBoolean("status")) {
                    Toast.makeText(c, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    fragment.refresh("");
                    dismiss();
                }else{
                    Toast.makeText(c, "Either your password is wrong or You are giving the old password of the user", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
