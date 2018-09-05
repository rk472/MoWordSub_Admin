package studio.smartters.mowordsub_admin.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import studio.smartters.mowordsub_admin.R;

public class BoothHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    public BoothHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.booth_name);
    }
    public void setName(String name){
        nameText.setText(name);
    }
}
