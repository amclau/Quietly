package com.example.quietly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitvale.switcher.SwitcherX;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class showpdfToOrder extends AppCompatActivity {
//String position="error";
// int position=-1;

    Handler handler;

    SwitcherX switch_;
    TextView dicount,sitchtext,subtotal,pricetotal;
    LinearLayout pagecount,ogswitch;
 String position__;

    PDFView pdfView;
    EditText count;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpdf_to_order);
        Intent intent = getIntent();
        int position=intent.getIntExtra("position",-1);
        //        position = getIntent().getIntExtra("position",-1);
//        Log.d("ExternalStorage",position);////path
        Uri file = Uri.fromFile(new File(shopfregment.fileList.get(position).toString()));
//position__=shopfregment.fileList.get(position).getName();
//        Toast.makeText(getApplicationContext(),position+" ",Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),file.toString()+" ",Toast.LENGTH_LONG).show();
//        Log.d("ExternalStorage",position__);////path

        pagecount=findViewById(R.id.pagecount);
        sitchtext=findViewById(R.id.sitchtext);
        switch_=findViewById(R.id.switch_);
        subtotal=findViewById(R.id.subtotal);
        pricetotal=findViewById(R.id.pricetotal);
        ogswitch=findViewById(R.id.ogswitch);

        switch_.setOnCheckedChangeListener(new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean) {
                Toast.makeText(showpdfToOrder.this, aBoolean.toString(), Toast.LENGTH_SHORT).show();
                if(aBoolean==true)
                {
                    pagecount.setVisibility(View.VISIBLE);
                    sitchtext.setText("Custom pages");
                }
                if(aBoolean==false)
                {
                    pagecount.setVisibility(View.GONE);
                    sitchtext.setText("All pages");
                }

                return null;
            }
        });

        dicount=findViewById(R.id.dicount);
        dicount.setPaintFlags(dicount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);















    count=findViewById(R.id.count);

        pdfView= (PDFView)findViewById(R.id.pdfView);
        TextView start=findViewById(R.id.start);
        TextView end=findViewById(R.id.end);

        RangeSlider slider = findViewById(R.id.slider);
        slider.setValueTo(10);
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                //It is just an example
                if (value == 3.0f)
                    return "TEST";
                return String.format(Locale.US, "%.0f", value);
            }
        });


        slider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint({"RestrictedApi", "SetTextI18n"})
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
//                Toast.makeText(MainActivity.this, Math.round(value)+" ", Toast.LENGTH_SHORT).show();
//                range.setText((int) value+" ");
                List<Float> list=new ArrayList<Float>();
                list=slider.getValues();

//
                int b = (int)Math.round(list.get(0));
                pdfView.jumpTo(b-1);
//                Toast.makeText(MainActivity.this, b+" ", Toast.LENGTH_SHORT).show();
                start.setText((int)Math.round(list.get(0))+" ");
                end.setText((int)Math.round(list.get(1))+" ");
//            end1.setText(Float.toString(Math.round(list.get(3)))+" ");
            }

        });



        pdfView.fromFile(shopfregment.fileList.get(position))
                .onLoad(
                        new OnLoadCompleteListener() {
                            @Override
                            public void loadComplete(int nbPages) {
                                Log.d("count", String.valueOf(nbPages));
                                count.setText(nbPages+"");
                                if(nbPages==1)
                                {
                                    ogswitch.setVisibility(View.GONE);
                                    subtotal.setText("1");
                                    dicount.setText("2");
                                    pricetotal.setText("1");

                                }
                            }
                        }
                )
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}