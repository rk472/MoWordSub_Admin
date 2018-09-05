package studio.smartters.mowordsub_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.viewHolder.WardHolder;

public class WardAdapter extends RecyclerView.Adapter<WardHolder> {
    private Context c;
    private List<String> names,ids;

    public WardAdapter(Context c, List<String> names,List<String> ids){
        this.names=names;
        this.ids=ids;
        this.c=c;
    }
    @NonNull
    @Override
    public WardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.panchayat_row,parent,false);
        return new WardHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WardHolder holder, int position) {
        holder.setName(names.get(position));
        //Toast.makeText(c, names.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
