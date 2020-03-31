
package xyz.flighty.scratch;

import android.content.Context;

public class Tile{

  float[] verticesTile = {

    -64,   0, 0, 0, 0,
      0,  32, 0, 0, 1,
     64,   0, 0, 1, 1,
      0, -32, 0, 1, 0
  };
  float[] verticesTileL = {

    -64,   0, 0, 0, 1,
      0,  32, 0, 1, 1,
      0, 672, 0, 1, 0,
    -64, 640, 0, 0, 0
  };
  float[] verticesTileR = {

     0,  32, 0, 0, 1,
    64,   0, 0, 1, 1,
    64, 640, 0, 0, 0,
     0, 672, 0, 1, 0
  };
  float[] verticesBorderL = {

     -1, -32, 0, 1, 0,
    -64,   0, 0, 1, 1,
    -64,  -1, 0, 0, 1,
     -1, -33, 0, 0, 0
  };
  float[] verticesBorderR = {

     1, -32, 0, 1, 0,
    64,   0, 0, 1, 1,
    64,  -1, 0, 0, 1,
     1, -33, 0, 0, 0
  };
  short[] indices = {0, 1, 2, 0, 3, 2};

  Thing center, left, right, borderL, borderR;
  Texture dirt2;

  Tile(int positionLocation, int texturePositionLocation, Context context){

    center = new Thing(verticesTile, indices, false, positionLocation, texturePositionLocation);
    left = new Thing(verticesTileL, indices, false, positionLocation, texturePositionLocation);
    right = new Thing(verticesTileR, indices, false, positionLocation, texturePositionLocation);
    borderL = new Thing(verticesBorderL, indices, false, positionLocation, texturePositionLocation);
    borderR = new Thing(verticesBorderR, indices, false, positionLocation, texturePositionLocation);

    center.setTexture(R.drawable.grass, context);
    left.setTexture(R.drawable.dirt, context);

    dirt2 = new Texture(R.drawable.dirt2, context);
  }

  void instance(float[] positions, float[] borderPositions, int instancePositionLocation){

    center.instance(positions, instancePositionLocation);
    left.instance(positions, instancePositionLocation);
    right.instance(positions, instancePositionLocation);
    borderL.instance(borderPositions, instancePositionLocation);
    borderR.instance(borderPositions, instancePositionLocation);
  }

  void display(){

    center.display();
    left.display();
    dirt2.bind();
    right.display();
    borderL.display();
    borderR.display();
  }
}
