
import android.content.Context;
import android.opengl.GLES30;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

class Thing{

  private int elementCount, count;
  private int[] vao;
  private Texture texture;
  private boolean textureSet = false;

  Thing(float[] vertices, short[] indices, boolean noTextureCoordinates, int positionAttributeLocation, int texturePositionAttributeLocation){

    elementCount = indices.length;
    count = 1;

    int stride;
    if(!noTextureCoordinates) stride = 5 * 4;
    else stride = 3 * 4;

    ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
    buffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertexBuffer = buffer.asFloatBuffer();
    vertexBuffer.put(vertices);
    vertexBuffer.position(0);

    buffer = ByteBuffer.allocateDirect(indices.length * 2);
    buffer.order(ByteOrder.nativeOrder());
    ShortBuffer indexBuffer = buffer.asShortBuffer();
    indexBuffer.put(indices);
    indexBuffer.position(0);

    vao = new int[1];
    int[] vbo = new int[1];
    int[] ebo = new int[1];

    GLES30.glGenVertexArrays(1, vao, 0);
    GLES30.glGenBuffers(1, vbo, 0);
    GLES30.glGenBuffers(1, ebo, 0);

    GLES30.glBindVertexArray(vao[0]);

    GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0]);
    GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, GLES30.GL_STATIC_DRAW);

    GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, ebo[0]);
    GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * 2, indexBuffer, GLES30.GL_STATIC_DRAW);

    GLES30.glVertexAttribPointer(positionAttributeLocation, 3, GLES30.GL_FLOAT, false, stride, 0);
    GLES30.glEnableVertexAttribArray(positionAttributeLocation);

    if(!noTextureCoordinates){

      GLES30.glVertexAttribPointer(texturePositionAttributeLocation, 2, GLES30.GL_FLOAT, false, stride, 3 * 4);
      GLES30.glEnableVertexAttribArray(texturePositionAttributeLocation);
    }

    GLES30.glBindVertexArray(0);

    GLES30.glDeleteBuffers(1, vbo, 0);
    GLES30.glDeleteBuffers(1, ebo, 0);
  }

  void setTexture(int resourceID, Context context){

    texture = new Texture(resourceID, context);
    textureSet = true;
  }

  void instance(float[] positions, int location){

    count = positions.length / 3;

    if(count != 0){

      ByteBuffer buffer = ByteBuffer.allocateDirect(positions.length * 4);
      buffer.order(ByteOrder.nativeOrder());
      FloatBuffer positionBuffer = buffer.asFloatBuffer();
      positionBuffer.put(positions);
      positionBuffer.position(0);

      int[] vbo2 = new int[1];
      GLES30.glGenBuffers(1, vbo2, 0);

      GLES30.glBindVertexArray(vao[0]);
      GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo2[0]);

      GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, positionBuffer.capacity() * 4, positionBuffer, GLES30.GL_STATIC_DRAW);

      GLES30.glVertexAttribPointer(location, 3, GLES30.GL_FLOAT, false, 3 * 4, 0);
      GLES30.glEnableVertexAttribArray(location);

      GLES30.glVertexAttribDivisor(location, 1);
      GLES30.glBindVertexArray(0);

      GLES30.glDeleteBuffers(1, vbo2, 0);
    }
  }

  void bind(){

    GLES30.glBindVertexArray(vao[0]);
  }

  static void unbind(){

    GLES30.glBindVertexArray(0);
  }

  void display(){

    bind();
    if(textureSet) texture.bind();
    if(count == 1) GLES30.glDrawElements(GLES30.GL_TRIANGLES, elementCount, GLES30.GL_UNSIGNED_SHORT, 0);
    else if(count > 1) GLES30.glDrawElementsInstanced(GLES30.GL_TRIANGLES, elementCount, GLES30.GL_UNSIGNED_SHORT, 0, count);
  }
}
