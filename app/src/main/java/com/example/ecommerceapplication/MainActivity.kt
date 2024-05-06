package com.example.ecommerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var rv : RecyclerView
    private lateinit var rvAdapter : RvAdapter
    private lateinit var fab : FloatingActionButton
    val dummyList = mutableListOf<Product>()
//    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        fab = findViewById(R.id.fab)

        FirebaseDatabase.getInstance().getReference("products")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    dummyList.clear()
                    for(currentProduct in snapshot.children)
                    {
                        Log.i("checkThis",(currentProduct.getValue(Product::class.java)).toString())
                        dummyList.add((currentProduct.getValue(Product::class.java))!!)
                    }

                    dummyList.add(
                        Product(
                            pName = "testProduct1",
                            pPrice =  "testPrice",
                            pDesc = "testDesc",
                            pImg = "https://firebasestorage.googleapis.com/v0/b/ecommerceapplication-673f7.appspot.com/o/images%2F3fe08cb6-d0a6-4021-90fc-f7a67ed39006.jpg?alt=media&token=9538ce65-02bc-49a3-97d3-5bddab20df59"
                        )
                    )

                    rvAdapter = RvAdapter(dummyList,this@MainActivity)
                    rv.adapter = rvAdapter
                    rv.layoutManager = GridLayoutManager(this@MainActivity,2)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        fab.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}