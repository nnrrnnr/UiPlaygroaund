package com.github.watanabear.playgoround2_mastar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.watanabear.playgoround2_mastar.databinding.ActivityMain3Binding;

public class Main3Activity extends AppCompatActivity {

    private ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_first:
                align(true, true);
                break;
            case R.id.button_second:
                align(false, true);
                break;
            case R.id.button_third:
                align(true, false);
                break;
            case R.id.button_fourth:
                align(false, false);
                break;
        }
    }

    private void align(boolean left, boolean top) {

    }

}
