package com.deepak.projectqat.dev;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.deepak.projectqat.R;
import com.deepak.projectqat.app.Utility;


/**
 * Created by Deepak Kumar on 17-03-2018.
 * Developer Dialog
 */

public class DeveloperDialog extends Dialog {

    private Context context;
    private String name, profile, mail, picture;

    public DeveloperDialog(Context context, String name, String profile, String mail, String picture) {
        super(context);
        this.context = context;
        this.name = name;
        this.profile = profile;
        this.mail = mail;
        this.picture = picture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dev_pop_up_window);

        TextView name_tv = (TextView)findViewById(R.id.name_dev_tv);
        TextView profile_tv = (TextView)findViewById(R.id.profile_tv);
        TextView mail_tv = (TextView)findViewById(R.id.mail_tv);
        CircularImageView circularImageView = (CircularImageView)findViewById(R.id.profile_pic);
        // Set Border
        circularImageView.setBorderColor(context.getResources().getColor(R.color.GrayLight));
        circularImageView.setBorderWidth(10);
        // Add Shadow with default param
        circularImageView.addShadow();

        //Set Values
        Glide.with(context).load(picture)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circularImageView);

        Utility.setConcertTypeFace(context,name_tv);
        Utility.setConcertTypeFace(context,mail_tv);
        Utility.setConcertTypeFace(context,profile_tv);
        name_tv.setText(name);
        profile_tv.setText(profile);
        mail_tv.setText(mail);


    }

}
