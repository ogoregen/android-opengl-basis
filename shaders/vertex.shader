
attribute vec3 position;
attribute vec2 texturePosition;
attribute vec3 instancePosition;

varying vec2 textureP;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

void main(){

	textureP = texturePosition;
	gl_Position = projection * view * model * vec4(position + instancePosition, 1);
}
