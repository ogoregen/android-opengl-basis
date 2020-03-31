
package xyz.flighty.scratch;

import android.opengl.GLES30;

class Shader{

  private int id;

  Shader(){

    String vsCode = "" +
            "attribute vec3 position;" +
            "attribute vec2 texturePosition;" +
            "attribute vec3 instancePosition;" +
            "varying vec2 textureP;" +
            "uniform mat4 projection;" +
            "uniform mat4 view;" +
            "uniform mat4 model;" +
            "void main(){" +
            "  textureP = texturePosition;" +
            "  gl_Position = projection * view * model * vec4(position + instancePosition, 1);" +
            "}";

    String fsCode = "" +
            "precision mediump float;" +
            "uniform vec4 color;" +
            "uniform bool usingTexture;" +
            "varying vec2 textureP;" +
            "uniform sampler2D textureSampler;" +
            "void main(){" +
            "  if(usingTexture) gl_FragColor = texture2D(textureSampler, textureP);" +
            "  else gl_FragColor = color;" +
            "}";

    int vs = compileShader(GLES30.GL_VERTEX_SHADER, vsCode);
    int fs = compileShader(GLES30.GL_FRAGMENT_SHADER, fsCode);
    id = GLES30.glCreateProgram();
    GLES30.glAttachShader(id, vs);
    GLES30.glAttachShader(id, fs);
    GLES30.glLinkProgram(id);
    GLES30.glDeleteProgram(vs);
    GLES30.glDeleteProgram(fs);
  }

  private int compileShader(int type, String shaderCode){

    int shader = GLES30.glCreateShader(type);
    GLES30.glShaderSource(shader, shaderCode);
    GLES30.glCompileShader(shader);
    return shader;
  }

  void bind(){

    GLES30.glUseProgram(id);
  }

  int getAttributeLocation(String name){

    return GLES30.glGetAttribLocation(id, name);
  }

  void setUniformV(String name, float[] value){

    GLES30.glUniform4fv(GLES30.glGetUniformLocation(id, name), 1, value, 0);
  }

  void setUniformM(String name, float[] value){

    GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(id, name), 1, false, value, 0);
  }

  void setUniform(String name, boolean value){

    GLES30.glUniform1i(GLES30.glGetUniformLocation(id, name), value ? 1 : 0);
  }
}
