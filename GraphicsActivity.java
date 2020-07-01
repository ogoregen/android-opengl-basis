
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class GraphicsActivity extends AppCompatActivity{

  private GLSurfaceView gLView;

  @Override
  protected void onCreate(Bundle savedInstanceState){

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_graphics);
    
    gLView = new SurfaceView(this);
    setContentView(gLView);

    findViewById(android.R.id.content).getRootView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    if(Build.VERSION.SDK_INT >= 28){

      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
      layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
      getWindow().setAttributes(layoutParams);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }

  @Override
  protected void onResume(){

    super.onResume();
    findViewById(android.R.id.content).getRootView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }
  
  class SurfaceView extends GLSurfaceView{

    flyRenderer renderer;

    public SurfaceView(Context context){

      super(context);
      setEGLContextClientVersion(2);
      renderer = new flyRenderer(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, context);
      setRenderer(renderer);
    }
  }
}
