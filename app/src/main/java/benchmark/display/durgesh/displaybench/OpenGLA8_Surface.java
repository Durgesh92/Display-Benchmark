package benchmark.display.durgesh.displaybench;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Durgesh on 11/03/16.
 */
public class OpenGLA8_Surface extends GLSurfaceView {
    private final OpenGLRenderer mRenderer;
    public OpenGLA8_Surface(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new OpenGLRenderer();
        try {
            super.setEGLConfigChooser(0, 0, 0, 8, 16, 0);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Configuration not supported");
        }
        //getHolder().setFormat(PixelFormat.RGB_332);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
