
import android.content.Context;
import static android.opengl.GLES30.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class Shader{

  private int id;

  Shader(int vsResourceID, int fsResourceID, Context context){

    int vs = compileShader(GL_VERTEX_SHADER, readShader(vsResourceID, context));
    int fs = compileShader(GL_FRAGMENT_SHADER, readShader(fsResourceID, context));
    id = glCreateProgram();
    glAttachShader(id, vs);
    glAttachShader(id, fs);
    glLinkProgram(id);
    glDeleteProgram(vs);
    glDeleteProgram(fs);
  }

  private String readShader(int resourceID, Context context){

    InputStream rawResource = context.getResources().openRawResource(resourceID);
    BufferedReader reader = new BufferedReader(new InputStreamReader(rawResource));
    StringBuilder builder = new StringBuilder();
    String line;

    try{

      while((line = reader.readLine()) != null) builder.append(line + "\n");
    }
    catch(Exception e){}

    try{

      rawResource.close();
    }
    catch(Exception e){}

    return builder.toString();
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
