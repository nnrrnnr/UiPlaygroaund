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
        MAIN_ACTIVITY("MainActivity", MainActivity.class, R.mipmap.ic_launcher_round),
        MAIN_ACTIVITY2("MainActivity2", MainActivity.class, R.mipmap.ic_launcher),
        ;

        final String label;
        final int imgRes;
        final Class<? extends Activity> activityClass;

        ActItem(String label, Class<? extends Activity> activityClass, int imgRes) {
            this.label = label;
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
            b.screenShot.setImageResource(a.imgRes);

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
