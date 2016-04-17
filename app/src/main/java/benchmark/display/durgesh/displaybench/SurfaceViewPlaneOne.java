package benchmark.display.durgesh.displaybench;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Durgesh on 02/02/16.
 */
public class SurfaceViewPlaneOne extends Fragment implements SurfaceHolder.Callback{
    public SurfaceView plane_one;//, plane_two;
    public SurfaceHolder plane_one_holder, plane_two_holder;
    public Bitmap plane_one_bitmap, plane_two_bitmap;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.alpha, container, false);
        plane_one = (SurfaceView) ll.findViewById(R.id.surface_plane_one);
        //plane_two = (SurfaceView) ll.findViewById(R.id.surface_plane_two);
        //FrameLayout fl = (FrameLayout) ll.findViewById(R.id.Alpha_container);
        //FrameLayout.LayoutParams plane_one_params, plane_two_params;
        //plane_one_params = new FrameLayout.LayoutParams(300,300);
        //plane_one_params.leftMargin = 150;
        //plane_one_params.topMargin = 150;
        //fl.addView(plane_one,plane_one_params);
        //plane_two_params = new FrameLayout.LayoutParams(300,300);
        //plane_two_params.leftMargin = 70;
        //plane_two_params.topMargin = 70;
        //fl.addView(plane_two,plane_two_params);
        plane_one_holder =  plane_one.getHolder();
        plane_one_holder.addCallback(this);
        //plane_two_holder = plane_two.getHolder();
        //plane_two_holder.addCallback(this);
        //plane_one_bitmap = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_8888);
        //plane_two_bitmap.eraseColor(Color.RED);
        //plane_two_bitmap = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_8888);
        //plane_two_bitmap.eraseColor(Color.BLUE);
        plane_one.setBackgroundColor(Color.RED);
        //plane_two.setBackgroundColor(Color.GREEN);
        return ll;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
