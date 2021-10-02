package com.example.intendi;

import android.view.View;
import android.widget.ImageView;


public class clickableDolphin {
    private String color;
    private View dolphinObject;

    public clickableDolphin(String _color, View _dolphinObject){
        this.color = _color;
        this.dolphinObject = _dolphinObject;
    }

    public String getColor() {
        return color;
    }

    public View getDolphinObject() {
        return dolphinObject;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDolphinObject(View dolphinObject) {
        this.dolphinObject = dolphinObject;
    }
    public void setBall(int image, String colorText) {
        ImageView myBall = (ImageView) dolphinObject.findViewById(R.id.Ball);
        myBall.setImageResource(image);
        this.color = colorText;
    }
}
