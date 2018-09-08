package studio.smartters.mowordsub_admin.viewHolder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import studio.smartters.mowordsub_admin.R;

public class LabharthiViewHolder extends RecyclerView.ViewHolder {
    private TextView nameText, addressText, schemeText;
    private ImageButton callButton;

    public LabharthiViewHolder(View itemView) {
        super(itemView);
        nameText = itemView.findViewById(R.id.labharthi_name);
        addressText = itemView.findViewById(R.id.labharthi_address);
        schemeText = itemView.findViewById(R.id.labharthi_scheme);
        callButton = itemView.findViewById(R.id.labharthi_call);
    }

    public void setData(final JSONObject data, final Context c) {
        try {
            nameText.setText(data.getString("head_name"));
            addressText.setText(data.getString("address"));
            schemeText.setText(data.getString("scheme_name"));
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    try {
                        i.setData(Uri.parse("tel:" + data.getString("phone")));
                        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        c.startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
