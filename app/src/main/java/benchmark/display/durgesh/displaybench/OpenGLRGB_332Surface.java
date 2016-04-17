package benchmark.display.durgesh.displaybench;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class OpenGLRGB_332Surface extends GLSurfaceView{

	private final OpenGLRenderer mRenderer;
	public OpenGLRGB_332Surface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 setEGLContextClientVersion(2);

	        // Set the Renderer for drawing on the GLSurfaceView
	        mRenderer = new OpenGLRenderer();
			super.setEGLConfigChooser(3, 3, 2, 0, 16, 0);
	        //getHolder().setFormat(PixelFormat.RGB_332);
	        setRenderer(mRenderer);
	        
	        // Render the view only when there is a change in the drawing data
	        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

}
