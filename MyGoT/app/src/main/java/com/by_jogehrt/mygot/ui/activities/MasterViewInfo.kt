package com.by_jogehrt.mygot.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.by_jogehrt.mygot.R
import com.by_jogehrt.mygot.databinding.ActivityMasterViewInfoBinding
import com.by_jogehrt.mygot.info.InfoDetail
import com.by_jogehrt.mygot.internet.GoTAPI
import com.by_jogehrt.mygot.internet.RetrofitService
import com.by_jogehrt.mygot.ui.adapter.InfoAdapter
import com.by_jogehrt.mygot.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasterViewInfo : AppCompatActivity() { //Main Class
    private lateinit var binding: ActivityMasterViewInfoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterViewInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitService.getRetrofit().create(GoTAPI::class.java).getInfoNodes()

        call.enqueue(object: Callback<ArrayList<InfoDetail>>{
            override fun onResponse(call: Call<ArrayList<InfoDetail>>, response: Response<ArrayList<InfoDetail>>) {
                binding.pbConexion.visibility = View.INVISIBLE

                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                val infAdapter = InfoAdapter(response.body()!!){ inf ->
                    val bundle = bundleOf("id" to inf.id)
                    val intent = Intent(this@MasterViewInfo, MainList::class.java)

                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                binding.rvMenu.layoutManager = LinearLayoutManager(this@MasterViewInfo, RecyclerView.VERTICAL, false)
                binding.rvMenu.adapter = infAdapter
            }

            override fun onFailure(call: Call<ArrayList<InfoDetail>>, t: Throwable) {
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MasterViewInfo, getString(R.string.error_no_online), Toast.LENGTH_SHORT).show()
            }

        })

       binding.btLogOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, Loggin::class.java))
            finish()
        }
    }
}

/*Layout solo con el recycler
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background_loggin"
    tools:context=".ui.activities.MasterViewInfo">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <ProgressBar
        android:id="@+id/pbConexion"
        style="?android:attr/progressBarStyle"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:indeterminateDuration="1500"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        />

</androidx.constraintlayout.widget.ConstraintLayout>*/
/*Layour con Titulo y boton de cerrar sesión
<TextView
                android:id="@+id/tvTitle"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"

                android:text="My Game of Thrones"
                android:fontFamily="@font/new_rocker"
                android:textAlignment="center"
                android:textColor="@color/text_loggin"
                android:textSize="40sp"
                android:textStyle="bold"

                app:layout_constraintTop_toTopOf="parent"/>

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rvMenu"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toBottomOf="@id/tvTitle"
                app:layout_constraintBottom_toTopOf="@id/btLogOut"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"/>

            <Button
                android:id="@+id/btLogOut"
                android:layout_width="wrap_content"
                android:layout_height="60dp"
                android:layout_gravity="center"
                android:backgroundTint="@color/background_button"
                android:text="Cerrar Sesion"
                android:fontFamily="@font/new_rocker"
                app:layout_constraintTop_toBottomOf="@id/rvMenu"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"/>*/