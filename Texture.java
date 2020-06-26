
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

class Texture{

  private int[] id = new int[1];

  Texture(int resourceID, Context context){

    GLES30.glGenTextures(1, id, 0);

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);

    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, id[0]);
    GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
    GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);

    GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

    bitmap.recycle();
  }

  void bind(){

    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, id[0]);
  }
}
