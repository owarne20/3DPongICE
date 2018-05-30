package com.android.owarn.a3dpong.shader;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static com.android.owarn.a3dpong.util.Util.BYTES_PER_FLOAT;

/**
 * Created by Oscar Warne on 16/05/2018 for 3DPong.
 */
public class Shader {

    private int shaderId;

    private FloatBuffer positionBuffer;
    private FloatBuffer normalBuffer;

    public Shader(ShaderCode code, float[] positionData, float[] normalData){
        buildShaderProgram(code);
        setFloatBuffer(positionData, normalData);
    }

    private void buildShaderProgram(ShaderCode code){
        shaderId =
                buildProgram(code.getVertexShader(), code.getFragShader());
    }

    private void setFloatBuffer(float[] positionData, float[] normalData){
        positionBuffer =  ByteBuffer.allocateDirect(positionData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(positionData);
        normalBuffer =  ByteBuffer.allocateDirect(normalData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(normalData);
    }

    private int buildProgram(String vertexShaderSource, String fragmentShaderSource)
    {
        int program;
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);
        program = linkProgram(vertexShader, fragmentShader);
        Log.e("ProgramFinal", String.valueOf(program));
        return program;
    }
    private int compileShader(int type, String shaderCode){
        final int shaderObjectId = glCreateShader(type);
        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);
        Log.e("Shader " + type, String.valueOf(shaderObjectId));
        return shaderObjectId;
    }
    private int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = glCreateProgram();
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        Log.e("Program", String.valueOf(programObjectId));
        return programObjectId;
    }

    public void setAtrribPointer(int offset, int location, int size, int stride, boolean isPosition){
        if(isPosition)
        {
            positionBuffer.position(offset);
            glVertexAttribPointer(location, size, GL_FLOAT, false, stride, positionBuffer);
            glEnableVertexAttribArray(location);
            positionBuffer.position(0);
        }
        else
            {
                normalBuffer.position(offset);
                glVertexAttribPointer(location, size, GL_FLOAT, false, stride, normalBuffer);
                glEnableVertexAttribArray(location);
                normalBuffer.position(0);
            }
    }
    public int getUniformLocation(String name){
        return glGetUniformLocation(shaderId, name);
    }
    public int getAttribLocation(String name){
        return glGetAttribLocation(shaderId, name);
    }
    public void useProgram(){
        glUseProgram(shaderId);
    }

}
