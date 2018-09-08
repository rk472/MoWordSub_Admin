package studio.smartters.mowordsub_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.viewHolder.BoothHolder;
import studio.smartters.mowordsub_admin.viewHolder.WardHolder;

public class BoothAdapter extends RecyclerView.Adapter<BoothHolder> {
    private Context c;
    private List<String> names,ids;

    public BoothAdapter(Context c,List<String> names,List<String> ids){
        this.names=names;
        this.ids=ids;
        this.c=c;
    }
    @NonNull
    @Override
    public BoothHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.booth_row,parent,false);
        return new BoothHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoothHolder holder, int position) {
        holder.setName(names.get(position));
        holder.edit(ids.get(position),c);
        holder.setClick(ids.get(position),c);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
