package com.android.owarn.a3dpong.util;

/**
 * Created by Oscar Warne on 28/06/2018 for 3DPong.
 */
public class Point {

    public float x, y, z;
    public Point(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point translateY(float distance){
        return new Point(x, y + distance, z);
    }
    public Point translate(Vector vector)
    {
        return new Point(
                x + vector.x,
                y + vector.y,
                z + vector.z
        );
    }

}
