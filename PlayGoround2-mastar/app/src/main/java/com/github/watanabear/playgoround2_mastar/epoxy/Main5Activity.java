package com.github.watanabear.playgoround2_mastar.epoxy;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.github.watanabear.playgoround2_mastar.R;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class Main5Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        int spanCount = getSpanCount();
        gridAdapter = new GridAdapter();
        gridAdapter.setSpanCount(spanCount);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, spanCount);
        gridLayoutManager.setSpanSizeLookup(gridAdapter.getSpanSizeLookup());
        gridLayoutManager.setRecycleChildrenOnDetach(true);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new VerticalGridCardSpacingDecoration());
        recyclerView.setAdapter(gridAdapter);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(R.layout.model_group, 50);

        gridAdapter.addGroup(new Group());
        gridAdapter.addGroup(new Group());
        gridAdapter.addGroup(new Group());
        gridAdapter.addGroup(new Group());
        gridAdapter.addGroup(new Group());
    }

    private int getSpanCount(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 100);
    }

    public class VerticalGridCardSpacingDecoration extends RecyclerView.ItemDecoration {
        private static final int OUTER_PADDING_DP = 10;
        private static final int INNER_PADDING_DP = 4;
        private int outerPadding = -1;
        private int innerPadding = -1;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (outerPadding == -1 || innerPadding == -1) {
                DisplayMetrics m = view.getResources().getDisplayMetrics();
                outerPadding = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, OUTER_PADDING_DP, m);
                innerPadding = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, INNER_PADDING_DP, m);
            }

            int position = parent.getChildAdapterPosition(view);
            final GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();

            // Zero everything out for the common case
            outRect.setEmpty();

            int spanSize = spanSizeLookup.getSpanSize(position);
            int spanCount = layoutManager.getSpanCount();
            int spanIndex = spanSizeLookup.getSpanIndex(position, spanCount);

            if (spanSize == spanCount) {
                // Only item in row
                outRect.left = outerPadding;
                outRect.right = outerPadding;
            } else if (spanIndex == 0) {
                // First item in row
                outRect.left = outerPadding;
                outRect.right = innerPadding;
            } else if (spanIndex == spanCount - 1) {
                // Last item in row
                outRect.left = innerPadding;
                outRect.right = outerPadding;
            } else {
                // Inner item (not relevant for less than three columns)
                outRect.left = innerPadding;
                outRect.right = innerPadding;
            }
        }
    }
}
