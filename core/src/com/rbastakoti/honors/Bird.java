package com.rbastakoti.honors;

import com.badlogic.gdx.graphics.Texture;


public class Bird {
    private Integer flapstate = 0;
    private Float birdStartPosition;
    private Texture bird1;
    private Texture bird2;
    public Texture[] birds;
    private Float birdVelocity=0f;

    public Bird(){
        this.bird1= new Texture("bird.png");
        this.bird2 =new Texture("bird2.png");

    }

    public Texture[] birdsArray(){
        birds = new Texture[2];
        birds[0] = this.bird1;
        birds[1] = this.bird2;
        return birds;
    }

    // change flapstate

    public void changeFlap(){
        if (this.flapstate == 0){
            this.flapstate = 1;
        } else{
            flapstate =0;
        }
    }

    public Integer getFlapState(){
        return this.flapstate;
    }

    public void setBirdVeocity(Float birdvelocity){
        this.birdVelocity = birdvelocity;
    }

    public Float getBirdVelocity(){
        return this.birdVelocity;
    }

    public void setBirdStartPosition(Float birdstartposition){
        this.birdStartPosition = birdstartposition;
    }

    public Float getBirdStartPosition(){
        return this.birdStartPosition;
    }










}

