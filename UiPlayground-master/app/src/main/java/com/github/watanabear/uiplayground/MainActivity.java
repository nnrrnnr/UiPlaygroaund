package com.github.watanabear.uiplayground;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.watanabear.uiplayground.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.activityList.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(ActItem.values())));
        binding.activityList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.activityList) {
            ActItem a = (ActItem) parent.getItemAtPosition(position);
            startActivity(new Intent(this, a.activityClass));
        }
    }

    private enum ActItem {
        MAIN_ACTIVITY("MainActivity", MainActivity.class),
        ;
        final String label;
        final Class<? extends Activity> activityClass;

        ActItem(String label, Class<? extends Activity> activityClass) {
            this.label = label;
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
