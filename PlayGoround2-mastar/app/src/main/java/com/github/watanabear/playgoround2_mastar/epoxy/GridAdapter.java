package com.github.watanabear.playgoround2_mastar.epoxy;

import com.airbnb.epoxy.EpoxyAdapter;
import com.github.watanabear.playgoround2_mastar.R;
import com.github.watanabear.playgoround2_mastar.epoxy.model.GroupModel_;
import com.github.watanabear.playgoround2_mastar.epoxy.model.HeaderModel;
import com.github.watanabear.playgoround2_mastar.epoxy.model.HeaderModel_;

/**
 * Created by ryo on 2017/07/11.
 */

public class GridAdapter extends EpoxyAdapter {
    public final HeaderModel headerModel;

    GridAdapter() {
        enableDiffing();
        headerModel = new HeaderModel_().title(R.string.app_name);
        addModels(
                headerModel
        );
    }

    public void addGroup(Group group) {
        insertModelAfter(
                new GroupModel_()
                        .text(group.title)
                        .color(group.color)
                        .image(group.image),
                headerModel);
    }



}
