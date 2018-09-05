package studio.smartters.mowordsub_admin.viewHolder;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

}
