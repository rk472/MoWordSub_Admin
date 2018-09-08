package studio.smartters.mowordsub_admin.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import studio.smartters.mowordsub_admin.Dialog.EditDialogBooth;
import studio.smartters.mowordsub_admin.MainActivity;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.SearchByBoothActivity;

public class BoothHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    private ImageView edit;
    private View v;
    public BoothHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.booth_name);
        edit=itemView.findViewById(R.id.edit_booth_btn);
        v=itemView;
    }
    public void setName(String name){
        nameText.setText(name);
    }
    public void edit(final String id, final Context c){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogBooth dialog= new EditDialogBooth(c,id);
                dialog.show();
            }
        });
    }
    public void setClick(final String id, final Context c){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c, SearchByBoothActivity.class);
                i.putExtra("id",id);
                c.startActivity(i);
                
            }
        });

    }
}
