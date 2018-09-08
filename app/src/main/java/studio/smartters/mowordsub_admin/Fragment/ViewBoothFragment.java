package studio.smartters.mowordsub_admin.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

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

import studio.smartters.mowordsub_admin.Dialog.CreateDialogBooth;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.adapter.BoothAdapter;
import studio.smartters.mowordsub_admin.others.Constants;

public class ViewBoothFragment extends Fragment {
    private View root;
    private AppCompatActivity main;
    private FloatingActionButton fab;
    private List<String> boothName,boothId,panchayatId,panchayatName;
    private RecyclerView list;
    public ProgressDialog p;
    private static ViewBoothFragment inst;
    private Spinner panchayatSpinner;
    private SwipeRefreshLayout refresh;
    private LinearLayout ln;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_view_booth, container, false);
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("View Booth");
        panchayatSpinner=root.findViewById(R.id.select_panchayat);
        refresh=root.findViewById(R.id.swipe_booth);
        ln=root.findViewById(R.id.booth_error);
        list=root.findViewById(R.id.booth_list);
        inst=this;
        list.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        list.setHasFixedSize(true);
        fab = root.findViewById(R.id.add_booth_fab);
        return root;
    }
    public static ViewBoothFragment getInstance(){
        return  inst;
    }
    public String getMandalId(){
        return getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).getString("id","0");
    }
    public String getWardId(){
        return panchayatId.get(panchayatSpinner.getSelectedItemPosition());
    }
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView =  main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_booth);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CreateDialogBooth dialog= new CreateDialogBooth(main);
                dialog.show();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWard();
                refresh.setRefreshing(false);
            }
        });
        refreshWard();
        panchayatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshBooth();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void refreshBooth() {
        if(Constants.isNetworkAvailable(getActivity())) {
            BoothTask bt = new BoothTask();
            bt.execute(Constants.URL + "getBooths?ward=" + panchayatId.get(panchayatSpinner.getSelectedItemPosition()));
            ln.setVisibility(View.GONE);
        }else{
            ln.setVisibility(View.VISIBLE);
        }


    }

    public void refreshWard(){
        if(Constants.isNetworkAvailable(getActivity())) {
            String mandal=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).getString("id","0");
            PanchayatTask pt=new PanchayatTask();
            pt.execute("http://205.147.101.127:8084/MoWord/getWards?mandal="+mandal);
            ln.setVisibility(View.GONE);
        }else{
            ln.setVisibility(View.VISIBLE);
        }
    }
    private class PanchayatTask extends AsyncTask<String,Void,String> {

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
            try {
                JSONArray arr=new JSONArray(s);
                for(int i=0;i<arr.length();i++){
                    JSONObject json = arr.getJSONObject(i);
                    panchayatId.add(json.getString("id"));
                    panchayatName.add(json.getString("name"));
                }
                ArrayAdapter<String> aa=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,panchayatName);
                panchayatSpinner.setAdapter(aa);
            } catch (JSONException e) {
                e.printStackTrace();
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
                BoothAdapter a=new BoothAdapter(getActivity(),boothName,boothId);
                list.setAdapter(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

}
