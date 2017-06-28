package com.github.watanabear.uiplayground.complexanim.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.watanabear.uiplayground.R;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.PageIndicator;

/**
 * Created by ryo on 2017/06/26.
 */

public class IconPageIndicator extends HorizontalScrollView implements PageIndicator {

    private LinearLayout mIconsLayout;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private Runnable mIconSelector;
    private int mSelectedIndex;
    private float percentExpanded;

    public IconPageIndicator(Context context) {
        super(context);
        init();
    }

    public IconPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public IconPageIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
        mIconsLayout = new LinearLayout(getContext());
        mIconsLayout.setClipChildren(false);
        addView(mIconsLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void animationToIcon(final int position) {
        if (mIconSelector != null) {
            removeCallbacks(mIconSelector);
        }
        mIconSelector = new Runnable() {
            @Override
            public void run() {
                updateScroll();
            }
        };
        post(mIconSelector);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mIconSelector != null) {
            // 再度保持しているselectorをpost
            post(mIconSelector);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIconSelector != null) {
            removeCallbacks(mIconSelector);
        }
    }

    private void updateScroll() {
        int x = mIconsLayout.getWidth() / 2;
        int scrollTo = (int) ((x * (1 - percentExpanded))
                + (percentExpanded) * mIconsLayout.getChildAt(mSelectedIndex).getLeft());
        smoothScrollTo(scrollTo, 0);
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) return;
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
        }
        PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance");
        }
        mViewPager = view;
        view.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedIndex = item;
        mViewPager.setCurrentItem(item);

        int tabCount = mIconsLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            View child = mIconsLayout.getChildAt(i);
            boolean isSelect = (i == item);
            child.setSelected(isSelect);
            View foreground = child.findViewById(R.id.foreground);
            if (isSelect) {
                animationToIcon(item);
                foreground.setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.fg_white));
            } else {
                //?
                foreground.setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.fg_gray));
            }
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        mIconsLayout.removeAllViews();
        boolean changePadding = true;
        IconPagerAdapter iconAdapter = (IconPagerAdapter) mViewPager.getAdapter();
        int count = iconAdapter.getCount();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < count; i++) {
            final View parent = inflater.inflate(R.layout.indicator, mIconsLayout, false);
            final ImageView view = (ImageView) parent.findViewById(R.id.icon);
            // LOLIPOP
            view.setTransitionName("tab_" + i);
            view.setImageResource(iconAdapter.getIconResId(i));
            if (changePadding) {
                changePadding = false;
                parent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        parent.getViewTreeObserver().removeOnPreDrawListener(this);
                        int parentWitdh = getWidth();
                        int width = parent.getWidth();
                        int leftRightPadding = (parentWitdh - width) / 2;
                        setPadding(leftRightPadding, getPaddingTop(), leftRightPadding, getPaddingBottom());
                        return true;
                    }
                });
            }
            mIconsLayout.addView(parent);

            parent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mIconsLayout.indexOfChild(parent));
                }
            });
            if (mSelectedIndex > count) {
                mSelectedIndex = count -1;
            }
            setCurrentItem(mSelectedIndex);
            requestLayout();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
// TODO: 2017/06/26
    }

    public void collapse(float top, float total) {
        // scaleを０にしてはダメ
        float newTop = top / 1.2f;
        float scale = (total - newTop) / total;
        ViewCompat.setScaleX(this, scale);
        ViewCompat.setScaleY(this, scale);
        int tabCount = mIconsLayout.getChildCount();

        //　alphaは０にしてよい
        percentExpanded = (total - top) / total;
        float alpha = 1 - percentExpanded;
        for (int i = 0; i < tabCount; i++) {
            View parent = mIconsLayout.getChildAt(i);
            View child = parent.findViewById(R.id.foreground);
            ViewCompat.setAlpha(child, alpha);
        }
        updateScroll();
    }
}
