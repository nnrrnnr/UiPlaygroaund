package com.github.watanabear.playgoround2_mastar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.watanabear.playgoround2_mastar.databinding.ActivityMainBinding;
import com.github.watanabear.playgoround2_mastar.databinding.ListItemActivityClassBinding;
import com.github.watanabear.playgoround2_mastar.epoxy.Main5Activity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.listActivity.setLayoutManager(new LinearLayoutManager(this));
        binding.listActivity.setAdapter(new ActClassAdapter(
                this,
                Arrays.asList(ActItem.values()),
                item -> startActivity(new Intent(this, item.activityClass))));
    }

    private enum ActItem {
        MAIN_ACTIVITY(MainActivity.class, "MainActivity", R.string.description_0, R.mipmap.mainactivity),
        SWIPE_BUTTON(Main2Activity.class, "Swipe Button", R.string.description_1, R.mipmap.swipebutton),
        TRANSITION(Main3Activity.class, "Transition", R.string.description_2, R.mipmap.transition),
        REVEAL_WITH_CIRCULAR_REVELATION(Main4Activity.class, "reveal an activity with circular revelation", R.string.description_3, R.mipmap.reveal),
        EPOXY(Main5Activity.class, "epoxy",R.string.description_3, R.mipmap.reveal),
        MORPH(Main6Activity.class, "morph", R.string.desc_morph, R.mipmap.reveal)
        ;

        final String label;
        final int description;
        final int imgRes;
        final Class<? extends Activity> activityClass;

        ActItem(Class<? extends Activity> activityClass, String label, int description, int imgRes) {
            this.label = label;
            this.description = description;
            this.activityClass = activityClass;
            this.imgRes = imgRes;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    interface OnRecyclerListener {
        void onItemClick(ActItem item);
    }

    class ActClassAdapter extends RecyclerView.Adapter<ActClassAdapter.ViewHolder> {

        private LayoutInflater inflater;
        private List<ActItem> items;
        private OnRecyclerListener listener;

        ActClassAdapter(Context context, List<ActItem> items, OnRecyclerListener listener) {
            this.inflater = LayoutInflater.from(context);
            this.items = items;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(ListItemActivityClassBinding.inflate(inflater, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ListItemActivityClassBinding b = holder.binding;
            ActItem a = items.get(position);
            b.label.setText(a.label);
            if (a.imgRes != 0) {
                b.screenShot.setGifResource(a.imgRes);
            }
            b.description.setText(a.description);
            b.getRoot().setOnClickListener(v -> {
                if (listener == null) return;

                listener.onItemClick(a);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ListItemActivityClassBinding binding;

            ViewHolder(ListItemActivityClassBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }


}
