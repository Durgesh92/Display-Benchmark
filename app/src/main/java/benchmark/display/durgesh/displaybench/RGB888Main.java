package benchmark.display.durgesh.displaybench;


import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Durgesh on 30/01/16.
 */
public class RGB888Main extends Fragment {

    private GLSurfaceView mGLView;
    public String filename;
    public List<String> ValResult;
    public FileWriter writer = null ;
    public int idd;
    public String suAvailable;
    class validate_opengl_pixel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ValResult = Shell.SU.run(new String[] {
                    "dumpsys SurfaceFlinger | grep \"SurfaceView\""
            });
            System.out.println("ValResult ==> " + ValResult);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // output
            String output = ValResult.get(1);
            Log.d("OPENGL", "" + output);
            String splited_op [] = output.split(" ");
            int index,actual_index=0,j=0;
            for (int i= 0 ;i<splited_op.length; i++){
                //System.out.println(""+i+"\t"+splited_op[i]);
                if(splited_op[i].trim().equals("|")){
                    index =  i;
                    j++;
                    //System.out.println("entered 1st if ==> "+index+" j==> "+j);
                    if(j == 6){
                        actual_index = index;
                        System.out.println("actual_index+1 ==> " + splited_op[actual_index + 1]);
                    }
                }
            }	//index 18 for SurfaceView Pixel Format
            String final_op = splited_op[actual_index+1];
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
            String idd_string = "RGB_888";
            Log.d("idd_string.len "+idd_string, " length"+idd_string.length());
            Log.d("final_op.len "+final_op, " length"+final_op.length());
            try {
                if(idd_string.equals(final_op)){
                    writer.append(idd_string+" \t\t "+final_op.trim()+"\t\t\t"+"PASS"+"\n");
                    //Snackbar.make(mGLView, "Validation result : PASS", Snackbar.LENGTH_SHORT)
                    //        .setAction("Action", null).show();
                }
                else{
                    writer.append(idd_string+" \t\t "+final_op.trim()+"\t\t\t"+"FAIL"+"\n");
                    //Snackbar.make(mGLView, "Validation result : FAIL", Snackbar.LENGTH_SHORT)
                    //        .setAction("Action", null).show();
                }
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        suAvailable = bundle.getString("su");
        mGLView = new OpenGLRGB_888Surface(getContext());
        if(suAvailable.matches("true")){
            new validate_opengl_pixel().execute();
        }
        return mGLView;
    }
}
