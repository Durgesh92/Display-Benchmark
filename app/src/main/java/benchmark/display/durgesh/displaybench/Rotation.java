package benchmark.display.durgesh.displaybench;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Durgesh on 29/01/16.
 */

public class Rotation extends Fragment {

    class portrait extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0"		//"dumpsys display com.example.displaybenchmark | grep \"orientation\" "
            });
            return null;
        }
    }
    class landscape extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1"
            });
            return null;
        }
    }
    class rportrait extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:2"
            });
            return null;
        }
    }
    class rlandscape extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:3"
            });
            return null;
        }
    }
    class acc_dis extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0"
            });
            return null;
        }
    }
    public List<String> ValResult = null;
    public int idd;
    public String filename,mytime;
    public FileWriter writer = null;
    public File root,gpxfile;
    public LinearLayout rl;
    class validate_rotation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ValResult = Shell.SU.run(new String[] {
                    "dumpsys display | grep \"mOverrideDisplayInfo\""
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // output
            String output = ValResult.get(0);
            String splited_op [] = output.split(" ");
            int index = 0;
            String temp;
            for (int i= 0 ;i<splited_op.length; i++){
                System.out.println(""+i+"\t"+splited_op[i]);
                if(splited_op[i].trim().equals("rotation")){
                 index = i;
                    System.out.println("Rotation val result ==> "+splited_op[index]);
                }
            }	//28-29 index for rotation
            String final_op = splited_op[index] + splited_op[index+1];
            File root = new File(Environment.getExternalStorageDirectory(), "DisplayBenchmark");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, filename);
            try {
                writer = new FileWriter(gpxfile,true);
                System.out.println("Writer FOUND");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                System.out.println("Writer NOT FOUND \n "+e1);
            }
            String idd_string = KeyStroke(idd);
            System.out.println("User Entered ==> "+idd_string);
            System.out.println("System Considered ==>" + final_op);
            try {
                if(idd_string.equals(final_op.replaceAll(",",""))){
                    writer.append(idd_string + " \t\t " + final_op.trim().replaceAll(",", "") + "\t\t" + "PASS" + "\n");
                    System.out.println("Validation Result ==> PASS");
                    Snackbar.make(rl, "Validation result : PASS", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                else{
                    writer.append(idd_string + " \t\t " + final_op.trim() + "\t\t" + "FAIL" + "\n");
                    System.out.println("Validation Result ==> FAIL");
                    Snackbar.make(rl, "Validation result : FAIL", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rl = (LinearLayout) inflater.inflate(R.layout.rotation, container, false);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        new acc_dis().execute();
        Button portrait,reverse_portrait,landscape, reverse_landscape;
        portrait = (Button) rl.findViewById(R.id.button1);
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new portrait().execute();
                idd = 0;
                new validate_rotation().execute();
            }
        });
        landscape = (Button) rl.findViewById(R.id.button2);
        landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new landscape().execute();
                idd = 1;
                new validate_rotation().execute();
            }
        });
        reverse_portrait = (Button) rl.findViewById(R.id.button3);
        reverse_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new rportrait().execute();
                idd = 2;
                new validate_rotation().execute();
            }
        });
        reverse_landscape = (Button) rl.findViewById(R.id.button4);
        reverse_landscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new rlandscape().execute();
                idd = 3;
                new validate_rotation().execute();
            }
        });
        //return inflater.inflate(R.layout.rotation, container, false);
        return rl;
    }
    public String KeyStroke (int id)
    {
        switch(id){
            case 0:
                return "rotation0";
            case 1:
                return "rotation1";
            case 2:
                return "rotation2";
            case 3:
                return "rotation3";
        }
        return null;
    }
}