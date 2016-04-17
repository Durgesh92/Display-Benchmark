package benchmark.display.durgesh.displaybench;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    class clear_log extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "cd /storage/emulated/legacy/DisplayBenchmark/","rm -rf *.*"
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            Snackbar sb = Snackbar.make(drawer,"Log Cleared",Snackbar.LENGTH_SHORT);
            sb.show();
        }
    }
    class reboot_sys extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "su","reboot"
            });
            return null;
        }
    }
    class hwc extends AsyncTask<Void, Void, Void> {
        private List<String> suResult = null;
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            suResult = Shell.SU.run(new String[] {
                    "stop && start"
            });
            return null;
        }
    }
    public String filename,mytime;
    public FileWriter writer = null;
    public File root,gpxfile;
    public android.support.v4.app.FragmentTransaction transaction;
    public DrawerLayout drawer;
    public String feature;
    public Toolbar toolbar;
    public FileOutputStream fos;
    public boolean suAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(shouldAskPermission())
        {
            String[] perms = {"android.permission.READ_EXTERNAl_STORAGE",
                    "android.permission.WRITE_SETTINGS",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.WAKE_LOCK",
                    "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
                    "android.permission.RECEIVE_BOOT_COMPLETED",
                    "android.permission.READ_PHONE_STATE",
                    "com.android.vending.BILLING",
                    "android.permission.GET_TASKS",
                    "android.permission.SYSTEM_ALERT_WINDOW",
                    "android.permission.WRITE_SECURE_SETTINGS",
                    "android.permission.PACKAGE_USAGE_STATS",
                    "android.permission.DUMP",
                    "android.permission.ACCESS_SPERUSER"};

            int permsRequestCode = 200;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, permsRequestCode);
            }
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        fab.setVisibility(drawer.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        suAvailable = Shell.SU.available();
        if (suAvailable == false){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Warning");
            builder.setMessage("Device is not rooted, few functions are disabled");
                    builder.setPositiveButton("OK", null);
            builder.show();
        }
        else {
            Snackbar.make(drawer,"Root access is available !!!", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.Rotation) {
            if (suAvailable == true){
                toolbar.setTitle("Rotation");
                feature = "rotation_";
                Rotation rotation_fragment = new Rotation();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename", filename);
                rotation_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container, rotation_fragment);
                transaction.commit();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Root access required !!!");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        } else if (id == R.id.Brightness) {
            if(suAvailable == true){
                toolbar.setTitle("Brightness");
                feature = "brightness_";
                Brightness brightness_fragment = new Brightness();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename", filename);
                bundle.putString("su","true");
                brightness_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container,brightness_fragment);
                transaction.commit();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Device is not rooted, manual validation is required");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toolbar.setTitle("Brightness");
                        feature = "brightness_";
                        Brightness brightness_fragment = new Brightness();
                        GenerateLogFile(feature);
                        Bundle bundle = new Bundle();
                        bundle.putString("filename", filename);
                        bundle.putString("su","false");
                        brightness_fragment.setArguments(bundle);
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.Container,brightness_fragment);
                        transaction.commit();
                    }
                });
                builder.show();

            }
        } else if (id == R.id.Resolution) {
            if(suAvailable == true){
                toolbar.setTitle("Resolution");
                feature = "resolution_";
                Resolution resolution_fragment = new Resolution();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename", filename);
                resolution_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container,resolution_fragment);
                transaction.commit();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Root access required !!!");
                builder.setPositiveButton("OK", null);
                builder.show();
            }

        } else if (id == R.id.Density) {
            if(suAvailable == true){
                toolbar.setTitle("Density");
                feature = "density_";
                Density density_fragment = new Density();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename",filename);
                density_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container,density_fragment);
                transaction.commit();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Root access required !!!");
                builder.setPositiveButton("OK", null);
                builder.show();
            }

        } else if (id == R.id.Bitmap_pixel_format) {
                toolbar.setTitle("Bitmap Pixel Format");
                feature = "bpp_";
                BitmapPixelFormat bitmap_fragment = new BitmapPixelFormat();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename",filename);
                bitmap_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container,bitmap_fragment);
                transaction.commit();
        } else if (id == R.id.Image_format) {
            if(suAvailable == true){
                toolbar.setTitle("Image Format");
                feature = "image_format_";
                ImageFormat image_fragment = new ImageFormat();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename",filename);
                bundle.putString("su","true");
                image_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container,image_fragment);
                transaction.commit();
            }
            else{
                final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Device is not rooted, manual validation is required");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toolbar.setTitle("Image Format");
                        feature = "image_format_";
                        ImageFormat image_fragment = new ImageFormat();
                        GenerateLogFile(feature);
                        Bundle bundle = new Bundle();
                        bundle.putString("filename",filename);
                        bundle.putString("su","false");
                        image_fragment.setArguments(bundle);
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.Container,image_fragment);
                        transaction.commit();
                    }
                });
                builder.show();
            }
        } else if (id == R.id.Opengl_pixel_format){
            if(suAvailable){
                toolbar.setTitle("Opengl Pixel Format");
                feature = "opengl_image_format_";
                OpenglPixelFormat opengl_fragment = new OpenglPixelFormat();
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename", filename);
                bundle.putString("su","true");
                opengl_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container, opengl_fragment);
                transaction.commit();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Device is not rooted, manual validation is required");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toolbar.setTitle("Opengl Pixel Format");
                        feature = "opengl_image_format_";
                        OpenglPixelFormat opengl_fragment = new OpenglPixelFormat();
                        GenerateLogFile(feature);
                        Bundle bundle = new Bundle();
                        bundle.putString("filename", filename);
                        bundle.putString("su","false");
                        opengl_fragment.setArguments(bundle);
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.Container, opengl_fragment);
                        transaction.commit();
                    }
                });
                builder.show();
            }
        } else if (id == R.id.Camera_image_format){

            toolbar.setTitle("Camera Image Format");
            feature = "camera_image_format_";

        } else if (id == R.id.Alpha_blending){
                toolbar.setTitle("Alpha Blending");
                AlphaBlending alphaBlending_fragment = new AlphaBlending();
                feature = "alpha_blending_";
                GenerateLogFile(feature);
                Bundle bundle = new Bundle();
                bundle.putString("filename",filename);
                alphaBlending_fragment.setArguments(bundle);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Container,alphaBlending_fragment);
                transaction.commit();
        } else if (id == R.id.Clear_log){
            if (suAvailable == true){
                new clear_log().execute();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Root access required !!!");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        } else if(id == R.id.Restart_hardware_composer){
            if(suAvailable == true){
                new hwc().execute();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Root access required !!!");
                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        }
        else if (id == R.id.Reboot){
            if(suAvailable == true){
                new reboot_sys().execute();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Opps!!!");
                builder.setMessage("Root access required !!!");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void GenerateLogFile(String f){
        mytime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
        filename = f+mytime.replaceAll(":","")+".log";
        System.out.println("External Storage Directory ==> "+Environment.getExternalStorageDirectory().toString());
        root = new File(Environment.getExternalStorageDirectory(), "DisplayBenchmark");
        System.out.println("External Storage State ==> "+android.os.Environment.getExternalStorageState());
        if (!root.exists()) {
            root.mkdirs();
            System.out.println("Directory created");
        }
        gpxfile = new File(root, filename);
        try {
            writer = new FileWriter(gpxfile,true);
            writer.append("UserEntered \t\t SystemConsidered \tValidationResult\n");
            writer.close();
            System.out.println("File wrote and closed");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("Failed to write into file \n"+e1);
        }
    }

    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);

    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:
                boolean ReadExternalStorageAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                boolean WriteSettingAccepted = grantResults[1]== PackageManager.PERMISSION_GRANTED;
                boolean WriteExternalStorageAccepted = grantResults[2]== PackageManager.PERMISSION_GRANTED;
                boolean WakeLockAccepted = grantResults[3]== PackageManager.PERMISSION_GRANTED;
                boolean MountUnmountFileSystemAccepted = grantResults[4]== PackageManager.PERMISSION_GRANTED;
                boolean ReceiveBootAccepted = grantResults[5]== PackageManager.PERMISSION_GRANTED;
                boolean ReadPhoneStateAccepted = grantResults[6]== PackageManager.PERMISSION_GRANTED;
                boolean BillingAccepted = grantResults[7]== PackageManager.PERMISSION_GRANTED;
                boolean GetTaskAccepted = grantResults[8]== PackageManager.PERMISSION_GRANTED;
                boolean SystemAlertWindowAccepted = grantResults[9]== PackageManager.PERMISSION_GRANTED;
                boolean WriteSecureSettingAccepted = grantResults[10]== PackageManager.PERMISSION_GRANTED;
                boolean PackageUsageStatAccepted = grantResults[11]== PackageManager.PERMISSION_GRANTED;
                boolean DumpAccepted = grantResults[12]== PackageManager.PERMISSION_GRANTED;
                boolean SuperUserAccepted = grantResults[13]== PackageManager.PERMISSION_GRANTED;
                System.out.println("ReadExternalStorageAccepted : "+ReadExternalStorageAccepted);
                System.out.println("WriteSettingAccepted : "+WriteSettingAccepted);
                System.out.println("WriteExternalStorageAccepted : "+WriteExternalStorageAccepted);
                System.out.println("WakeLockAccepted : "+WakeLockAccepted);
                System.out.println("MountUnmountFileSystemAccepted : "+MountUnmountFileSystemAccepted);
                System.out.println("ReceiveBootAccepted : "+ReceiveBootAccepted);
                System.out.println("ReadPhoneStateAccepted : "+ReadPhoneStateAccepted);
                System.out.println("BillingAccepted : "+BillingAccepted);
                System.out.println("GetTaskAccepted : "+GetTaskAccepted);
                System.out.println("SystemAlertWindowAccepted : "+SystemAlertWindowAccepted);
                System.out.println("WriteSecureSettingAccepted : "+WriteSecureSettingAccepted);
                System.out.println("PackageUsageStatAccepted : "+PackageUsageStatAccepted);
                System.out.println("DumpAccepted : "+DumpAccepted);
                System.out.println("SuperUserAccepted : "+SuperUserAccepted);
                break;
        }

    }
}
