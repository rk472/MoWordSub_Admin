package studio.smartters.mowordsub_admin.viewHolder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import studio.smartters.mowordsub_admin.Dialog.AddAdharDialog;
import studio.smartters.mowordsub_admin.Dialog.AddVoterDialog;
import studio.smartters.mowordsub_admin.Dialog.CreateDialogBooth;
import studio.smartters.mowordsub_admin.PersonDataActivity;
import studio.smartters.mowordsub_admin.R;


public class DataViewHolder extends RecyclerView.ViewHolder {
    private TextView nameText, hofText;
    private ImageButton callButton,editButton ;
    private View v;

    public DataViewHolder(View itemView) {
        super(itemView);
        v = itemView;
        nameText = v.findViewById(R.id.member_name);
        hofText = v.findViewById(R.id.member_hof);
        callButton = v.findViewById(R.id.member_call);
        editButton = v.findViewById(R.id.member_edit);
    }

    public void setName(String name) {
        nameText.setText(name);
    }

    public void setHof(String name) {
        hofText.setText(name);
    }

    public void setEditAdhar(final AppCompatActivity main,final String id){
        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddAdharDialog dialog= new AddAdharDialog(main,id);
                dialog.show();
            }
        });
    }
    public void setEditVoter(final AppCompatActivity main, final String id){
        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddVoterDialog dialog= new AddVoterDialog(main,id);
                dialog.show();
            }
        });
    }

    public void setCall(final String number, final AppCompatActivity a) {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(a, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                a.startActivity(i);
            }
        });
    }
    public void setClick(final String json, final AppCompatActivity a){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(a,PersonDataActivity.class);
                i.putExtra("json_data",json);
                a.startActivity(i);
            }
        });

    }
}
