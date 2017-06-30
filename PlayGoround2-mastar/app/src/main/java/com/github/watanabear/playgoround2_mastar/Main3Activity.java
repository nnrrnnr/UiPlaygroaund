package com.github.watanabear.playgoround2_mastar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.github.watanabear.playgoround2_mastar.databinding.ActivityMain3Binding;

import static android.widget.RelativeLayout.ABOVE;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.BELOW;
import static android.widget.RelativeLayout.LayoutParams;

public class Main3Activity extends AppCompatActivity {

    private ActivityMain3Binding binding;
    private TransitionSet transitionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3);

        transitionSet = new TransitionSet();
        Transition first = new ChangeBounds();
        Transition second = new ChangeBounds();
        Transition third = new ChangeBounds();
        Transition fourth = new ChangeBounds();

        first.addTarget(binding.buttonFirst);
        second.setStartDelay(50).addTarget(binding.buttonSecond);
        third.setStartDelay(100).addTarget(binding.buttonThird);
        fourth.setStartDelay(150).addTarget(binding.buttonFourth);

        transitionSet.addTransition(first)
                .addTransition(second)
                .addTransition(third)
                .addTransition(fourth);
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
        LayoutParams params;
        if (binding.checkboxStagger.isChecked()) {
            TransitionManager.beginDelayedTransition((ViewGroup) binding.getRoot(), transitionSet);
        } else {
            TransitionManager.beginDelayedTransition((ViewGroup) binding.getRoot());
        }


        int oldAlignmentLeftRight = left ? ALIGN_PARENT_RIGHT : ALIGN_PARENT_LEFT;
        int newAlignmentLeftRight = left ? ALIGN_PARENT_LEFT : ALIGN_PARENT_RIGHT;
        int oldAlignmentTopBottom = top ? ABOVE : BELOW;
        int newAlignmentTopBottom = top ? BELOW : ABOVE;

        params = (LayoutParams) binding.buttonFirst.getLayoutParams();
        params.addRule(top ? ALIGN_PARENT_BOTTOM : BELOW, 0);
        params.addRule(oldAlignmentLeftRight, 0);
        params.addRule(top ? BELOW : ALIGN_PARENT_BOTTOM, top ? R.id.checkbox_stagger : 1);
        params.addRule(newAlignmentLeftRight);
        binding.buttonFirst.setLayoutParams(params);

        params = (LayoutParams) binding.buttonSecond.getLayoutParams();
        params.addRule(oldAlignmentLeftRight, 0);
        params.addRule(oldAlignmentTopBottom, 0);
        params.addRule(newAlignmentLeftRight);
        params.addRule(newAlignmentTopBottom, R.id.button_first);
        binding.buttonSecond.setLayoutParams(params);

        params = (LayoutParams) binding.buttonThird.getLayoutParams();
        params.addRule(oldAlignmentLeftRight, 0);
        params.addRule(oldAlignmentTopBottom, 0);
        params.addRule(newAlignmentLeftRight);
        params.addRule(newAlignmentTopBottom, R.id.button_second);
        binding.buttonThird.setLayoutParams(params);

        params = (LayoutParams) binding.buttonFourth.getLayoutParams();
        params.addRule(oldAlignmentLeftRight, 0);
        params.addRule(oldAlignmentTopBottom, 0);
        params.addRule(newAlignmentLeftRight);
        params.addRule(newAlignmentTopBottom, R.id.button_third);
        binding.buttonFourth.setLayoutParams(params);

    }

}
