package studio.smartters.mowordsub_admin;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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
import studio.smartters.mowordsub_admin.adapter.LabharthiAdapter;
import studio.smartters.mowordsub_admin.others.Constants;

public class LabharthiListActivity extends AppCompatActivity {
    private EditText searchName;
    private Spinner schemeSelect;
    private RecyclerView list;
    private String name="",atal="1",sukanya="0",surakhya="0",other="0",ujwala="0",id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labharthi_list);
        searchName=findViewById(R.id.search_scheme_name);
        schemeSelect=findViewById(R.id.scheme_select);
        list=findViewById(R.id.labharthi_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        id=getSharedPreferences("login",MODE_PRIVATE).getString("id","0");
        refresh();
        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name=s.toString();
                refresh();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        schemeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=schemeSelect.getSelectedItem().toString();
                if(s.equals("Atal Pension Yojna")) {
                    atal = "1";
                    sukanya = "0";
                    surakhya = "0";
                    ujwala = "0";
                    other = "0";
                }else if(s.equals("Ujjwala Yojna")) {
                    atal = "0";
                    sukanya = "0";
                    surakhya = "0";
                    ujwala = "1";
                    other = "0";
                }else if(s.equals("Sukanya Yojna")){
                    atal = "0";
                    sukanya = "1";
                    surakhya = "0";
                    ujwala = "0";
                    other = "0";
                }else if(s.equals("Surakhya Yojna")){
                    atal = "0";
                    sukanya = "0";
                    surakhya = "1";
                    ujwala = "0";
                    other = "0";
                }else if(s.equals("Others")){
                    atal = "0";
                    sukanya = "0";
                    surakhya = "0";
                    ujwala = "0";
                    other = "1";
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void refresh(){
        GetDataTask gt=new GetDataTask();
        gt.execute(Constants.URL+"getLabharthiBySubAdmin?id="+id+"&atal="+atal+"&ujwala="+ujwala+"&sukanya="+sukanya+"&surakhya="+surakhya+"&other="+other+"&name="+name);
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
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray arr = new JSONArray(s);
                if (arr.length() == 0)
                    Snackbar.make(list, "No data found", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                List<JSONObject> jsonList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    jsonList.add(arr.getJSONObject(i));
                    Log.e("arr", arr.getJSONObject(i).toString());
                }

                LabharthiAdapter d = new LabharthiAdapter(jsonList, LabharthiListActivity.this);
                list.setAdapter(d);

            } catch(JSONException e){
                Toast.makeText(LabharthiListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
