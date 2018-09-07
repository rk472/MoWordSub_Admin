package studio.smartters.mowordsub_admin.viewHolder;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import studio.smartters.mowordsub_admin.Fragment.VideoFragment;
import studio.smartters.mowordsub_admin.R;
import studio.smartters.mowordsub_admin.others.Constants;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    private ImageView img;
    private TextView nameText;
    private View v;
    private ImageButton deleteBtn;
    public VideoViewHolder(View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.video_click);
        nameText=itemView.findViewById(R.id.video_title);
        deleteBtn=itemView.findViewById(R.id.delete_video);
        v=itemView;
    }
    public void setName(String name){
        nameText.setText(name);
    }
    public void setClick(final String url){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiraffePlayer.play(v.getContext(), new VideoInfo(Uri.parse(Constants.URL+"videos/"+url)));
            }
        });
    }
    public void deleteVideo(final String id, final Context c){
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
                                dt.execute(Constants.URL+"removeVideo?id="+id);
                            }
                        }).setNegativeButton("No, Don't",null).show();
            }
        });
    }
    private class DeleteTask extends AsyncTask<String,Void,String> {

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
                VideoFragment.getInstance().refresh();
            }
        }
    }
}
