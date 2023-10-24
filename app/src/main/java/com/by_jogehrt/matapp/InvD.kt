package com.by_jogehrt.matapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible

import com.by_jogehrt.matapp.databinding.ActivityInvDBinding

class InvD : AppCompatActivity() {
    private lateinit var binding: ActivityInvDBinding
    private lateinit var functions : Array<String>
    private var d : Int = 0
    private var e : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvDBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNumVal.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readyET()
            }
        })
        binding.etNumMod.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readyET()
                e = binding.etNumMod.text.toString().toInt()
                if(esPrimo(e))
                    binding.tvAnticipo.text = getString(R.string.com_positive)
                else
                    binding.tvAnticipo.text = getString(R.string.com_negative)
            }
        })

        binding.btnAction.setOnClickListener {
            d = binding.etNumVal.text.toString().toInt()
            val c = getInvD(d,e);
            if(c == 0)
                binding.tvResultado.text = getString(R.string.res_invd,d,e,c)
            else
                binding.tvResultado.text = getString(R.string.res_invd_not,d,e)
            binding.tvAnticipo.isInvisible = true
        }

        functions.set(0,getString(R.string.name_general_formula))
        functions.set(1,getString(R.string.name_product_vec))

        binding.spnSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,functions)
        binding.spnSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                changeActivity()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    }

    private fun changeActivity(){
        if( 0 == binding.spnSpinner.selectedItem.toString().compareTo(getString(R.string.name_general_formula))){
            startActivity(Intent(this,GeneralFormula::class.java))
            finish()
        }
        if( 0 == binding.spnSpinner.selectedItem.toString().compareTo(getString(R.string.name_product_vec))){
            startActivity(Intent(this,VectorOps::class.java))
            finish()
        }
    }
    private fun getInvD(n: Int, m: Int): Int{
        for (i in 1 until m) if ((i * n) % m == 1) return i
        return 0
    }
    private fun esPrimo(numero: Int): Boolean{
        if(numero <= 1) return false

        for(i in 2 until numero) if(numero%i == 0) return false

        return true
    }
    private fun readyET() : Boolean = binding.etNumVal.text.isNotEmpty() && binding.etNumMod.text.isNotEmpty()
}