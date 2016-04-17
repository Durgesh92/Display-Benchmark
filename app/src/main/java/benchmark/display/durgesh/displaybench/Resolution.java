package benchmark.display.durgesh.displaybench;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

import static benchmark.display.durgesh.displaybench.R.style.MyAlertDialogStyle;

/**
 * Created by Durgesh on 30/01/16.
 */
public class Resolution extends Fragment {
    public List<String> ValResult;
    public FileWriter writer = null ;
    public int idd;
    class small extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 426x320"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();
        }
    }
    small sm;
    class normal extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 470x320"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    normal nm;
    class large extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 640x480"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    large la;
    class xlarge extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 960x720"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    xlarge xl;
    class tablet1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 1024x600"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    tablet1 ta1;
    class tablet2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 1920x1080"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    tablet2 ta2;
    class tablet3 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 2560x1440"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    tablet3 ta3;
    class uwqhd extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 3440x1440"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    uwqhd uw;
    class uhd1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 3840x2160"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    uhd1 uh;
    class fourk extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size 4096x2160"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_resolution().execute();

        }
    }
    fourk fo;
    class original extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Shell.SU.run(new String[] {
                    "wm size reset"
            });
            return null;
        }
    }

    class validate_resolution extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // "dumpsys display | grep \"mOverrideDisplayInfo\""
            ValResult = Shell.SU.run(new String[] {
                    "wm size | grep \"Override\""
            });
            if (ValResult.isEmpty()){
                ValResult = Shell.SU.run(new String[] {
                        "wm size | grep \"Physical\""
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // output
            String output = ValResult.get(0);
            String splited_op [] = output.split(" ");
            for (int i= 0 ;i<splited_op.length; i++){
                System.out.println(""+i+"\t"+splited_op[i]);
            }	//index 10-11-12-13 for resolution
            String final_op = splited_op[2];//+splited_op[11]+splited_op[12]+splited_op[13];
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
            String idd_string = KeyStroke(idd);
            Log.d("idd_string.len "+idd_string, " length"+idd_string.length());
            Log.d("final_op.len " + final_op, " length" + final_op.length());
            try {
                if(idd_string.equals(final_op)){
                    writer.append(idd_string+" \t\t "+final_op.trim()+"\t\t"+"PASS"+"\n");
                    //Snackbar.make(getActivity().getCurrentFocus(), "Validation result : PASS", Snackbar.LENGTH_SHORT)
                    //.setAction("Action", null).show();
                }
                else{
                    writer.append(idd_string+" \t\t "+final_op.trim()+"\t\t"+"FAIL"+"\n");
                    //Snackbar.make(getActivity().getCurrentFocus(), "Validation result : FAIL", Snackbar.LENGTH_SHORT)
                    //        .setAction("Action", null).show();
                }
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public String filename;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.resolution, container, false);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        Button rb = (Button) ll.findViewById(R.id.button_resolution);
        final CharSequence[] items = {
                "Reset original", "Small 426x320", "Normal 470x320", "Large 640x480", "Xlarge 960x720", "Tablet1 1024x600", "Tablet2 1920x1080"
                , "Tablet3 2560x1440" , "UWQHD 3440x1440" , "UHD1 3840x2160" , "4K 4096x2160"
        };

        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext() ,R.style.MyAlertDialogStyle);
                //builder.setTitle("Dialog");
                //builder.setMessage("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo,");
                //builder.setPositiveButton("OK", null);
                //builder.setNegativeButton("Cancel", null);
                builder.setTitle("Select Resolution");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0:
                                new original().execute();
                                break;
                            case 1:
                                idd = 1;
                                new small().execute();
                                break;
                            case 2:
                                idd = 2;
                                new normal().execute();
                                break;
                            case 3:
                                idd = 3;
                                new large().execute();
                                break;
                            case 4:
                                idd = 4;
                                new xlarge().execute();
                                break;
                            case 5:
                                idd = 5;
                                new tablet1().execute();
                                break;
                            case 6:
                                idd = 6;
                                new tablet2().execute();
                                break;
                            case 7:
                                idd = 7;
                                new tablet3().execute();
                                break;
                            case 8:
                                idd = 8;
                                new uwqhd().execute();
                                break;
                            case 9:
                                idd = 9;
                                new uhd1().execute();
                                break;
                            case 10:
                                idd = 10;
                                new fourk().execute();
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                //builder.show();
            }
        });
        return ll;
    }
    public String KeyStroke (int id)
    {
        switch(id){
            case 1:
                return "426x320";
            case 2:
                return "470x320";
            case 3:
                return "640x480";
            case 4:
                return "960x720";
            case 5:
                return "1024x600";
            case 6:
                return "1920x1080";
            case 7:
                return "2560x1440";
            case 8:
                return "3440x1440";
            case 9:
                return "3840x2160";
            case 10:
                return "4096x2160";
        }
        return null;
    }
}
