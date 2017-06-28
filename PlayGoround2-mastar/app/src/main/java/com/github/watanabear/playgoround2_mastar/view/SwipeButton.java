package com.github.watanabear.playgoround2_mastar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.watanabear.playgoround2_mastar.R;

/**
 * Created by ryo on 2017/06/28.
 */

public class SwipeButton extends RelativeLayout {

    private TextView centerText;
    private ImageView slidingButton;
    private Drawable disableDrawable;
    private Drawable enableDrawable;

    public SwipeButton(Context context) {
        super(context);
        init(context, null, -1, -1);
    }

    public SwipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }

    @TargetApi(21)
    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context,attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // add a background
        RelativeLayout background = new RelativeLayout(context);

        LayoutParams layoutParamsView = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE);

        background.setBackground(ContextCompat.getDrawable(context,
                R.drawable.shape_rounded));

        addView(background, layoutParamsView);

        // add a informative text in the button
        this.centerText = new TextView(context);

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(CENTER_IN_PARENT, TRUE);
        centerText.setText("SWIPE"); //add any text
        centerText.setTextColor(Color.WHITE);
        centerText.setPadding(35,35,35,35);
        background.addView(centerText, layoutParams);

        // add the moving icon
        this.slidingButton = new ImageView(context);
        this.disableDrawable = ContextCompat.getDrawable(getContext(),
                R.mipmap.ic_launcher_round);
        this.enableDrawable = ContextCompat.getDrawable(getContext(),
                R.mipmap.ic_launcher);

        slidingButton.setImageDrawable(disableDrawable);

        LayoutParams layoutParamsButton = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        layoutParamsButton.addRule(CENTER_VERTICAL, TRUE);

        addView(slidingButton, layoutParamsButton);

        setOnTouchListener(getButtonTouchListener());
    }

    private OnTouchListener getButtonTouchListener() {
        return null;
    }
}
