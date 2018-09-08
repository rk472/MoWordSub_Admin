package studio.smartters.mowordsub_admin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import studio.smartters.mowordsub_admin.AddImageActivity;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.adapter.ImageAdapter;
import studio.smartters.mowordsub_admin.others.Constants;

public class ImageFragment extends Fragment {
    private RequestQueue r;
    private String url= Constants.URL+"getPhotos";
    private List<String> name,path,id;
    private RecyclerView list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View v;
    private FloatingActionButton fab;
    private LinearLayout ln;
    private static ImageFragment inst;
    private AppCompatActivity main;
    public ImageFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_image, container, false);
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("View Image");
        list=v.findViewById(R.id.image_list);
        ln=v.findViewById(R.id.image_error);
        fab=v.findViewById(R.id.add_image);
        swipeRefreshLayout=v.findViewById(R.id.swipe_image);
        inst=this;
        Cache c=new DiskBasedCache(getActivity().getCacheDir(),1024*1024);
        Network n=new BasicNetwork(new HurlStack());
        r=new RequestQueue(c,n);
        return v;
    }

    public static ImageFragment getInstance() {
        return inst;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddImageActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_image);
        refresh();
    }

    public void refresh(){
        if(isNetworkAvailable()) {
            name = new ArrayList<>();
            path = new ArrayList<>();
            id   = new ArrayList<>();
            r.start();
            JsonArrayRequest j = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    int count = 0;
                    while (count < response.length()) {
                        try {
                            JSONObject j = response.getJSONObject(count);
                            name.add(j.getString("name"));
                            path.add(j.getString("url"));
                            id.add(j.getString("id"));
                            count++;
                        } catch (JSONException e) {

                        }
                        ImageAdapter p = new ImageAdapter(getActivity(), name, path,id);
                        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        list.setHasFixedSize(true);
                        list.setAdapter(p);
                        ln.setVisibility(View.GONE);
                    }

                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            r.add(j);
        }else{
            ln.setVisibility(View.VISIBLE);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
