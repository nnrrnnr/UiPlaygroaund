package com.github.watanabear.playgoround2_mastar.epoxy;

import android.graphics.Color;

import com.github.watanabear.playgoround2_mastar.R;

import java.util.Random;

/**
 * Created by ryo on 2017/07/11.
 */

public class Group {

    public static final Random RANDOM = new Random();
    public int title;
    public int image;
    public int color;

    public Group() {
        title = randomTitle();
        image = randomPicture();
        color = randomColor();
    }


    private int randomPicture() {
        int grid = RANDOM.nextInt(6);

        switch(grid) {
            case 0:
                return R.mipmap.ic_launcher;
            default:
                return R.mipmap.ic_launcher;
        }
    }

    private int randomTitle() {
        int title = RANDOM.nextInt(8);
        switch (title) {
            case 0:
                return R.string.group_1;
            case 1:
                return R.string.group_2;
            case 2:
                return R.string.group_3;
            case 3:
                return R.string.group_4;
            case 4:
                return R.string.group_5;
            case 5:
                return R.string.group_6;
            case 6:
                return R.string.group_7;
            case 7:
                return R.string.group_8;
            default:
                return R.string.app_name;
        }
    }

    private int randomColor() {
        int r = RANDOM.nextInt(256);
        int g = RANDOM.nextInt(256);
        int b = RANDOM.nextInt(256);
        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);
        return Color.HSVToColor(hsv);
    }

}
