package benchmark.display.durgesh.displaybench;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Durgesh on 30/01/16.
 */
public class OpenglPixelFormat extends Fragment {

    public Button ob;
    public String filename;
    public android.support.v4.app.FragmentTransaction transaction;
    public Bundle bundle;
    public FragmentManager fm = getFragmentManager();
    public Fragment fragment1,fragment2,fragment3,fragment4,fragment5,fragment6, fragment7;
    public String suAvailable;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.opengl, container, false);
        ob = (Button) ll.findViewById(R.id.button_opengl);
        Bundle bundle = this.getArguments();
        filename = bundle.getString("filename");
        suAvailable = bundle.getString("su");
        final CharSequence[] items = {
                "RGBA_4444", "RGBA_5551", "RGBA_8888", "RGB_332", "RGB_565", "RGB_888", "A8"
        };
        ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext() ,R.style.MyAlertDialogStyle);
                builder.setTitle("Select Pixel Format");
                fragment1 = getActivity().getSupportFragmentManager().findFragmentByTag("rgb332");
                fragment2 = getActivity().getSupportFragmentManager().findFragmentByTag("rgb565");
                fragment3 = getActivity().getSupportFragmentManager().findFragmentByTag("rgb888");
                fragment4 = getActivity().getSupportFragmentManager().findFragmentByTag("rgba4444");
                fragment5 = getActivity().getSupportFragmentManager().findFragmentByTag("rgba5551");
                fragment6 = getActivity().getSupportFragmentManager().findFragmentByTag("rgba8888");
                fragment7 = getActivity().getSupportFragmentManager().findFragmentByTag("a8");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 3:
                                RGB332Main rgb332_fragment = new RGB332Main();
                                if(fragment2 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
                                    System.out.println("Removed fragment ==> " + fragment2.getTag());
                                } else if(fragment3 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment3).commit();
                                    System.out.println("Removed fragment ==> " + fragment3.getTag());
                                } else if(fragment4 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment4).commit();
                                    System.out.println("Removed fragment ==> " + fragment4.getTag());
                                } else if(fragment5 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment5).commit();
                                    System.out.println("Removed fragment ==> " + fragment5.getTag());
                                } else if(fragment6 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment6).commit();
                                    System.out.println("Removed fragment ==> " + fragment6.getTag());
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                rgb332_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, rgb332_fragment,"rgb332");
                                transaction.commit();
                                break;
                            case 4:
                                if(fragment1 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                } else if(fragment3 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment3).commit();
                                    System.out.println("Removed fragment ==> " + fragment3.getTag());
                                } else if(fragment4 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment4).commit();
                                    System.out.println("Removed fragment ==> " + fragment4.getTag());
                                } else if(fragment5 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment5).commit();
                                    System.out.println("Removed fragment ==> " + fragment5.getTag());
                                } else if(fragment6 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment6).commit();
                                    System.out.println("Removed fragment ==> " + fragment6.getTag());
                                }
                                RGB565Main rgb565Main_fragment = new RGB565Main();
                                bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                rgb565Main_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, rgb565Main_fragment,"rgb565");
                                transaction.commit();
                                break;
                            case 5:
                                if(fragment2 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
                                    System.out.println("Removed fragment ==> " + fragment2.getTag());
                                } else if(fragment1 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                } else if(fragment4 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment4).commit();
                                    System.out.println("Removed fragment ==> " + fragment4.getTag());
                                } else if(fragment5 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment5).commit();
                                    System.out.println("Removed fragment ==> " + fragment5.getTag());
                                } else if(fragment6 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment6).commit();
                                    System.out.println("Removed fragment ==> " + fragment6.getTag());
                                }
                                RGB888Main rgb888Main_fragment = new RGB888Main();
                                bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                rgb888Main_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, rgb888Main_fragment,"rgb888");
                                transaction.commit();
                                break;
                            case 0:
                                if(fragment2 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
                                    System.out.println("Removed fragment ==> " + fragment2.getTag());
                                } else if(fragment3 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment3).commit();
                                    System.out.println("Removed fragment ==> " + fragment3.getTag());
                                } else if(fragment1 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                } else if(fragment5 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment5).commit();
                                    System.out.println("Removed fragment ==> " + fragment5.getTag());
                                } else if(fragment6 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment6).commit();
                                    System.out.println("Removed fragment ==> " + fragment6.getTag());
                                }
                                RGBA4444Main rgba4444Main_fragment = new RGBA4444Main();
                                bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                rgba4444Main_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, rgba4444Main_fragment,"rgba4444");
                                transaction.commit();
                                break;
                            case 1:
                                if(fragment2 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
                                    System.out.println("Removed fragment ==> " + fragment2.getTag());
                                } else if(fragment3 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment3).commit();
                                    System.out.println("Removed fragment ==> " + fragment3.getTag());
                                } else if(fragment4 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment4).commit();
                                    System.out.println("Removed fragment ==> " + fragment4.getTag());
                                } else if(fragment1 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                } else if(fragment6 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment6).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                }
                                RGBA5551Main rgba5551Main_fragment = new RGBA5551Main();
                                bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                rgba5551Main_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, rgba5551Main_fragment,"rgba5551");
                                transaction.commit();
                                break;
                            case 2:
                                if(fragment2 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
                                    System.out.println("Removed fragment ==> " + fragment2.getTag());
                                } else if(fragment3 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment3).commit();
                                    System.out.println("Removed fragment ==> " + fragment3.getTag());
                                } else if(fragment4 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment4).commit();
                                    System.out.println("Removed fragment ==> " + fragment4.getTag());
                                } else if(fragment5 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment5).commit();
                                    System.out.println("Removed fragment ==> " + fragment5.getTag());
                                } else if(fragment1 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                }
                                RGBA8888Main rgba8888Main_fragment = new RGBA8888Main();
                                bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                rgba8888Main_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, rgba8888Main_fragment,"rgba8888");
                                transaction.commit();
                                break;
                            case 6:
                                if(fragment2 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment2).commit();
                                    System.out.println("Removed fragment ==> " + fragment2.getTag());
                                } else if(fragment3 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment3).commit();
                                    System.out.println("Removed fragment ==> " + fragment3.getTag());
                                } else if(fragment4 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment4).commit();
                                    System.out.println("Removed fragment ==> " + fragment4.getTag());
                                } else if(fragment5 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment5).commit();
                                    System.out.println("Removed fragment ==> " + fragment5.getTag());
                                } else if(fragment1 != null){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
                                    System.out.println("Removed fragment ==> " + fragment1.getTag());
                                }
                                A8Main A8Main_fragment = new A8Main();
                                bundle = new Bundle();
                                bundle.putString("filename", filename);
                                bundle.putString("su",suAvailable);
                                A8Main_fragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.Opengl_container, A8Main_fragment,"a8");
                                transaction.commit();
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
}