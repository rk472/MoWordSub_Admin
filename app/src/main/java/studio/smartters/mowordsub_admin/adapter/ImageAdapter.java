package studio.smartters.mowordsub_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.viewHolder.ImageHolder;

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {
    private Context c;
    private List<String> name,path,id;
    public ImageAdapter(Context c, List<String> name,List<String> path,List<String> id) {
            this.c = c;
            this.name=name;
            this.path=path;
            this.id=id;
    }
    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(c).inflate(R.layout.image_row,parent,false);
            return new ImageHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
            holder.setName(name.get(position));
            holder.setImg(path.get(position));
            holder.deleteImage(id.get(position),c);
    }
    @Override
    public int getItemCount() {
            return name.size();
    }
}

