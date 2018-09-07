package studio.smartters.mowordsub_admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;
import studio.smartters.mowordsub_admin.others.Constants;

public class AddImageActivity extends AppCompatActivity {
    private ImageView img;
    private EditText captionText;
    private RequestQueue r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        img=findViewById(R.id.view_image_check);
        captionText=findViewById(R.id.image_caption);
        Cache c=new DiskBasedCache(getCacheDir(),1024*1024);
        Network n=new BasicNetwork(new HurlStack());
        r=new RequestQueue(c,n);
        r.start();
    }

    public void chooseImage(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3,2)
                .start(AddImageActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File thumb_filePath = new File(resultUri.getPath());
                Bitmap bitmap = null;
                try {
                    bitmap = new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(300)
                            .setQuality(10)
                            .compressToBitmap(thumb_filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                img.setImageBitmap(bitmap);

            }
        }
    }


    public void uploadImage(View view) {
        final String caption=captionText.getText().toString().trim();
        if(TextUtils.isEmpty(caption)){
            Toast.makeText(this, "Caption can't be blank", Toast.LENGTH_SHORT).show();
        }else {
            Bitmap b = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            final byte[] mbyte = byteArrayOutputStream.toByteArray();
            final String req= Base64.encodeToString(mbyte,Base64.DEFAULT);
            StringRequest s=new StringRequest(Request.Method.POST, Constants.URL+"uploadPhoto", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AddImageActivity.this, response, Toast.LENGTH_SHORT).show();

                   if(response.equals("Image Successfully Stored")){
                       finish();
                   }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddImageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    //Log.e("e",error.getMessage());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map m=new HashMap();
                    m.put("image",req);
                    m.put("name",Long.toString(new Date().getTime()));
                    m.put("caption",caption);
                    return m;
                }
            };
            r.add(s);
        }

    }

}
