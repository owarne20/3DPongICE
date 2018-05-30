package com.android.owarn.a3dpong.shader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Oscar Warne on 17/05/2018 for 3DPong.
 */
public class ShaderCode {

    private String fragCode;
    private String vertexCode;

    public ShaderCode(Context c, int fragRes, int vertRes){
        fragCode = readTextFileFromRawResource(c, fragRes);
        vertexCode = readTextFileFromRawResource(c, vertRes);
    }

    public String getFragShader(){
        return fragCode;
    }
    public String getVertexShader(){
        return vertexCode;
    }
    private String readTextFileFromRawResource(final Context context,
                                               final int resourceId)
    {
        final InputStream inputStream = context.getResources().openRawResource(
                resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream);
        final BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

        String nextLine;
        final StringBuilder body = new StringBuilder();

        try
        {
            while ((nextLine = bufferedReader.readLine()) != null)
            {
                body.append(nextLine);
                body.append('\n');
            }
        }
        catch (IOException e)
        {
            return null;
        }

        return body.toString();
    }
}
