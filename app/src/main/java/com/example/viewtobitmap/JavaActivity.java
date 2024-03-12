package com.example.viewtobitmap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.viewtobitmap.databinding.ActivityJavaBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class JavaActivity extends AppCompatActivity {

    ActivityJavaBinding binding;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        linearLayout = binding.main;

        binding.btnScreenshot.setOnClickListener(view -> {

            binding.btnScreenshot.setVisibility(View.INVISIBLE);
            saveImage();
        });


    }

    private void saveImage() {

        linearLayout.setDrawingCacheEnabled(true);
        linearLayout.buildDrawingCache();
        linearLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = linearLayout.getDrawingCache();
        saveBitmap(bitmap);

    }

    private void saveBitmap(Bitmap bitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root+"/Download");
        String file_name = "my_image.png";
        File myFile = new File(file, file_name);

        if(myFile.exists()){
            myFile.delete();
        }


        try {
            FileOutputStream outputStream = new FileOutputStream(myFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();

            linearLayout.setDrawingCacheEnabled(false);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error :"+ e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}