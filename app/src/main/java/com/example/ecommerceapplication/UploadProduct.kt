package com.example.ecommerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadProduct : AppCompatActivity() {

    private lateinit var  pName : EditText
    private lateinit var  pPrice : EditText
    private lateinit var  pDesc : EditText
    private lateinit var  pImg : ImageView
    private lateinit var  getImg : Button
    private lateinit var  progressBar : ProgressBar
    private lateinit var  subBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)

        pName = findViewById(R.id.pName)
        pPrice = findViewById(R.id.pPrice)
        pDesc = findViewById(R.id.pDesc)
        pImg = findViewById(R.id.pImg)
        getImg = findViewById(R.id.getImg)
        progressBar = findViewById(R.id.loadingBar)
        subBtn = findViewById(R.id.subBtn)

        getImg.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),4004)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 4004) {
            val uri = (data!!.data)
            pImg.setImageURI(data?.data)
            pImg.visibility = View.VISIBLE

            subBtn.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val productName = pName.text.toString()
                val productPrice = pPrice.text.toString()
                val productDesc = pDesc.text.toString()

                val fileName = UUID.randomUUID().toString() + ".jpg"
                val storageRef = FirebaseStorage.getInstance().reference.child("images/$fileName")
                storageRef.putFile(uri!!).addOnSuccessListener {
                    val result = it.metadata!!.reference!!.downloadUrl
                    result?.addOnSuccessListener {
                        uploadProduct(
                            productName,
                            productPrice,
                            productDesc,
                            it.toString()
                        )
                    }
                }
            }
        }
    }

    private fun uploadProduct(pNameStr: String, pPriceStr: String, pDescStr: String, pImgStr: String) {
        val productData = mapOf(
            "pName" to  pNameStr,
            "pPrice" to pPriceStr,
            "pDesc" to pDescStr,
            "pImg" to pImgStr
        )

        Firebase.database.getReference("products").
        child(pNameStr).setValue(productData).
        addOnSuccessListener {
            progressBar.visibility = View.GONE
            Toast.makeText(this,"Successfully Uploaded",Toast.LENGTH_SHORT).show()
        }
    }
}