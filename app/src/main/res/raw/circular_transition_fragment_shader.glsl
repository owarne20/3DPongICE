precision highp float;

uniform vec3 u_CirclePos;

varying vec3 v_Position;
uniform vec4 u_Color;
uniform float u_Time;
uniform int u_Invert;
void main()
{
    float distance = length(u_CirclePos - v_Position);
    if(u_Invert == 0)
    {
        if(distance < u_Time)
        {
            discard;
        }
        else{
            gl_FragColor = u_Color;
        }
    }
    else
    {
        if(distance > u_Time)
        {
            discard;
        }
        else
        {
            gl_FragColor = u_Color;
        }
    }


}
