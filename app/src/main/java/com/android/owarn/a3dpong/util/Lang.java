package com.android.owarn.a3dpong.util;

/**
 * Created by Oscar Warne on 16/05/2018 for 3DPong.
 */
public class Lang {

    public static final int BYTES_PER_FLOAT = 4;
    public static final String A_NORMAL = "a_Normal";
    public static final String A_POSITION = "a_Position";
    public static final String U_MATRIX = "u_Matrix";
    public static final String U_MVMATRIX = "u_MVMatrix";
    public static final String U_COLOR = "u_Color";
    public static final String U_LIGHTPOS = "u_LightPos";

    public enum GameState
    {
        title,
        inGame,
        credits
    }
    public enum Side
    {
        top,
        bottom,
        left,
        right,
        none
    }

    public static final Color one = new Color(25, 0, 97);
    public static final Color two = new Color(36, 0, 144);
    public static final Color three = new Color(131, 0, 139);
    public static final Color four = new Color(40, 40, 40);
    public static final Color five = new Color(12, 0, 50);
    public static final Color six = new Color(70, 73, 76);

    private static boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    private static int orientation(Point p, Point q, Point r)
    {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        float val = ((q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y));
        //System.out.println(val);
        if (val == 0) return 0;  // colinear

        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        //System.out.println(o1 + " ... " + o2 + " ... " + o3 + " ... " + o4);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    public static class Color
    {
        public final float nR, nG, nB;

        public Color(float r, float g, float b)
        {
            this.nR = r / 255.0f;
            this.nG = g / 255.0f;
            this.nB = b / 255.0f;
        }
    }

}