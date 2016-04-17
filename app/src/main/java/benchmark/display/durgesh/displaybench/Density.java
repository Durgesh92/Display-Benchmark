package benchmark.display.durgesh.displaybench;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Durgesh on 30/01/16.
 */
public class Density extends Fragment {
    class ldpi extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density 120"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_density().execute();

        }
    }
    ldpi ld;
    class mdpi extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density 160"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_density().execute();

        }
    }
    mdpi md;
    class hdpi extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density 240"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_density().execute();

        }
    }
    hdpi hd;
    class xhdpi extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density 320"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_density().execute();

        }
    }
    xhdpi xhd;
    class xxhdpi extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density 480"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_density().execute();

        }
    }
    xxhdpi xxh;
    class xxxhdpi extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density 640"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            new validate_density().execute();

        }
    }
    xxxhdpi xxx;
    class original extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "wm density reset"
            });
            return null;
        }
    }
    public List<String> ValResult;
    public FileWriter writer = null ;
    public int idd;
    public String filename,output;
    class validate_density extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //"dumpsys display | grep \"mOverrideDisplayInfo\""
            ValResult = Shell.SU.run(new String[]{
                    "wm density | grep \"Override\""
            });
            if(ValResult.isEmpty()){
                System.out.println("Entered in isEmpty");
                ValResult = Shell.SU.run(new String[]{
                        "wm density | grep \"Physical\""
                });
            }
            System.out.println("validate density executed ==> " + ValResult.get(0));
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // output
            output = ValResult.get(0);
            System.out.println("final op string considered ==> " + output);
            String splited_op [] = output.split(" ");
            for (int i= 0 ;i<splited_op.length; i++){
                System.out.println(""+i+"\t"+splited_op[i]);
            }	//index 30-31 for density
            String final_op = splited_op[2];//[30]+splited_op[31];
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
            Log.d("idd_string.len " + idd_string, " length" + idd_string.length());
            Log.d("final_op.len "+final_op, " length"+final_op.length());
            try {
                if(idd_string.equals(final_op)){
                    writer.append(idd_string+" \t\t\t "+final_op.trim()+"\t\t\t"+"PASS"+"\n");
                    //Snackbar.make(getActivity().getCurrentFocus(), "Validation result : PASS", Snackbar.LENGTH_SHORT)
                    //        .setAction("Action", null).show();
                }
                else{
                    writer.append(idd_string+" \t\t\t "+final_op.trim()+"\t\t\t"+"FAIL"+"\n");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.density, container, false);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        final CharSequence[] items = {
                "Reset original", "ldpi 120dpi", "mdpi 160dpi", "hdpi 240dpi", "xhdpi 320dpi", "xxhdpi 480dpi", "xxxhdpi 640dpi"
        };
        Button db = (Button) ll.findViewById(R.id.button_density);
        db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext() ,R.style.MyAlertDialogStyle);
                builder.setTitle("Select Density");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0 :
                                new original().execute();
                                break;
                            case 1 :
                                idd = 1;
                                new ldpi().execute();
                                break;
                            case 2 :
                                idd = 2;
                                new mdpi().execute();
                                break;
                            case 3 :
                                idd = 3;
                                new hdpi().execute();
                                break;
                            case 4 :
                                idd = 4;
                                new xhdpi().execute();
                                break;
                            case 5 :
                                idd = 5;
                                new xxhdpi().execute();
                                break;
                            case 6:
                                idd = 6;
                                new xxxhdpi().execute();
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return ll;
    }
    public String KeyStroke (int id)
    {
        switch(id){
            case 1:
                return "120";
            case 2:
                return "160";
            case 3:
                return "240";
            case 4:
                return "320";
            case 5:
                return "480";
            case 6:
                return "640";
        }
        return null;
    }
}
