package studio.smartters.mowordsub_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.viewHolder.LabharthiViewHolder;

public class LabharthiAdapter extends RecyclerView.Adapter<LabharthiViewHolder> {
    private List<JSONObject> list;
    private Context c;
    public LabharthiAdapter(List<JSONObject> list, Context c) {
        this.c=c;
        this.list = list;
    }

    @NonNull
    @Override
    public LabharthiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.labharthi_row,parent,false);
        return new LabharthiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LabharthiViewHolder holder, int position) {
        holder.setData(list.get(position),c);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
