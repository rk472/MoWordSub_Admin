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
import studio.smartters.mowordsub_admin.NoVoterActivity;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.others.Constants;

public class AddVoterDialog extends Dialog {
    private EditText et_name;
    private Button btn_create;
    private NoVoterActivity fragment=NoVoterActivity.getInstance();
    private Context c;
    public AddVoterDialog(@NonNull final Context context, final String id) {
        super(context);
        setContentView(R.layout.modal_voter);
        //Toast.makeText(context, id+"", Toast.LENGTH_SHORT).show();
        et_name = findViewById(R.id.modal_create);
        btn_create = findViewById(R.id.modal_btn);
        c=context;
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString().trim();

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "Voter ID can't be empty..", Toast.LENGTH_SHORT).show();
                }else{
                    fragment.p=new ProgressDialog(c);
                    fragment.p.setTitle("Please wait");
                    fragment.p.setMessage("Please wait while we are adding Voter ID");
                    fragment.p.setCanceledOnTouchOutside(false);
                    fragment.p.setCancelable(false);
                    fragment.p.show();
                    AddBoothTask at=new AddBoothTask();
                    at.execute(Constants.URL+"editNoVoter?id="+id+"&voter="+name);

                }
            }
        });
    }
    private class AddBoothTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url=new URL(strings[0]);
                URLConnection con=url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                StringBuilder res= new StringBuilder();
                while(data!=-1){
                    res.append((char) data);
                    data=ir.read();
                }
                return res.toString();
            } catch (IOException e) {
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fragment.p.dismiss();
            try {
                Log.e("err",s);
                JSONObject json=new JSONObject(s);
                if(json.getBoolean("status")){
                    Toast.makeText(c, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    fragment.refresh("");
                    dismiss();
                }else{
                    Toast.makeText(c, "Some error occurred...try again later..", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(fragment, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
