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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import studio.smartters.mowordsub_admin.Dialog.CreateDialogPanchayat;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.adapter.WardAdapter;
import studio.smartters.mowordsub_admin.others.Constants;

public class ViewWardFragment extends Fragment {
    private View root;
    private AppCompatActivity main;
    private FloatingActionButton fab;
    private List<String> names,ids;
    private RecyclerView list;
    public ProgressDialog p;
    private static ViewWardFragment  inst;
    private SwipeRefreshLayout swipe;
    private LinearLayout ln;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_view_ward, container, false);
        inst=this;
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("View Ward/Panchayat");
        swipe=root.findViewById(R.id.swipe_ward);
        ln=root.findViewById(R.id.ward_error);
        list=root.findViewById(R.id.panchayat_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fab = root.findViewById(R.id.add_panchayat_fab);


        refresh();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipe.setRefreshing(false);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CreateDialogPanchayat dialog= new CreateDialogPanchayat(main);
                dialog.show();
            }
        });
    }

    public static ViewWardFragment getInstance(){
        return  inst;
    }
    public String getid(){
        return getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).getString("id","");
    }
    public void refresh(){
        if(Constants.isNetworkAvailable(getActivity())) {
            String id = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).getString("id", "");
            GetWordTask gt = new GetWordTask();
            gt.execute(Constants.URL + "getWards?mandal=" + id);
            ln.setVisibility(View.GONE);
        }else{
            ln.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_panchayat);
    }

    private class GetWordTask extends AsyncTask<String,Void,String>{

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
            super.onPostExecute(s);
            try {
                JSONArray arr=new JSONArray(s);
                names=new ArrayList<>();
                ids=new ArrayList<>();
                for(int i=0;i<arr.length();i++){
                    JSONObject json=arr.getJSONObject(i);
                    names.add(json.getString("name"));
                    ids.add(json.getString("id"));
                    WardAdapter a=new WardAdapter(getActivity(),names,ids);
                    list.setAdapter(a);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
