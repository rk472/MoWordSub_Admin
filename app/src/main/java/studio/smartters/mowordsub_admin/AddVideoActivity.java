package studio.smartters.mowordsub_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;



import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import studio.smartters.mowordsub_admin.Fragment.VideoFragment;
import studio.smartters.mowordsub_admin.others.Constants;
import studio.smartters.mowordsub_admin.others.Upload;

public class AddVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private EditText captionText;
    private String selectedPath,caption;
    private ProgressDialog uploading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        videoView=findViewById(R.id.view_video_check);
        captionText=findViewById(R.id.caption_video);
    }

    public void chooseVideo(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), 1);
    }

    public void uploadVideo(View view) {
        caption=captionText.getText().toString();
        if(TextUtils.isEmpty(selectedPath)){
            Toast.makeText(this, "You must select a video", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(caption) ){
            Toast.makeText(this, "Caption can't be empty", Toast.LENGTH_SHORT).show();
        }else{
            UploadVideo ut=new UploadVideo();
            ut.execute();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                videoView.setVideoURI(selectedImageUri);
                videoView.start();
                videoView.setMediaController(new MediaController(this));
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }
    private class UploadVideo extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uploading = ProgressDialog.show(AddVideoActivity.this, "Uploading File", "Please wait...", false, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.split(":")[0].equals("success")){
                DBTask dt=new DBTask();
                Log.i("res",s.split(":")[1]);
                dt.execute(Constants.URL+"addVideoDB?name="+s.split(":")[1]+"&caption="+caption);
            }else{
                uploading.dismiss();
                Toast.makeText(AddVideoActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            Upload u = new Upload();
            String msg = u.uploadVideo(selectedPath,caption);
            return msg;
        }
    }
    private class DBTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection con= url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                String res="";
                while(data!=-1){
                    res+=(char)data;
                    data=ir.read();
                }
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Unknown error occurred";
        }

        @Override
        protected void onPostExecute(String s) {
            uploading.dismiss();
            super.onPostExecute(s);
            Toast.makeText(AddVideoActivity.this, s, Toast.LENGTH_SHORT).show();
            Log.i("err",s);
            if(s.equals("Successfully uploaded")){
                VideoFragment.getInstance().refresh();
                finish();
            }
        }
    }
}
