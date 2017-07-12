package com.github.watanabear.playgoround2_mastar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.watanabear.playgoround2_mastar.animation.MorphAnimation;
import com.github.watanabear.playgoround2_mastar.databinding.ActivityMain6Binding;

public class Main6Activity extends AppCompatActivity {

    private MorphAnimation animLogin;
    private MorphAnimation animRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain6Binding bind = DataBindingUtil.setContentView(this, R.layout.activity_main6);

        animLogin = new MorphAnimation(bind.formBtn, bind.rootView, bind.loginViews);
        animRegister = new MorphAnimation(bind.formRegister, bind.rootView, bind.registerViews);

        bind.buttonInsideGroup.setOnClickListener(v -> {
            if (!animLogin.isPressed()) {
                animLogin.morphIntoForm();
            } else {
                animLogin.morphIntoButton();
            }
        });

        bind.buttonRegister.setOnClickListener(v -> {
            if (!animRegister.isPressed()) {
                animRegister.morphIntoForm();
            } else {
                animRegister.morphIntoButton();
            }
        });
    }
}
