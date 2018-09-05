package studio.smartters.mowordsub_admin.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import studio.smartters.mowordsub_admin.R;

public class WardHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    public WardHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.panchayat_name);
    }
    public void setName(String name){
        nameText.setText(name);
    }
}
