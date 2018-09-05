package studio.smartters.mowordsub_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import studio.smartters.mowordsub_admin.others.Constants;

public class LoginActivity extends AppCompatActivity {
    private EditText userText,passText;
    private ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userText=findViewById(R.id.login_username);
        passText=findViewById(R.id.login_password);
    }

    public void goHome(View view) {
        String user=userText.getText().toString().trim();
        String pass=passText.getText().toString().trim();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Toast.makeText(this, "You must fill both the fields", Toast.LENGTH_SHORT).show();
        }else{
            p=new ProgressDialog(this);
            p.setTitle("please wait");
            p.setMessage("Please wait while we are logging you in");
            p.setCancelable(false);
            p.setCanceledOnTouchOutside(false);
            p.show();
            LoginTask lt=new LoginTask();
            lt.execute(Constants.URL+"loginSubAdmin?user="+user+"&pass="+pass);

        }
    }
    private  class LoginTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
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
                Log.i("err",e.getMessage());
            }
            JSONObject json=new JSONObject();
            try {
                json.put("status",0);
            } catch (JSONException e) {
                Log.i("err",e.getMessage());
            }
            return json.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            try {
                JSONObject json=new JSONObject(s);
                if(json.getString("status").equals("1")){
                    SharedPreferences sharedPreferences=LoginActivity.this.getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("login",true);
                    editor.apply();
                    editor.putString("id",json.getString("id"));
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.i("err",e.getMessage());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences=LoginActivity.this.getSharedPreferences("login",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("login",false)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
}
