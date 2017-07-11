package com.github.watanabear.playgoround2_mastar.epoxy.model;

import android.support.annotation.CallSuper;
import android.view.View;

import com.airbnb.epoxy.EpoxyHolder;

import butterknife.ButterKnife;

/**
 * Created by ryo on 2017/07/11.
 */

public abstract class BaseEpoxyHolder extends EpoxyHolder {

    @CallSuper
    @Override
    protected void bindView(View itemView) {
        ButterKnife.bind(this, itemView);
    }
}
