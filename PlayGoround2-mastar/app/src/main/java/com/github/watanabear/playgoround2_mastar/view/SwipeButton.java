package com.github.watanabear.playgoround2_mastar.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
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
    private float initialX;
    private boolean active;

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
                R.drawable.ic_lock_open_black_36dp);
        this.enableDrawable = ContextCompat.getDrawable(getContext(),
                R.drawable.ic_lock_outline_black_36dp);
        slidingButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_button));
        slidingButton.setPadding(30,30,30,30);
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
        return (v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //ゼロでない場合にボタンの最初の位置を確認する
                    if (initialX == 0) {
                        initialX = slidingButton.getX();
                    }

                    //タッチの位置にボタンの中心を設定し、スワイプが増加するにつれてテキストのアルファを減らす。
                    if (event.getX() > initialX + slidingButton.getWidth() / 2 &&
                            event.getX() + slidingButton.getWidth() / 2 < getWidth()) {
                        slidingButton.setX(event.getX() - slidingButton.getWidth() / 2);
                        centerText.setAlpha(1 - 1.3f * (slidingButton.getX() + slidingButton.getWidth()) / getWidth());
                    }

                    //ユーザが限界の外側をスワイプするときに可動部品の位置をROOTの限界に設定する
                    if (event.getX() + slidingButton.getWidth() / 2 > getWidth() &&
                            slidingButton.getX() + slidingButton.getWidth() / 2 < getWidth()) {
                        slidingButton.setX(getWidth() - slidingButton.getWidth());
                    }
                    if (event.getX() < slidingButton.getWidth() / 2 &&
                            slidingButton.getX() > 0) {
                        slidingButton.setX(0);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (active) {
                        collapseButton();
                    } else {
//                        initialButtonWidth = slidingButton.getWidth();
                        if (slidingButton.getX() + slidingButton.getWidth() > getWidth() * 0.85) {
                            expandButton();
                        } else {
                            moveButtonBack();
                        }
                    }
                    return true;
            }
            return false;
        };
    }

    private void collapseButton() {
        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                slidingButton.getWidth(), slidingButton.getHeight());

        widthAnimator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
            params.width = (Integer) widthAnimator.getAnimatedValue();
            slidingButton.setLayoutParams(params);
        });

        widthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                active = false;
                slidingButton.setImageDrawable(disableDrawable);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(centerText, "alpha", 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, widthAnimator);
        animatorSet.start();
    }

    private void expandButton() {
        //コンポーネントの左端をアニメーション
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(slidingButton.getX(), 0);
        positionAnimator.addUpdateListener(animation -> {
            float x = (Float) positionAnimator.getAnimatedValue();
            slidingButton.setX(x);
        });
        //コンポーネントの全空間を拡大して占有
        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                slidingButton.getWidth(),
                getWidth());

        widthAnimator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
            params.width = (Integer) widthAnimator.getAnimatedValue();
            slidingButton.setLayoutParams(params);
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                active = true;
                slidingButton.setImageDrawable(enableDrawable);
            }
        });

        animatorSet.playTogether(positionAnimator, widthAnimator);
        animatorSet.start();
    }

    private void moveButtonBack() {
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(slidingButton.getX(), 0);
        positionAnimator.setInterpolator(new AccelerateInterpolator());
        positionAnimator.addUpdateListener(animation -> {
            float x = (Float) positionAnimator.getAnimatedValue();
            slidingButton.setX(x);
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(centerText, "alpha", 1);

        positionAnimator.setDuration(200);//ms

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, positionAnimator);
        animatorSet.start();
    }
}
