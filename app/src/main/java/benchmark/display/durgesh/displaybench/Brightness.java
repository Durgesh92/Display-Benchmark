package benchmark.display.durgesh.displaybench;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Durgesh on 30/01/16.
 */
public class Brightness extends Fragment {
    public float val_check = 0;
    public String actual_b;
    public float su_val;
    public float b ,k;
    public String filename,mytime;
    public FileWriter writer = null;
    public File root,gpxfile;
    public int pass_count = 0, fail_count =0;
    public String suAvailable ;
    class bright_val extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
		/* protected  void onPreExecute()
		  {
              changeBrightness();
          } */
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //changeBrightness();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("durgesh", "Current in BackThr" + b);
            suResult = Shell.SU.run(new String[]{
                    "cat /sys/class/leds/lcd-backlight/brightness"
            });
            if (suResult.isEmpty()){
                System.out.println("Its Empty");
            }
            else {
                actual_b = suResult.get(0);
                su_val = Float.valueOf(actual_b);
                /*System.out.println("SuR after");
                su_val = Float.valueOf(actual_b);
                if (k > 1) {
                    su_val = Float.valueOf(actual_b);
                    su_val = (float) (su_val + 1.0);
                } else {
                    su_val = Float.valueOf(actual_b);
                }
                Log.d("durgesh", "System Value :" + su_val); */
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            /*
            WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
            layout.screenBrightness = b/255;
            getActivity().getWindow().setAttributes(layout); */
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
            System.out.println("User Entered ==> "+b);
            System.out.println("System Considered ==> "+su_val);
            try {
                if (b == su_val){
                    writer.append(b+" \t\t\t"+su_val+" \t\t\t"+"PASS"+"\n");
                    pass_count++;
                }
                else{
                    writer.append(b+" \t\t\t"+su_val+" \t\t\t"+"FAIL"+"\n");
                    fail_count++;
                }
                if(pass_count+fail_count == 255){
                    writer.append("*****Summary*****\n");
                    writer.append("Total Successful Validations ==> " + pass_count + "\n");
                    writer.append("Total Faied Validations ==> " + fail_count + "\n");
                }
                System.out.println("Pass Count ==>"+pass_count);
                System.out.println("Fail Count ==>"+fail_count);
                writer.close();
                b++;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    class setBrightness extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            changeBrightness();
        }
        protected void changeBrightness(){
            WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
            layout.screenBrightness = b/255;
            getActivity().getWindow().setAttributes(layout);
            System.out.println("Brightness set to : " + b);
        }
        }
    class noRoot extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            String su_val = "N.A.";
            WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
            layout.screenBrightness = b/255;
            getActivity().getWindow().setAttributes(layout);
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
            System.out.println("User Entered ==> "+b);
            System.out.println("System Considered ==> " + su_val);
            try {
                    writer.append(b+" \t\t\t"+su_val+" \t\t\t"+"N.A."+"\n");
                    writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.brightness, container, false);
        Bundle bundle = this.getArguments();
        filename = ""+bundle.getString("filename");
        suAvailable = bundle.getString("su");
        WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
        layout.screenBrightness = (float) (0.0/(float)255);
        getActivity().getWindow().setAttributes(layout);
        final EditText b_value = (EditText) ll.findViewById(R.id.editText_brightness);
        FloatingActionButton fab = (FloatingActionButton) ll.findViewById(R.id.fab_brightness);
        if (suAvailable.matches("false")){
            fab.setVisibility(View.GONE);
        }
        Button brv = (Button) ll.findViewById(R.id.button_brightness);
        brv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = b_value.getText().toString();
                if (check.matches(""))
                {
                    Snackbar sb = Snackbar.make(v,"Enter Brightness Value First",Snackbar.LENGTH_SHORT);
                    sb.show();
                }
                else
                if (Float.parseFloat(check) > 255 || Float.parseFloat(check) <0)
                {
                    Snackbar sb = Snackbar.make(v,"Value must be between 0-255",Snackbar.LENGTH_SHORT);
                    sb.show();
                }
                else
                {
                    WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
                    String value = b_value.getText().toString();
                    Log.d("durgesh","value entered by user "+value);
                    layout.screenBrightness = Float.parseFloat(value)/255;
                    b = Float.parseFloat(value);
                    getActivity().getWindow().setAttributes(layout);
                    if (suAvailable.matches("true")){
                        System.out.println("brightness root validation");
                        new bright_val().execute();
                    }
                    else {
                        new noRoot().execute();
                    }
                }
            }
        });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(suAvailable.matches("true")){
                        Snackbar.make(view, "Starting auto brightness...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        k = 0;
                        b = 1;
                        while (k<=255) {
                            new setBrightness().execute();
                            new bright_val().execute();
                            k++;
                        }
                    }
                    else {
                        Snackbar.make(view, "Feature available on rooted device only.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            });
        return ll;
    }
}
