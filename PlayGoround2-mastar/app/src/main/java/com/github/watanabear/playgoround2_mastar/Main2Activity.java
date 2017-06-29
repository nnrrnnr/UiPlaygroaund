package com.github.watanabear.playgoround2_mastar;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.watanabear.playgoround2_mastar.databinding.ActivityMain2Binding;

public class Main2Activity extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        binding.swipeButton.setOnStateChangeListener(active ->{
            Toast.makeText(this, active ? "close" : "open", Toast.LENGTH_SHORT).show();
        });
    }


}
