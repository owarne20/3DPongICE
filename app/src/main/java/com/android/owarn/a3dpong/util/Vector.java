package com.android.owarn.a3dpong.util;

import java.util.Random;

/**
 * Created by Oscar Warne on 18/05/2018 for 3DPong.
 */
public class Vector {

    public float x, y, z;

    public Vector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector scale(float scale){
        return new Vector(x * scale, y * scale, z * scale);
    }
    public float length(){
        return (float) Math.sqrt((x*x)+(y*y)+(z*z));
    }

    public Vector translate(Vector other){
        return new Vector(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z);
    }

    public static Vector random2DNormalisedVector(float speed){
        Random r = new Random();
        final int directionInDegrees = r.nextInt(360);
        final float x = (float) Math.sin(Math.toRadians(directionInDegrees)) * speed;
        final float y = (float) Math.cos(Math.toRadians(directionInDegrees)) * speed;
        return new Vector(x, y, 0);
    }
}
