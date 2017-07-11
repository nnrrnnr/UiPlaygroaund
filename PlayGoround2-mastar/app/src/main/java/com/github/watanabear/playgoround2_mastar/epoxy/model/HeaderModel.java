package com.github.watanabear.playgoround2_mastar.epoxy.model;

import android.support.annotation.StringRes;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.github.watanabear.playgoround2_mastar.R;
import com.github.watanabear.playgoround2_mastar.epoxy.views.HeaderView;

/**
 * Created by ryo on 2017/07/11.
 */
@EpoxyModelClass(layout = R.layout.view_header_holder)
public abstract class HeaderModel extends EpoxyModel<HeaderView> {

    @EpoxyAttribute @StringRes int title;

    @Override
    public void bind(HeaderView view) {
        view.setTitle(title);
    }

    @Override
    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        return totalSpanCount;
    }
}
