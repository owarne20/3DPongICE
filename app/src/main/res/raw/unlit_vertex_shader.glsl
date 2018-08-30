attribute vec4 a_Position;

uniform mat4 u_Matrix;

void main() {
    gl_PointSize = 20.0;
    gl_Position = u_Matrix * a_Position;
}
