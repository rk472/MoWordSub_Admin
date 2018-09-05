package studio.smartters.mowordsub_admin.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import studio.smartters.mowordsub_admin.Dialog.EditDialogBooth;
import studio.smartters.mowordsub_admin.R;

public class BoothHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    private ImageView edit;
    public BoothHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.booth_name);
        edit=itemView.findViewById(R.id.edit_booth_btn);
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
}
