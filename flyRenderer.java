
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.content.Context;
import javax.microedition.khronos.opengles.GL10;

public class flyRenderer implements GLSurfaceView.Renderer{

  Context context;
  int width, height;
  Shader shader;
  float[] projection = new float[16];
  float[] view = new float[16];
  float[] model = new float[16];
    
  flyRenderer(int width_, int height_, Context context_){
    
    width = width_;
    height = height_;
    context = context_;
  }

  public void onSurfaceCreated(GL10 unused, EGLConfig config){
    
    setup();
    
    GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); //background color
    shader.setUniform("usingTexture", false);
    float[] fillColor = {1.0f, 1.0f, 1.0f, 1.0f};
    shader.setUniformV("color", fillColor);
  }

  public void onDrawFrame(GL10 unused){

    GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

    //draw calls here
  }

  public void onSurfaceChanged(GL10 unused, int width, int height){

    GLES30.glViewport(0, 0, width, height);
    Matrix.orthoM(projection, 0, 0, width, height, 0, -10, 10);
    shader.setUniformM("projection", projection);
  }
  
  public void setup(){
    
    GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    GLES30.glEnable(GLES30.GL_BLEND);
    GLES30.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
    
    shader = new Shader();
    shader.bind();
    
    Matrix.setIdentityM(projection, 0);
    Matrix.setIdentityM(view, 0);
    Matrix.setIdentityM(model, 0);
    shader.setUniformM("projection", projection);
    shader.setUniformM("view", view);
    shader.setUniformM("model", model);
  }
}
