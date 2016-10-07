package com.example.archer.picasso_proj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    private final String IMAGE_PATH="http//img4.imgtn.bdimg.com/it/u=1301259139,2025033435&fm=21&gp=0.jpg";
//    private final String IMAGE_PATH1="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSYWtnFjJ-GcfUfuBN2FLpi_azpyM8uoO4rEMKHlTvZEETyaoRv90lzipU";
//    private final String IMAGE_PATH1="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSYWtnFjJ-GcfUfuBN2FLpi_azpyM8uoO4rEMKHlTvZEETyaoRv90lzipU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.button);
        imageView= (ImageView) findViewById(R.id.imageView);

        /**
         * 图片下载的步骤
         * 1.实用异步任务或者 handler+thread 获取图片资源
         * 2.实用bitmapFactory 对图片进行解码
         * 3.显示图片
         *
         * fit()方法应该是有一个BUG，当控件设置为wrap_content 的时候，无法加载图片
         *
         * 当控件设置为有一定的dp的长度时，fit方法可以加载，但是长度不对。
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
                Picasso.with(MainActivity.this).load(IMAGE_PATH).resize(800,800).centerCrop().error(R.mipmap.ic_launcher).into(imageView);
//                Picasso.with(MainActivity.this).load(IMAGE_PATH).fit().error(R.mipmap.ic_launcher).into(imageView);
            }
        });



    }
}
