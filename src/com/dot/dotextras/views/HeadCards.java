package com.dot.dotextras.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;

public class HeadCards extends CardView {

    TextView head_title;
    ImageView head_image;
    String title;
    int image;

    public HeadCards(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public HeadCards(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public HeadCards(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }



    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View base = inflate(context, R.layout.head_card, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadCards, defStyleAttr, defStyleRes);
        title = typedArray.getString(R.styleable.HeadCards_title);
        image = typedArray.getResourceId(R.styleable.HeadCards_image, 0);
        head_title = base.findViewById(R.id.head_title);
        head_image = base.findViewById(R.id.head_image);
        if (!title.isEmpty()) {
            head_title.setText(title);
        } else {
            head_title.setText("Title attribute not found");
        }
        head_image.setImageResource(image);
        typedArray.recycle();
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

