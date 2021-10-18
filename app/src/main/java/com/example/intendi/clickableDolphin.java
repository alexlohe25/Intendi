package com.example.intendi;

import android.view.View;
import android.widget.ImageView;

/*
  Class that serves to store the reference to an XML view of a dolphin,
  and their respective attributes.
  It saves a string and and a view.
*/
public class clickableDolphin {
    private String color;
    private View dolphinObject;

    //Necessary constructor, receives a color in the form of a string and a reference to a view
    public clickableDolphin(String _color, View _dolphinObject){
        this.color = _color;
        this.dolphinObject = _dolphinObject;
    }

    //Return the color string
    public String getColor() {
        return color;
    }

    //Returns the view
    public View getDolphinObject() {
        return dolphinObject;
    }

    //Sets the color string
    public void setColor(String color) {
        this.color = color;
    }

    //Sets the view
    public void setDolphinObject(View dolphinObject) {
        this.dolphinObject = dolphinObject;
    }

    //Changes the ball image inside the dolphin view and the string color
    public void setBall(int image, String colorText) {
        ImageView myBall = (ImageView) dolphinObject.findViewById(R.id.Ball);
        myBall.setImageResource(image);
        this.color = colorText;
    }
}
