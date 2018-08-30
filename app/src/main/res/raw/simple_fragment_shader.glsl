precision highp float;

uniform vec3 u_LightPos;

varying vec3 v_Position;
varying vec3 v_Normal;
uniform vec4 u_Color;
void main()
{
    float distance = length(u_LightPos - v_Position);
    vec3 lightVector = normalize(u_LightPos - v_Position);
    float diffuse = max(dot(v_Normal, lightVector), 0.1);
    float strength = 1.4;
    diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance))) * strength;
    gl_FragColor = vec4(vec3(u_Color) * diffuse, u_Color.a);
}
