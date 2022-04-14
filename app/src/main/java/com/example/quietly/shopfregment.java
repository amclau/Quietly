package com.example.quietly;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class shopfregment extends Fragment {
//////////////////////pdf
PDFView pdfView;
////////////////////////////////loadpdf
ListView lv_pdf;
    public static ArrayList<File> fileList = new ArrayList<File>();
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    File dir;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    final int[] ponumber = {1};
///////////////////////////////////////////
    View view;
//TableLayout tablayout;
//ImageSlider image_slider;
//
//ImageView topbar;
////+17343578288

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_shopfregment, container, false);
/////////////////////////////////////////////////////pdf
    pdfView=view.findViewById(R.id.pdfView);

        ////////////////recycle///////////
    init();
    ///////////////recycle end



//    test on click
//    topbar=view.findViewById(R.id.topbar);
//    topbar.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(getActivity(),testactivity.class));
//
//        }
//    });


    ///////////////////Slider image code withought title
//    image_slider=view.findViewById(R.id.image_slider);
//    final List<SlideModel> remoteimage=new ArrayList<>();
//    FirebaseDatabase.getInstance().getReference().child("slider")
//            .addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot data:snapshot.getChildren())
//                    {
//                        remoteimage.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.FIT));
//                    }
//                    image_slider.setImageList(remoteimage,ScaleTypes.FIT);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
    /////////////////////////Code ends
//////////////////////////////////////////tab layout
//    TabLayout tabLayout=view.findViewById(R.id.tablayout);
//
//
//
//    final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//
//    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//    viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
//    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//    tabLayout.setupWithViewPager(viewPager);
//    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//        @Override
//        public void onTabSelected(TabLayout.Tab tab) {
//            viewPager.setCurrentItem(tab.getPosition());
//        }
//
//        @Override
//        public void onTabUnselected(TabLayout.Tab tab) {
//
//        }
//
//        @Override
//        public void onTabReselected(TabLayout.Tab tab) {
//
//        }
//    });

        return view;
    }
//////////////////////////////////////////////////Listview
    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        final Boolean[] dtap = {false};

        lv_pdf = (ListView) view.findViewById(R.id.lv_pdf);
        dir = new File(Environment.getExternalStorageDirectory().toString());
        fn_permission();

        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ponumber[0] =i;
                Intent intent = new Intent(getActivity(), showpdfToOrder.class);

                intent.putExtra("position", i);
                startActivity(intent);


            }
        });
//GESTURE
//        lv_pdf.setOnTouchListener(new View.OnTouchListener() {
//            GestureDetector gestureDetector=new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
//                @SuppressLint("ClickableViewAccessibility")
//                @Override
//                public void onLongPress(MotionEvent e) {
//
//                                       super.onLongPress(e);
//                }
//
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
////                    dtap[0] =true;
////
////                    Intent intent = new Intent(getActivity(), showpdfToOrder.class);
////                    intent.putExtra("position", ponumber[0]);
////                    startActivity(intent);
//
//
//                    return super.onDoubleTap(e);
//                }
//
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//
//
//                    uploadpdf();
////                    if(dtap[0])
////                    {
////
////                        Toast.makeText(getContext(), "Single tap if vadu"+dtap[0].toString(), Toast.LENGTH_SHORT).show();
////                        dtap[0]=false;
////                        return super.onSingleTapUp(e);
////
////
////                    }
////               else {
////                        Toast.makeText(getContext(), "Single tap !if vadu", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(getActivity(), showpdfToOrder.class);
////                        intent.putExtra("position", ponumber[0]);
////                        startActivity(intent);
//                        return super.onSingleTapUp(e);
////                    }
//                }
//            });
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//gestureDetector.onTouchEvent(motionEvent);
//                return false;
//            }
//        });

    }


    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);

                } else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".pdf")) {

                        for (int j = 0; j < fileList.size(); j++) {
                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {

                            }
                        }

                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            fileList.add(listFile[i]);

                        }
                    }
                }
            }
        }
        return fileList;
    }


