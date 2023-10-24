package com.by_jogehrt.matapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import com.by_jogehrt.matapp.databinding.ActivityGeneralFormulaBinding
import kotlin.math.sqrt

class GeneralFormula : AppCompatActivity() {
    private lateinit var binding: ActivityGeneralFormulaBinding
    private lateinit var functions : Array<String>
    private var X1 :Float = 0.0f
    private var X2 :Float = 0.0f
    private var X2i :Float = 0.0f
    private var status:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralFormulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        functions.set(0,getString(R.string.name_inverse_mod))
        functions.set(1,getString(R.string.name_product_vec))

        binding.spnSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,functions)
        binding.spnSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                changeActivity()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.etNumA.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readyET()
            }
        })
        binding.etNumB.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readyET()
            }
        })
        binding.etNumC.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readyET()
            }
        })

        binding.btnAction.setOnClickListener {
            generalForm(binding.etNumA.text.toString().toFloat(),
                        binding.etNumB.text.toString().toFloat(),
                        binding.etNumC.text.toString().toFloat())
            if(status){
                binding.tvResultX1.isInvisible = false
                binding.tvResultX1.text = getString(R.string.res_x,"1",X1)
                binding.tvResultX2.isInvisible = false
                binding.tvResultX2.text = getString(R.string.res_x,"2",X2)
            }else{
                if(binding.etNumB.text.toString().toFloat() == 0.0f){
                    binding.tvResultX1.isInvisible = false
                    binding.tvResultX1.text = getString(R.string.res_x_img,"1",X2)
                    binding.tvResultX2.isInvisible = false
                    binding.tvResultX2.text = getString(R.string.res_x_img,"1",X2i)
                } else{
                    binding.tvResultX1.isInvisible = false
                    binding.tvResultX1.text = getString(R.string.res_x_comp,"1",X1,X2)
                    binding.tvResultX2.isInvisible = false
                    binding.tvResultX2.text = getString(R.string.res_x_comp,"2",X1,X2i)
                }

            }
        }
    }

    private fun changeActivity(){
        if( 0 == binding.spnSpinner.selectedItem.toString().compareTo(getString(R.string.name_inverse_mod))){
            startActivity(Intent(this,InvD::class.java))
            finish()
        }
        if( 0 == binding.spnSpinner.selectedItem.toString().compareTo(getString(R.string.name_product_vec))){
            startActivity(Intent(this,VectorOps::class.java))
            finish()
        }
    }

    fun getDisc(a:Float,b:Float,c:Float):Float = ((b*b) -(4*a*c))
    fun generalForm(a:Float, b:Float, c:Float){
        val d = getDisc(a,b,c)

        status = d < 0

        if(status){
            X1 = (-b) / (2 * a)
            X2 = sqrt(-d) / (2 * a)
            X2i = -sqrt(-d) / (2 * a)
        }else {
            X1 = ((-b) + sqrt(d)) / (2 * a)
            X2 = ((-b) - sqrt(d)) / (2 * a)
        }
    }

    private fun readyET() : Boolean = binding.etNumA.text.isNotEmpty() && binding.etNumB.text.isNotEmpty() && binding.etNumC.text.isNotEmpty()
}