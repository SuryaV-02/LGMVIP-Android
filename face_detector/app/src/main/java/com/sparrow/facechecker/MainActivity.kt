package com.sparrow.facechecker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.setPadding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    var selectedPhotoUri : Uri? = null
    var imageBitmap : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            setupView()
        },2000)
    }

    private fun setupView() {
        val ll_splash_screen = findViewById<LinearLayout>(R.id.ll_splash_screen)
        val ll_main_content = findViewById<LinearLayout>(R.id.ll_main_content)

        ll_splash_screen.visibility = View.GONE
        ll_main_content.visibility = View.VISIBLE
        val ll_upload_image = findViewById<LinearLayout>(R.id.ll_upload_image)
        ll_upload_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
        val ibtn_upload_image = findViewById<com.mikhaellopez.circularimageview.CircularImageView>(R.id.ibtn_upload_image)
        ibtn_upload_image.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
        val btn_check_face =findViewById<Button>(R.id.btn_check_face)
        btn_check_face.setOnClickListener {
            detectFace()
        }
    }

    private fun detectFace() {
        if(selectedPhotoUri == null){
            Toast.makeText(this, "Please Upload a photo first", Toast.LENGTH_SHORT).show()
            return
        }else{
            val highAccuracyOps = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()

            var image : InputImage? = null

            try{
                image = InputImage.fromFilePath(this,selectedPhotoUri)
            }catch (e : Exception){
                e.printStackTrace()
            }
            val tv_result = findViewById<TextView>(R.id.tv_result)
            val detector = FaceDetection.getClient(highAccuracyOps)
            val result = detector.process(image)
                .addOnSuccessListener {
                    if(it.size == 0){
                        tv_result.text = "No face detected"
                    }
                    for (face in it) {
                        val bounds = face.boundingBox
                        val tempBitmap = imageBitmap!!.copy(Bitmap.Config.RGB_565,true)
                        val canvas = Canvas(tempBitmap)
                        val paint = Paint()
                        paint.alpha = 0xA0
                        paint.color = Color.RED
                        paint.style = Paint.Style.STROKE
                        paint.strokeWidth = 4F

                        canvas.drawRect(bounds,paint)
                        val ibtn_upload_image = findViewById<com.mikhaellopez.circularimageview.CircularImageView>(R.id.ibtn_upload_image)
                        ibtn_upload_image.setImageBitmap(tempBitmap)
                        ibtn_upload_image.setPadding(0)
                        tv_result.text = "Face detected!!!"

                        if(face.smilingProbability !=null){
                            val smileProb = (face.smilingProbability)*100
                            tv_result.text = "Face Detected"
                            Log.i("SKHST_8961","${smileProb.roundToInt()}")
                        }else{
                            Log.i("SKHST_8961","User didn't smiled!")
                        }

                        if (face.smilingProbability != null) {
                            val smileProb = face.smilingProbability
                            Log.i("SRI171103109","${smileProb}")
                        }
                        else{
                            Log.i("SRI171103109","${null}")
                        }
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data!=null){
            selectedPhotoUri = data.data
            imageBitmap = MediaStore.Images.Media.getBitmap(
                contentResolver,
                selectedPhotoUri
            )
            val ll_upload_image = findViewById<LinearLayout>(R.id.ll_upload_image)
            val ibtn_upload_image = findViewById<com.mikhaellopez.circularimageview.CircularImageView>(R.id.ibtn_upload_image)
            ll_upload_image.visibility = View.GONE
            ibtn_upload_image.visibility = View.VISIBLE
            ibtn_upload_image.setImageBitmap(imageBitmap)
        }
    }
}