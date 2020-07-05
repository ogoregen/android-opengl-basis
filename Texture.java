
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static android.opengl.GLES30.*;
import android.opengl.GLUtils;

class Texture{

  private int[] id = new int[1];

  Texture(int resourceID, Context context){

    glGenTextures(1, id, 0);

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);

    glBindTexture(GL_TEXTURE_2D, id[0]);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

    bitmap.recycle();
  }

  void bind(){

    glBindTexture(GL_TEXTURE_2D, id[0]);
  }
  
  static void unbind(){
  
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
