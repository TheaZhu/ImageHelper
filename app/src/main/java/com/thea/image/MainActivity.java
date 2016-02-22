package com.thea.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Bitmap mBitmap;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        iv4 = (ImageView) findViewById(R.id.iv_4);
        iv5 = (ImageView) findViewById(R.id.iv_5);
        iv6 = (ImageView) findViewById(R.id.iv_6);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        iv1.setImageBitmap(mBitmap);
        iv2.setImageBitmap(ImageHelper.handleImageNegative(mBitmap));
        iv3.setImageBitmap(ImageHelper.handleImageOldPhoto(mBitmap));
        iv4.setImageBitmap(ImageHelper.handleImageRelief(mBitmap));
        iv5.setImageBitmap(ImageHelper.convertCircleImage(mBitmap));
        iv6.setImageBitmap(ImageHelper.convertCircleImageByShader(mBitmap));
    }
}
