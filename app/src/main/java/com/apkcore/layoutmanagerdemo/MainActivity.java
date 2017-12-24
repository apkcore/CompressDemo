package com.apkcore.layoutmanagerdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.apkcore.compress.CompressUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<Bitmap> mBitmaps = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    for (int i = 0; i < imgs.length; i++) {
                        mBitmaps.add(BitmapFactory.decodeFile(path + i + ".jpg"));
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(adapter.getRawItemCount() * 10000);
                    }
                    break;
            }
        }
    };

    private int[] imgs = {
            R.mipmap.a,
            R.mipmap.b,
            R.mipmap.c,
            R.mipmap.d,
            R.mipmap.e
    };
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/compress/";
    private MyAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        adapter = new MyAdapter(this, mBitmaps);
//        ScrollZoomLayoutManager layoutManager = new ScrollZoomLayoutManager(this,50);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

        final File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            if(file.list().length >= imgs.length) {
                mHandler.sendEmptyMessage(0);
            }else{
                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < imgs.length; i++) {
                            CompressUtil.nativeCompressBitmap(BitmapFactory.decodeResource(getResources(), imgs[i]), 20, path + i + ".jpg", true);
                        }
                        mHandler.sendEmptyMessage(0);
                    }
                }.start();
            }
        }

    }


    /**
     * ALPHA_8
     * 表示8位Alpha位图,即A=8,一个像素点占用1个字节,它没有颜色,只有透明度
     * ARGB_4444
     * 表示16位ARGB位图，即A=4,R=4,G=4,B=4,一个像素点占4+4+4+4=16位，2个字节
     * ARGB_8888
     * 表示32位ARGB位图，即A=8,R=8,G=8,B=8,一个像素点占8+8+8+8=32位，4个字节
     * RGB_565
     * 表示16位RGB位图,即R=5,G=6,B=5,它没有透明度,一个像素点占5+6+5=16位，2个字节
     * <p>
     * *使用rgb_565能使图片减小一半
     */
    public Bitmap compressBitmap(int rId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(getResources(), rId, options);
    }
}
