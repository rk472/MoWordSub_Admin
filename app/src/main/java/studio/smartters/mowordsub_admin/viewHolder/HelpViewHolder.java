package studio.smartters.mowordsub_admin.viewHolder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import studio.smartters.mowordsub_admin.HelpDetailsActivity;
import studio.smartters.mowordsub_admin.HelpViewActivity;
import studio.smartters.mowordsub_admin.R;

public class HelpViewHolder extends RecyclerView.ViewHolder {
    private View v;
    private TextView nameText, addressText;
    private ImageButton callButton;

    public HelpViewHolder(View itemView) {
        super(itemView);
        v = itemView;
        nameText = v.findViewById(R.id.search_name);
        addressText = v.findViewById(R.id.search_address);
        callButton = v.findViewById(R.id.search_call);
    }

    public void setName(String name) {
        nameText.setText(name);
    }

    public void setAddress(String address) {
        addressText.setText(address);
    }

    public void setCall(final String number) {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel" + number));
                if (ActivityCompat.checkSelfPermission(HelpViewActivity.getInstance(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                HelpViewActivity.getInstance().startActivity(i);
            }
        });
    }
    public void setClick(final String json){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HelpViewActivity.getInstance(), HelpDetailsActivity.class);
                i.putExtra("json_data",json);
                HelpViewActivity.getInstance().startActivity(i);
            }
        });
    }
}
