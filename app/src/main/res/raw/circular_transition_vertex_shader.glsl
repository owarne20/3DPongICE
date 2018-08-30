attribute vec4 a_Position;

uniform mat4 u_Matrix;
uniform mat4 u_MVMatrix;

varying vec3 v_Position;

void main()
{
    v_Position = vec3(u_MVMatrix * a_Position);
    gl_Position = u_Matrix * a_Position;
}