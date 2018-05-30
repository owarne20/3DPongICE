package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.shader.Shader;
import com.android.owarn.a3dpong.shader.ShaderCode;

import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Oscar Warne on 16/05/2018 for 3DPong.
 */
public class GameObject {

     protected Shader shader;
     protected float deltaTime;
     private long last = 0L;

     protected GameObject(Context c, ShaderCode code, float[] positionData, float[] normalData){
         this.shader = new Shader(code, positionData, normalData);
     }
     public void draw(float[] viewProjectionMatrix, float[] viewMatrix){

     }
     public void update(){
         final long now = System.nanoTime();
         deltaTime = ((now - last) / 1000000000F);
         last = now;
     }
     protected float[] positionObject(float x, float y, float z, float[] viewProjectionMatrix, float a, float rx, float ry, float rz){
         float[] modelMatrix = new float[16];
         float[] modelViewProjectionMatrix = new float[16];
         setIdentityM(modelMatrix, 0);
         //rotateM(modelMatrix, 0, a, rx, ry, rz);
         translateM(modelMatrix, 0, x, y, z);
         multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
         return  modelViewProjectionMatrix;
     }
     public void Start(){
         last = System.nanoTime();
     }
}
