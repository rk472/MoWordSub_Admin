package studio.smartters.mowordsub_admin.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import studio.smartters.mowordsub_admin.R;

public class RegisterSurveyFragment extends Fragment {
    private View root;
    private AppCompatActivity main;
    private EditText nameText,phoneText,passText;
    private Spinner panchayatSpinner,boothSpinner;
    private List<String> panchayatId,panchayatName,boothId,boothName;
    private Button registerButton;
    private ProgressDialog p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register_survey, container, false);
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("Register Now");
        nameText=root.findViewById(R.id.reg_name);
        phoneText=root.findViewById(R.id.reg_phone);
        passText=root.findViewById(R.id.reg_password);
        panchayatSpinner=root.findViewById(R.id.panchayat_spinner);
        boothSpinner=root.findViewById(R.id.booth_spinner);
        registerButton=root.findViewById(R.id.register_button);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String mandal=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).getString("id","0");
        PanchayatTask pt=new PanchayatTask();
        pt.execute("http://205.147.101.127:8084/MoWord/getWards?mandal="+mandal);
        panchayatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                registerButton.setEnabled(false);
                String ward_id=panchayatId.get(position);
                BoothTask bt=new BoothTask();
                bt.execute("http://205.147.101.127:8084/MoWord/getBooths?ward="+ward_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameText.getText().toString().trim();
                String phone=phoneText.getText().toString().trim();
                String pass=passText.getText().toString().trim();
                String booth_id=boothId.get(boothSpinner.getSelectedItemPosition());
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||TextUtils.isEmpty(pass)){
                    Toast.makeText(main, "All Fields must be filled..", Toast.LENGTH_SHORT).show();
                }else{
                    p=new ProgressDialog(getActivity());
                    p.setTitle("Please Wait");
                    p.setMessage("Please Wait While we are registering");
                    p.setCanceledOnTouchOutside(false);
                    p.setCancelable(false);
                    RegisterTask rt=new RegisterTask();
                    rt.execute("http://205.147.101.127:8084/MoWord/addServeyMan?name="+name+"&phone="+phone+"&pass="+pass+"&booth="+booth_id);
                }
            }
        });
    }

    private class PanchayatTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection con= url.openConnection();
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
            panchayatId=new ArrayList<>();
            panchayatName=new ArrayList<>();
            if(s!=null) {
                try {
                    JSONArray arr = new JSONArray(s);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json = arr.getJSONObject(i);
                        panchayatId.add(json.getString("id"));
                        panchayatName.add(json.getString("name"));
                    }
                    ArrayAdapter<String> aa = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, panchayatName);
                    panchayatSpinner.setAdapter(aa);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(s);
        }
    }
    private class BoothTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection con= url.openConnection();
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
            boothId=new ArrayList<>();
            boothName=new ArrayList<>();
            try {
                JSONArray arr=new JSONArray(s);
                for(int i=0;i<arr.length();i++){
                    JSONObject json = arr.getJSONObject(i);
                    boothId.add(json.getString("id"));
                    boothName.add(json.getString("name"));
                }
                ArrayAdapter<String> aa=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,boothName);
                boothSpinner.setAdapter(aa);
                if(boothName.size()>0){
                    registerButton.setEnabled(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    private class RegisterTask extends AsyncTask<String,Void,String>{

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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            p.dismiss();
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                if(jsonObject.getBoolean("status")){
                    Toast.makeText(main, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    nameText.setText("");
                    passText.setText("");
                    phoneText.setText("");
                }else{
                    Toast.makeText(main, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
