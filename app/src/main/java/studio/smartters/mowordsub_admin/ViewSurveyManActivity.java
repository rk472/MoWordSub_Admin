package studio.smartters.mowordsub_admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import studio.smartters.mowordsub_admin.adapter.DataAdapter;
import studio.smartters.mowordsub_admin.adapter.SurveyManAdapter;
import studio.smartters.mowordsub_admin.others.Constants;
import studio.smartters.mowordsub_admin.others.SurveyMan;

public class ViewSurveyManActivity extends AppCompatActivity {
    private RecyclerView list;
    private String id;
    private static ViewSurveyManActivity inst;
    public ProgressDialog p;
    public static ViewSurveyManActivity getInstance(){
        return inst;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_survey_man);
        list=findViewById(R.id.survey_man_list);
        inst=this;
        id=getSharedPreferences("login",MODE_PRIVATE).getString("id","0");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        refresh("");
    }
    public void refresh(String name){
        GetDataTask gt=new GetDataTask();
        gt.execute(Constants.URL+"getAllSurveyMan?id="+id+"&name="+name);
    }
    private class GetDataTask extends AsyncTask<String,Void,String> {

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
                return "can't reach server";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray arr = new JSONArray(s);
                if (arr.length() == 0)
                    Snackbar.make(list, "No data found", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                List<SurveyMan> jsonList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json=arr.getJSONObject(i);
                    jsonList.add(new SurveyMan(json.getString("name"),json.getString("booth"),json.getString("ward"),json.getString("phone"),json.getString("total"),json.getString("id")));
                    Log.e("arr", arr.getJSONObject(i).toString());
                }

                SurveyManAdapter d = new SurveyManAdapter( ViewSurveyManActivity.this,jsonList);
                list.setAdapter(d);

            } catch(JSONException e){
                Toast.makeText(ViewSurveyManActivity.this, s, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
