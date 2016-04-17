package benchmark.display.durgesh.displaybench;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Durgesh on 02/02/16.
 */
public class AlphaBlending extends Fragment implements SurfaceHolder.Callback{
    class alpha_val extends AsyncTask<Void, Void, Void> {
        //@Override
		/*protected  void onPreExecute()
		  {
            System.out.println("OnPreExec Called\n");
          }*/
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            set_alpha();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected float getAlpha(){
            return plane_two.getAlpha();
        }
        private void set_alpha() {
            plane_two.setAlpha(f_a/255);
        }

        @Override
        protected void onPostExecute(Void result) {

            File root = new File(Environment.getExternalStorageDirectory(), "DisplayBenchmark");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, filename);
            try {
                writer = new FileWriter(gpxfile,true);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Result = getAlpha();
            System.out.println("User Entered ==> "+f_a/255);
            System.out.println("System Considered ==> " +Result);
            int comp = Float.compare(f_a/255,Result);
            try {
                if (comp == 0){
                    writer.append(f_a / 255 + " \t\t\t" + Result + " \t\t\t" + "PASS" + "\n");
                    pass_count++;
                    System.out.println("\t\t\tPASS  pass_count ==>"+pass_count);
                }
                else{
                    writer.append(f_a / 255 + " \t\t\t" + Result + " \t\t\t" + "FAIL" + "\n");
                    fail_count++;
                    System.out.println("\t\t\tFAIL  fail_count ==>"+fail_count);
                }
                if(pass_count + fail_count == 256){
                    writer.append("*****Summary*****\n");
                    writer.append("Total Successful Validations ==> " + pass_count + "\n");
                    writer.append("Total Faied Validations ==> " + fail_count + "\n");
                    System.out.println("Passed ==> " + pass_count);
                    System.out.println("Failed ==> " + fail_count);
                }
                System.out.println("Passed ==> " + pass_count);
                System.out.println("Failed ==> "+fail_count);
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            f_a++;
        }
    }
    public float f_a = 0;
    public float Result;
    public FileWriter writer = null;
    public File root,gpxfile;
    public String filename;
    public SurfaceView plane_one,plane_two;
    public SurfaceHolder plane_one_holder,plane_two_holder;
    public EditText alpha_value;
    public Button set_alpha;
    public int pass_count = 0, fail_count = 0;
    public int k;
    public android.support.v4.app.FragmentTransaction transaction;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.alpha, container, false);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        plane_one = (SurfaceView) ll.findViewById(R.id.surface_plane_one);
        plane_two = (SurfaceView) ll.findViewById(R.id.surface_plane_two);
        alpha_value = (EditText) ll.findViewById(R.id.editText_alpha);
        set_alpha = (Button) ll.findViewById(R.id.button_alpha);
        plane_one_holder =  plane_one.getHolder();
        plane_one_holder.addCallback(this);
        plane_two_holder = plane_two.getHolder();
        plane_two_holder.addCallback(this);
        plane_one.setBackgroundColor(Color.parseColor("#0099ff"));
        plane_two.setBackgroundColor(Color.parseColor("#e5003e"));
        plane_one_holder.setFormat(PixelFormat.RGB_565);
        plane_two_holder.setFormat(PixelFormat.RGBA_8888);
        set_alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = alpha_value.getText().toString();
                float alpha = Float.parseFloat(alpha_value.getText().toString());
                System.out.println("Alpha ==> " + alpha);
                if (temp.matches("")) {
                    Snackbar sb = Snackbar.make(v, "Enter Alpha Value First", Snackbar.LENGTH_SHORT);
                    sb.show();
                } else if (alpha < 0 || alpha > 255) {
                    Snackbar sb = Snackbar.make(v, "Alpha must be between 0-255", Snackbar.LENGTH_SHORT);
                    sb.show();
                } else {
                    //plane_two.setAlpha(alpha / 255);
                    f_a = alpha;
                    new alpha_val().execute();
                    System.out.println("Alpha Value Plane One ==> " + plane_one.getAlpha());
                    System.out.println("Plane one hwa "+plane_one.isHardwareAccelerated()+"\nPlane two "+plane_two.isHardwareAccelerated());
                    System.out.println("Alpha Value Plane Two ==> " + plane_two.getAlpha());
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) ll.findViewById(R.id.fab_alpha);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Setting auto alpha...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //for (k =0;k<=255;k++){
                 k = 0;
                 while (k<=255){
                    new alpha_val().execute();
                    k++;
                 }
                //}
            }
        });
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