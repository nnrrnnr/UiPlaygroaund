package com.github.watanabear.playgoround2_mastar.epoxy.model;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.github.watanabear.playgoround2_mastar.R;

import butterknife.BindView;

/**
 * Created by ryo on 2017/07/11.
 */
@EpoxyModelClass(layout = R.layout.model_group)
public abstract class GroupModel extends EpoxyModelWithHolder<GroupModel.GroupHolder> {

    @EpoxyAttribute @ColorInt int color;
    @EpoxyAttribute int text;
    @EpoxyAttribute @DrawableRes int image;

    @Override
    public void bind(GroupHolder holder) {
        holder.cardView.setCardBackgroundColor(color);
        holder.cardView.setBackgroundResource(image);
        holder.textView.setText(text);
    }

    static class GroupHolder extends BaseEpoxyHolder {
        @BindView(R.id.card_view_group)
        CardView cardView;
        @BindView(R.id.text_view_group)
        TextView textView;
    }
}