////////////////////////////////////////////////upload PDF
//    public void uploadpdf()
//    {
//        Uri file = Uri.fromFile(new File(shopfregment.fileList.get(ponumber[0]).toString()));
//        pdfView.fromFile(shopfregment.fileList.get(ponumber[0]))
//                .onLoad(
//                        new OnLoadCompleteListener() {
//                            @Override
//                            public void loadComplete(int nbPages) {
//                                Log.d("count", String.valueOf(nbPages));
//                                //////////////////////////////////////////////////////////////////////////////////////
//
//
//                                storageReference= FirebaseStorage.getInstance().getReference();
//                                databaseReference= FirebaseDatabase.getInstance().getReference("documents");
//
//                                final ProgressDialog pd=new ProgressDialog(getContext());
//                                pd.setTitle("File Uploading....!!!");
//                                pd.show();
//                                final StorageReference reference=storageReference.child("pdf/"+ file.toString()+".pdf");
//                                reference.putFile(file)
//                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                            @Override
//                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                    @Override
//                                                    public void onSuccess(final Uri uri) {
//
//
//                                                        HashMap<String,Object> map = new HashMap<>();
//                                                        map.put("filename","testing");
//                                                        map.put("totalpage",String.valueOf(nbPages));
//                                                        map.put("fileurl",uri.toString());
//
//
//                                                        FirebaseDatabase.getInstance().getReference()
//                                                                .child("pdf")
//                                                                .push()
//                                                                .setValue(map)
//                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                    @Override
//                                                                    public void onSuccess(Void aVoid) {
//
//                                                                        Toast.makeText(getContext(),"File Uploaded",Toast.LENGTH_LONG).show();
////                                                                                    Intent intent=new Intent(uploadpdf.this,showpdf.class);
////                                                                                    intent.putExtra("uripdf",filepath.toString()); // getText() SHOULD NOT be static!!!
////                                                                                    startActivity(intent);
////                                                                                    Log.d("path",filepath.toString());
////                                                                                    Toast.makeText(getApplicationContext(),filepath.toString(),Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//                                                                })
//                                                                .addOnFailureListener(new OnFailureListener() {
//                                                                    @Override
//                                                                    public void onFailure(@NonNull Exception e) {
////                                                                                    progressBarpdf.setVisibility(View.INVISIBLE);
//                                                                        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
//                                                                    }
//                                                                });
//
//                                                        pd.dismiss();
//
//
//
//                                                    }
//                                                });
//
//                                            }
//                                        })
//                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                            @Override
//                                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                                                float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                                                pd.setMessage("Uploaded :"+(int)percent+"%");
//                                            }
//                                        });
//
//
//
////                                            .////////////////////////////////////////////////////////////////////////////////
//
//                                Toast.makeText(getContext(), String.valueOf(nbPages)+" ok", Toast.LENGTH_SHORT).show();
////                                            count.setText(nbPages+"");
//
//                            }
//                        }
//                )
//                .load();
//
//
//    }
//////////////////////////////////pdf
    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;

            getfile(dir);

            obj_adapter = new PDFAdapter(getActivity(), fileList);
            lv_pdf.setAdapter(obj_adapter);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;
                getfile(dir);

                obj_adapter = new PDFAdapter(getActivity(), fileList);
                lv_pdf.setAdapter(obj_adapter);

            } else {
                Toast.makeText(getActivity(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }
//    public class PagerAdapter extends FragmentStatePagerAdapter {
//        int mNumOfTabs;
//
//        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
//            super(fm);
//            this.mNumOfTabs = NumOfTabs;
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//
//            switch (position) {
//                case 0:
//                    return new canteenFragment();
//                case 1:
//                    return new stationeryFragment();
//
//
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return mNumOfTabs;
//        }
//    }
}


/////////////////////slider image code with title
//
//    Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view=inflater.inflate(R.layout.fragment_shopfregment, container, false);
////    imageframe=view.findViewById(R.id.imageframe);
////
////    imageframe.setOnClickListener(new View.OnClickListener() {
////        @Override
////        public void onClick(View v) {
////            imageframe.setImageResource(R.drawable.frameimg2);
////        }
////    });
//
//        image_slider=view.findViewById(R.id.image_slider);
//final List<SlideModel> remoteimage=new ArrayList<>();
//
//        FirebaseDatabase.getInstance().getReference().child("slider")
//        .addListenerForSingleValueEvent(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot snapshot) {
//        for (DataSnapshot data:snapshot.getChildren())
//        {
//        remoteimage.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(), ScaleTypes.FIT));
//        }
//        image_slider.setImageList(remoteimage,ScaleTypes.FIT);
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//        });
//
//        return view;
//        }