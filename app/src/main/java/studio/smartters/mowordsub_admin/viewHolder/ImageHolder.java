package studio.smartters.mowordsub_admin.viewHolder;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import studio.smartters.mowordsub_admin.Fragment.ImageFragment;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.others.Constants;

public class ImageHolder extends RecyclerView.ViewHolder {
    private TextView captionText;
    private ImageView imageView;
    private ImageButton deleteBtn;
    private View v;
    public ImageHolder(View itemView) {
        super(itemView);
        captionText=itemView.findViewById(R.id.caption_image);
        imageView=itemView.findViewById(R.id.image_image);
        deleteBtn=itemView.findViewById(R.id.delete_image);
        v=itemView;
    }
    public void setName(String name){
        captionText.setText(name);
    }
    public void setImg(String path){
        RequestQueue r;
        String url= Constants.URL+"images/";
        Cache c=new DiskBasedCache(v.getContext().getCacheDir(),1024*1024);
        Network n=new BasicNetwork(new HurlStack());
        r=new RequestQueue(c,n);
        r.start();
        ImageRequest i=new ImageRequest(url + path, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                int width=imageView.getWidth();
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,width*2/3));
                imageView.setImageBitmap(response);
            }
        },0,0,ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        r.add(i);
    }
    public void deleteImage(final String id, final Context c){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(c)
                        .setTitle("Exit")
                        .setMessage("Do You really want to Delete ?")
                        .setPositiveButton("Yes, Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteTask dt=new DeleteTask();
                                dt.execute(Constants.URL+"removeImage?id="+id);
                            }
                        }).setNegativeButton("No, Don't",null).show();
            }
        });
    }
    private class DeleteTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                URLConnection con=url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                String res="";
                while(data!=-1){
                    res+=(char)data;
                    data=ir.read();
                }
                return res;
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(v.getContext(), s, Toast.LENGTH_SHORT).show();
            if(s.equals("Successfully removed")){
                ImageFragment.getInstance().refresh();
            }
        }
    }
}