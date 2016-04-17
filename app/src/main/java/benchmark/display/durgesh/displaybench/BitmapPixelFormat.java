package benchmark.display.durgesh.displaybench;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Durgesh on 30/01/16.
 */
public class BitmapPixelFormat extends Fragment {

    public FileWriter writer = null ;
    public int idd;
    public Bitmap b;
    public String filename;
    public String ValResult;
    public ImageView iv;
    public BitmapDrawable bd;

    class validate_bpp extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ValResult = b.getConfig().toString();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            // output
            String output = ValResult;
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
            Log.d("final_op.len " + output, " length" + output.length());
            try {
                if(idd_string.equals(output)){
                    writer.append(idd_string+" \t\t "+output.trim()+"\t\t\t"+"PASS"+"\n");
                    Snackbar.make(getActivity().getCurrentFocus(), "Validation result : PASS", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                else{
                    writer.append(idd_string+" \t\t "+output.trim()+"\t\t\t"+"FAIL"+"\n");
                    Snackbar.make(getActivity().getCurrentFocus(), "Validation result : FAIL", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
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

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.bitmap, container, false);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        Button bb = (Button) ll.findViewById(R.id.button_bitmap);
        iv = (ImageView) ll.findViewById(R.id.imageView_bitmap);
        final CharSequence[] items = {
                "RGB565", "ARGB4444", "ARGB8888", "ALPHA8"
        };
        b = BitmapFactory.decodeResource(getResources(),R.drawable.earth);
        iv.setImageBitmap(b);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext() ,R.style.MyAlertDialogStyle);
                builder.setTitle("Select Pixel Format");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0:
                                iv.setImageDrawable(null);
                                b = convertToMutable (BitmapFactory.decodeResource(getResources(),R.drawable.earth));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    b.setConfig(Bitmap.Config.RGB_565);
                                }
                                bd = new BitmapDrawable(b);
                                bd.setTileModeX(Shader.TileMode.CLAMP);
                                System.out.println("Tile mode " + bd.getTileModeX());
                                iv.setImageDrawable(bd);
                                idd = 1;
                                new validate_bpp().execute();
                                break;
                            case 1:
                                iv.setImageDrawable(null);
                                b = convertToMutable (BitmapFactory.decodeResource(getResources(),R.drawable.earth));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    b.setConfig(Bitmap.Config.ARGB_4444);
                                }
                                bd = new BitmapDrawable(b);
                                bd.setTileModeX(Shader.TileMode.CLAMP);
                                System.out.println("Tile mode " + bd.getTileModeX());
                                iv.setImageDrawable(bd);
                                idd = 2;
                                new validate_bpp().execute();
                                break;
                            case 2:
                                iv.setImageDrawable(null);
                                b = convertToMutable (BitmapFactory.decodeResource(getResources(),R.drawable.earth));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    b.setConfig(Bitmap.Config.ARGB_8888);
                                }
                                bd = new BitmapDrawable(b);
                                bd.setTileModeX(Shader.TileMode.CLAMP);
                                System.out.println("Tile mode " + bd.getTileModeX());
                                iv.setImageDrawable(bd);
                                idd = 3;
                                new validate_bpp().execute();
                                break;
                            case 3:
                                iv.setImageDrawable(null);
                                b = convertToMutable (BitmapFactory.decodeResource(getResources(),R.drawable.earth));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    b.setConfig(Bitmap.Config.ALPHA_8);
                                }
                                bd = new BitmapDrawable(b);
                                bd.setTileModeXY(Shader.TileMode.CLAMP,Shader.TileMode.MIRROR);
                                System.out.println("Tile modeX " + bd.getTileModeX());
                                System.out.println("Tile modeY " + bd.getTileModeY());
                                iv.setImageDrawable(bd);
                                idd = 4;
                                new validate_bpp().execute();
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
    /**
     * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
     * more memory that there is already allocated.
     *
     * @param imgIn - Source image. It will be released, and should not be used more
     * @return a copy of imgIn, but muttable.
     */
    public static Bitmap convertToMutable(Bitmap imgIn) {
        try {
            //this is the file going to use temporally to save the bytes.
            // This file will not be a image, it will store the raw image data.
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

            //Open an RandomAccessFile
            //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
            //into AndroidManifest.xml file
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // get the width and height of the source bitmap.
            int width = imgIn.getWidth();
            int height = imgIn.getHeight();
            Bitmap.Config type = imgIn.getConfig();

            //Copy the byte to the file
            //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
            imgIn.copyPixelsToBuffer(map);
            //recycle the source bitmap, this will be no longer used.
            imgIn.recycle();
            System.gc();// try to force the bytes from the imgIn to be released

            //Create a new bitmap to load the bitmap again. Probably the memory will be available.
            imgIn = Bitmap.createBitmap(width, height, type);
            map.position(0);
            //load it back from temporary
            imgIn.copyPixelsFromBuffer(map);
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();

            // delete the temp file
            file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgIn;
    }
    public String KeyStroke (int id)
    {
        switch(id){
            case 1:
                return "RGB_565";
            case 2:
                return "ARGB_4444";
            case 3:
                return "ARGB_8888";
            case 4:
                return "ALPHA_8";
        }
        return null;
    }
}