package studio.smartters.mowordsub_admin.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import studio.smartters.mowordsub_admin.Dialog.CreateDialogPanchayat;
import studio.smartters.mowordsub_admin.Dialog.EditDialogBooth;
import studio.smartters.mowordsub_admin.Dialog.EditDialogWard;
import studio.smartters.mowordsub_admin.R;

public class WardHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    private ImageView edit;
    public WardHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.panchayat_name);
        edit=itemView.findViewById(R.id.edit_panchayat_btn);
    }
    public void setName(String name){
        nameText.setText(name);
    }
    public void edit(final String id, final Context c){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogWard dialog= new EditDialogWard(c,id);
                dialog.show();
            }
        });
    }
}
