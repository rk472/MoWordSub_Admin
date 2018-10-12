package studio.smartters.mowordsub_admin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.viewHolder.DataViewHolder;


public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private List<JSONObject> arr;
    private AppCompatActivity a;
    private char c='n';
    public DataAdapter(List<JSONObject> arr, AppCompatActivity a){
        this.arr=arr;
        this.a=a;
    }
    public DataAdapter(List<JSONObject> arr, AppCompatActivity a,char c){
        this.arr=arr;
        this.a=a;
        this.c=c;
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(a).inflate(R.layout.person_row,parent,false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        try {
            holder.setHof(arr.get(position).getString("phead"));
            holder.setName(arr.get(position).getString("pname"));
            holder.setCall(arr.get(position).getString("pcontact"),a);
            holder.setClick(arr.get(position).toString(),a);
            if(c=='a')
                holder.setEditAdhar(a,arr.get(position).getString("id"));
            else if(c=='v')
                holder.setEditVoter(a,arr.get(position).getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}
