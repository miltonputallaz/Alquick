package com.putallazmilton.alquick.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.putallazmilton.alquick.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;
import static com.google.android.gms.tasks.Tasks.whenAll;

/**
 * Created by Milton on 11/02/2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private ArrayList<String> image_resources;
    private ImageView imageView;




    public CustomSwipeAdapter(Context ctx,ArrayList<String> urls){
        this.ctx=ctx;

        this.image_resources=urls;








    }

    @Override
    public int getCount() {
        return image_resources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view= layoutInflater.inflate(R.layout.swipe_layout,container,false);

        imageView= (ImageView) item_view.findViewById(R.id.imageswipe);


        Glide.with(ctx.getApplicationContext()).load(image_resources.get(position)).into(imageView);

        container.addView(item_view);


        return item_view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }


}
