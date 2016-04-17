package benchmark.display.durgesh.displaybench;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class OpenGLRGBA_8888Surface extends GLSurfaceView{

	private final OpenGLRenderer mRenderer;
	public OpenGLRGBA_8888Surface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new OpenGLRenderer();
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        //getHolder().setFormat(PixelFormat.RGBA_8888);

        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

}
