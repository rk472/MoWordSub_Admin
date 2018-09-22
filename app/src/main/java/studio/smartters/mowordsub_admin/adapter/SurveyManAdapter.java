package studio.smartters.mowordsub_admin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.others.SurveyMan;
import studio.smartters.mowordsub_admin.viewHolder.SurveyManViewHolder;

public class SurveyManAdapter extends  RecyclerView.Adapter<SurveyManViewHolder>{
    private final AppCompatActivity a;
    private final List<SurveyMan> list;

    public SurveyManAdapter(AppCompatActivity a, List<SurveyMan> list){
        this.a=a;
        this.list=list;
    }
    @NonNull
    @Override
    public SurveyManViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(a).inflate(R.layout.survey_man_row,parent,false);
        return new SurveyManViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyManViewHolder holder, int position) {
        holder.setName(list.get(position).getName());
        holder.setCall(list.get(position).getNumber(),a);
        holder.setArea(list.get(position).getWard()+","+list.get(position).getBooth());
        holder.setNo(list.get(position).getTotal());
        holder.setClick(list.get(position).getId(),a);
        holder.setEdit(list.get(position).getId(),a);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
