package com.rbastakoti.honors;

import com.badlogic.gdx.graphics.Texture;

public class Tube {
    public Texture topTube;
    public Texture bottomTube;
    private Float tubeVelocity = 4f;

    public Tube(){
        this.topTube = new Texture("toptube.png");
        this.bottomTube = new Texture("bottomtube.png");
    }

    public Float getTubeVelocity() {
        return tubeVelocity;
    }





}

