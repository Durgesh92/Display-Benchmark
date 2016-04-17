package benchmark.display.durgesh.displaybench;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class OpenGLRGBA_5551Surface extends GLSurfaceView{

	private final OpenGLRenderer mRenderer;
	public OpenGLRGBA_5551Surface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new OpenGLRenderer();
        	 super.setEGLConfigChooser(5, 5, 5, 1, 16, 0);
        //getHolder().setFormat(PixelFormat.RGBA_5551);
        setRenderer(mRenderer);
        
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

}
