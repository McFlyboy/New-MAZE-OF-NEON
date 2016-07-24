#version 400 core

in vec3 vertex;
in vec3 color;

out vec3 passColor;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;

void main(void){
	gl_Position = projection * view * transformation * vec4(vertex, 1.0);
	passColor = color;
}