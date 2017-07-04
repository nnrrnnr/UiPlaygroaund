package com.github.watanabear.playgoround2_mastar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 *
 */
public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        findViewById(R.id.button_go).setOnClickListener(this::presentActivity);
        findViewById(R.id.button_go_2).setOnClickListener(this::presentActivity);
        findViewById(R.id.button_go_0).setOnClickListener(this::presentActivity);
    }

    /**
     * All we have to do is to create an scene transition animation to use the default fading animation.
     * I am sending the coordinates to the second activity so it knows
     * where is it should start de reveal animation.
     * @param view
     */
    private void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent i = new Intent(this, Sub4Activity.class);
        i.putExtra(Sub4Activity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        i.putExtra(Sub4Activity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, i, options.toBundle());
    }
}
