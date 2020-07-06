
precision mediump float;

uniform vec4 color;
uniform bool usingTexture;

varying vec2 textureP;

uniform sampler2D textureSampler;

void main(){
  
  if(usingTexture) gl_FragColor = texture2D(textureSampler, textureP);
  else gl_FragColor = color;
}
