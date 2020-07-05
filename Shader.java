
import static android.opengl.GLES30.*;

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

    int vs = compileShader(GL_VERTEX_SHADER, vsCode);
    int fs = compileShader(GL_FRAGMENT_SHADER, fsCode);
    id =  glCreateProgram();
    glAttachShader(id, vs);
    glAttachShader(id, fs);
    glLinkProgram(id);
    glDeleteProgram(vs);
    glDeleteProgram(fs);
  }

  private int compileShader(int type, String shaderCode){

    int shader = glCreateShader(type);
    glShaderSource(shader, shaderCode);
    glCompileShader(shader);
    return shader;
  }

  void bind(){

    glUseProgram(id);
  }
  
  static void unbind(){
    
    glUseProgram(0);
  }

  int getAttributeLocation(String name){

    return glGetAttribLocation(id, name);
  }

  void setUniformV(String name, float[] value){

    glUniform4fv(glGetUniformLocation(id, name), 1, value, 0);
  }

  void setUniformM(String name, float[] value){

    glUniformMatrix4fv(glGetUniformLocation(id, name), 1, false, value, 0);
  }

  void setUniform(String name, boolean value){

    glUniform1i(glGetUniformLocation(id, name), value ? 1 : 0);
  }
}
