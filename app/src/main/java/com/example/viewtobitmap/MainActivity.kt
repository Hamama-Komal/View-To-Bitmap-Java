package com.example.viewtobitmap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.viewtobitmap.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageUri = createImageUri()

        binding.btnScreenshot.setOnClickListener {

            it.isVisible = false
            val view = findViewById<LinearLayout>(R.id.main)
            val bitmap = getBitmapFromView(binding.main)

            storeBitmap(bitmap)
        }




    }

    private fun storeBitmap(bitmap: Bitmap) {

        val outputStream = applicationContext.contentResolver.openOutputStream(imageUri)
        if (outputStream != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        outputStream!!.close()

    }

    private fun getBitmapFromView(view: View) : Bitmap {

        /*val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val background = view.background
        background.draw(canvas)

        *//*
        if (view.background != null) {
            view.background.draw(canvas)
        } else {
            // Handle null background (e.g., set default background color)
            Toast.makeText(this@MainActivity, "No View Found", Toast.LENGTH_SHORT).show()
        }
        *//*

        view.draw(canvas)
        return bitmap
*/

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bg = view.background
        bg.draw(canvas)
        view.draw(canvas)
        return bitmap
    }

    private fun createImageUri() : Uri{

        val image = File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,"com.example.viewtobitmap.fileProvider", image)
    }
}