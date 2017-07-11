package com.github.watanabear.playgoround2_mastar.epoxy.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.watanabear.playgoround2_mastar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryo on 2017/07/11.
 */
public class HeaderView extends LinearLayout {

    @BindView(R.id.title_text)
    TextView title;

    TextView caption;

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_header, this);
        ButterKnife.bind(this);
    }

    public void setTitle(int text) {
        title.setText(text);
    }

    public void setCaption(CharSequence text) {
        caption.setText(text);
    }



}
