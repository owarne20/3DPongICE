package com.android.owarn.a3dpong.util;

/**
 * Created by Oscar Warne on 28/06/2018 for 3DPong.
 */
public class Plane {

    public final Point point;
    public final Vector normal;
    public Plane(Point point, Vector normal){
        this.point = point;
        this.normal = normal;
    }

}
