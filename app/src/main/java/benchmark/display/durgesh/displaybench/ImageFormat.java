package benchmark.display.durgesh.displaybench;


import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Durgesh on 30/01/16.
 */
public class ImageFormat extends Fragment {
    /*
    class validate_nv21 extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                Process permission = Runtime.getRuntime().exec("/system/bin/chmod 744 /data/data/benchmark.display.durgesh.displaybench/files/ffmpeg_v7a");
                System.out.println("Library permission changed");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(permission.getInputStream()));
                int read;
                char[] buffer = new char[4096];
                StringBuffer output = new StringBuffer();
                while ((read = reader.read(buffer)) > 0) {
                    output.append(buffer, 0, read);
                }
                reader.close();
                permission.waitFor();
                System.out.println("Permission change op ==> "+output.toString());
            } catch (IOException e) {
                System.out.println("Permission change exception ==> " + e);
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("Permission wait for exception ==> "+e);
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                Process execute_ffmpeg = Runtime.getRuntime().exec("/data/data/benchmark.display.durgesh.displaybench/files/ffmpeg_v7a -i /storage/emulated/legacy/DisplayBenchmark/NV21.jpg");//+" /data/data/benchmark.display.durgesh.displaybench/files/NV21.jpg");//-i /storage/emulated/0/DisplayBenchmark/NV21.jpg
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(execute_ffmpeg.getInputStream()));
                int read;
                char[] buffer = new char[4096];
                StringBuffer output = new StringBuffer();
                while ((read = reader.read(buffer)) > 0) {
                    output.append(buffer, 0, read);
                    System.out.println("buffer ==> "+buffer);
                }
                reader.close();
                execute_ffmpeg.waitFor();
                System.out.println("ffmpeg op ==> "+output.toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(" ffmpeg exception ==> "+e);
            } catch (InterruptedException e) {
                System.out.println(" ffmpeg wait for exception ==> "+e);
                e.printStackTrace();
            }
        }
    } */
    ImageView iv;
    Bitmap b,image;
    int bytes,width,height;
    ByteBuffer bb;
    byte[] array;
    ByteArrayOutputStream out;
    YuvImage yuvImage;
    byte[] imageBytes;
    Button ib;
    public String filename,imagename;
    public FileWriter writer = null;
    public File root,imageroot,gpxfile,imagefile;
    public FileOutputStream fout;
    public AssetManager assetManager;
    public String nativeOutput;
    public int idd;
    public LinearLayout ll;
    public String suAvailable;
    class val extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("doInBackground");
            suResult = Shell.SU.run(new String[] {
                    "chmod /data/data/benchmark.display.durgesh.displaybench/files/ffmpeg_v7a 777",
                    "./data/data/benchmark.display.durgesh.displaybench/files/ffmpeg_v7a 777 -i /storage/emulated/legacy/DisplayBenchmark/"+imagename+" > /storage/emulated/legacy/DisplayBenchmark/ffmpeg_output.txt 2>&1",
                    "cat /storage/emulated/legacy/DisplayBenchmark/ffmpeg_output.txt | grep \"Stream\""
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            if (suResult.isEmpty()){
                System.out.println("Its empty");
            }else{
                String output = suResult.get(0);
                String splited_op [] = output.split(" ");
                /* for (int i= 0 ; i< splited_op.length ; i++){
                    System.out.println(i+" "+splited_op[i]);
                } */
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
                    System.out.println(e1.getMessage());
                }
                String idd_string = getPiixelFormat(idd);
                String final_op = splited_op[8];
                String fo = final_op.substring(0,Math.min(final_op.length(),8));
                try {
                    writer.append("hi");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if(idd_string.equals(fo)){
                        writer.append(idd_string + " \t\t " + fo + "\t\t" + "PASS" + "\n");
                        System.out.println("Validation Result ==> PASS\nUser ==> "+idd_string+"\nSystem ==> "+fo);
                        Snackbar.make(ll, "Validation result : PASS", Snackbar.LENGTH_SHORT)
                               .setAction("Action", null).show();
                    }
                    else{
                        writer.append(idd_string + " \t\t " + fo + "\t\t" + "FAIL" + "\n");
                        System.out.println("Validation Result ==> FAIL\nUser ==> "+idd_string+"\nSystem ==> "+fo);
                        Snackbar.make(ll, "Validation result : FAIL", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    class copy_binaries extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            copyAssetFolder(getActivity().getAssets(), "libs", "/data/data/benchmark.display.durgesh.displaybench/files");
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

        }
        protected boolean copyAssetFolder(AssetManager assetManager,
                                               String fromAssetPath, String toPath) {
            try {
                String[] files = assetManager.list(fromAssetPath);
                new File(toPath).mkdirs();
                boolean res = true;
                for (String file : files) {
                    //if (file.contains(".")){  //to copy file that has any extension
                    res &= copyAsset(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                    System.out.println("copying binary ==> "+file);
                    //}

                /* else{    //to copy folder
                    res &= copyAssetFolder(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                    System.out.println("copying folder ==> "+file);
                } */
                }
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected boolean copyAsset(AssetManager assetManager,
                                         String fromAssetPath, String toPath) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(fromAssetPath);
                new File(toPath).createNewFile();
                out = new FileOutputStream(toPath);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void copyFile(InputStream in, OutputStream out) throws IOException {
            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ll = (LinearLayout) inflater.inflate(R.layout.image_format, container, false);
        ib = (Button) ll.findViewById(R.id.button_image);
        iv = (ImageView) ll.findViewById(R.id.imageView_image_format);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        suAvailable = bundle.getString("su");
        new copy_binaries().execute();
        final CharSequence[] items = {
                "NV21", "YVY2"
        };
        b = BitmapFactory.decodeResource(getResources(),R.drawable.earth);
        iv.setImageBitmap(b);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
                builder.setTitle("Select Image Format");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0:
                                idd = 1;
                                bytes = b.getByteCount();
                                width = b.getWidth();
                                height = b.getHeight();
                                bb = ByteBuffer.allocate(bytes);
                                b.copyPixelsToBuffer(bb);
                                array = bb.array();
                                out = new ByteArrayOutputStream();
                                yuvImage = new YuvImage(array, android.graphics.ImageFormat.NV21, width, height, null);
                                yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, out);
                                imageBytes = out.toByteArray();
                                imagename = "NV21.jpg";
                                imageroot = new File(Environment.getExternalStorageDirectory(), "DisplayBenchmark");
                                if (!imageroot.exists()) {
                                    imageroot.mkdirs();
                                }
                                imagefile = new File(imageroot, imagename);
                                if (imagefile.exists()) imagefile.delete();
                                try {
                                    fout = new FileOutputStream(imagefile);
                                    yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, fout);
                                    fout.flush();
                                    fout.close();
                                    Snackbar.make(ll, "NV21 image stored in : " + imageroot + "" + imagename, Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                iv.setImageBitmap(image);
                                if (suAvailable.matches("true")) {
                                    new val().execute();
                                }
                                break;
                            case 1:
                                idd = 2;
                                bytes = b.getByteCount();
                                width = b.getWidth();
                                height = b.getHeight();
                                bb = ByteBuffer.allocate(bytes);
                                b.copyPixelsToBuffer(bb);
                                array = bb.array();
                                out = new ByteArrayOutputStream();
                                yuvImage = new YuvImage(array, android.graphics.ImageFormat.YUY2, width, height, null);
                                yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, out);
                                imageBytes = out.toByteArray();
                                imagename = "yuy2.jpg";
                                imageroot = new File(Environment.getExternalStorageDirectory(), "DisplayBenchmark");
                                if (!imageroot.exists()) {
                                    imageroot.mkdirs();
                                }
                                imagefile = new File(imageroot, imagename);
                                if (imagefile.exists()) imagefile.delete();
                                try {
                                    fout = new FileOutputStream(imagefile);
                                    yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, fout);
                                    fout.flush();
                                    fout.close();
                                    Snackbar.make(ll, "yuy2 image stored in : " + imageroot + "" + imagename, Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                iv.setImageBitmap(image);
                                if (suAvailable == "true") {
                                    new val().execute();
                                }
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
    public String getPiixelFormat(int k){
        if (k == 1){
            return "yuvj420p";
        }else if(k ==2){
            return "yuvj422p";
        }else {
            return null;
        }
    }
}