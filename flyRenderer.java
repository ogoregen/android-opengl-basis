
import android.content.Context;
import static android.opengl.GLES30*;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class flyRenderer implements GLSurfaceView.Renderer{ //well, the name Renderer was taken

  int width, height;
  Context context;
  Shader shader;
  float[] projection = new float[16];
  float[] view = new float[16];
  float[] model = new float[16];

  Thing parallelogram;
  float[] vertices = {
     //x,   y, z
    -100, -50, 0,
       0,  50, 0,
     100,  50, 0,
       0, -50, 0
  };
  short[] indices = {

    0, 1, 3,
    3, 1, 2
  };

  flyRenderer(int width_, int height_, Context context_){

    width = width_;
    height = height_;
    context = context_;
  }

  public void onSurfaceCreated(GL10 unused, EGLConfig config){

    setup();

    glClearColor(0.0f, 0.0f, 0.0f, 1.0f); //background color
    shader.setUniform("usingTexture", false);
    float[] fillColor = {1.0f, 1.0f, 1.0f, 1.0f};
    shader.setUniformV("color", fillColor);

    parallelogram = new Thing(vertices, indices, true, shader.getAttributeLocation("position"), shader.getAttributeLocation("texturePosition"));
  }

  public void onDrawFrame(GL10 unused){

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    Matrix.setIdentityM(model, 0);
    Matrix.translateM(model, 0, width/2, height/2, 0); //translating...
    shader.setUniformM("model", model); //...and letting the shader know (should be done in onSurfaceCreated if static like this, doing it here regardless for convenience)

    parallelogram.display();
  }

  public void onSurfaceChanged(GL10 unused, int width, int height){

    glViewport(0, 0, width, height);
    Matrix.orthoM(projection, 0, 0, width, height, 0, -10, 10);
    shader.setUniformM("projection", projection);
  }

  public void setup(){

    glEnable( GL_DEPTH_TEST);
    glEnable( GL_BLEND);
    glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

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
