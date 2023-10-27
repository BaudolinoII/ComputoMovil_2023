package com.by_jogehrt.matapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import com.by_jogehrt.matapp.databinding.ActivityVectorOpsBinding
import kotlin.math.acos
import kotlin.math.sqrt

class VectorOps : AppCompatActivity() {
    private lateinit var binding: ActivityVectorOpsBinding
    private lateinit var functions : Array<String>
    private var res_Vec = arrayOf<Float>(0.0f, 0.0f, 0.0f)
    private var res_scl : Float = 0.0f
    private var vecs = arrayOf<Float>(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    private var normal: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVectorOpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        functions = emptyArray()

        functions = append(functions, getString(R.string.name_product_vec))
        functions = append(functions, getString(R.string.name_inverse_mod))
        functions = append(functions, getString(R.string.name_general_formula))

        binding.spnSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,functions)
        binding.spnSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                changeActivity()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.etNumAX.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.etNumAY.isEnabled = readiV1()
                binding.etNumBY.isEnabled = readiV1()
                binding.etNumCY.isEnabled = readiV1()
            }
        })
        binding.etNumBX.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.etNumAY.isEnabled = readiV1()
                binding.etNumBY.isEnabled = readiV1()
                binding.etNumCY.isEnabled = readiV1()
            }
        })
        binding.etNumCX.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.etNumAY.isEnabled = readiV1()
                binding.etNumBY.isEnabled = readiV1()
                binding.etNumCY.isEnabled = readiV1()
            }
        })

        binding.etNumAY.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readiV2()
            }
        })
        binding.etNumBY.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readiV2()
            }
        })
        binding.etNumCY.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                //Validar si hay algo en la caja, para habilitar el botón
                binding.btnAction.isEnabled = readiV2()
            }
        })

        binding.btnAction.setOnClickListener {
            vecs[0] = binding.etNumAX.text.toString().toFloat()
            vecs[1] = binding.etNumBX.text.toString().toFloat()
            vecs[2] = binding.etNumCX.text.toString().toFloat()
            vecs[3] = binding.etNumAY.text.toString().toFloat()
            vecs[4] = binding.etNumBY.text.toString().toFloat()
            vecs[5] = binding.etNumCY.text.toString().toFloat()

            getCrossProduct()
            getDotProduct()

            binding.btnNormal.isEnabled = readiV2()

            binding.tvResultCross.isInvisible = false
            binding.tvResultCross.text = getString(R.string.res_cross, res_Vec[0], res_Vec[1], res_Vec[2])

            binding.tvResultDot.isInvisible = false
            binding.tvResultDot.text = getString(R.string.res_dot, res_scl)
        }
        binding.btnNormal.setOnClickListener{
            normal = !normal

            getCrossProduct()
            getDotProduct()

            binding.tvResultCross.isInvisible = false
            binding.tvResultCross.text = getString(R.string.res_cross, res_Vec[0], res_Vec[1], res_Vec[2])

            binding.tvResultDot.isInvisible = false
            binding.tvResultDot.text = getString(R.string.res_dot, res_scl)

            if(normal)
                binding.btnNormal.text = getString(R.string.btn_action3)
            else
                binding.btnNormal.text = getString(R.string.btn_action2)
            getCrossProduct()
        }
    }

    private fun append(arr: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }

    private fun changeActivity(){
        if( 0 == binding.spnSpinner.selectedItem.toString().compareTo(getString(R.string.name_general_formula))){
            startActivity(Intent(this,GeneralFormula::class.java))
            finish()
        }
        if( 0 == binding.spnSpinner.selectedItem.toString().compareTo(getString(R.string.name_inverse_mod))){
            startActivity(Intent(this,InvD::class.java))
            finish()
        }
    }

    private fun getScale() : Float{
        return sqrt(vecs[3]*vecs[3] + vecs[4]*vecs[4] + vecs[5]*vecs[5]) * sqrt(vecs[0]*vecs[0] + vecs[1]*vecs[1] + vecs[2]*vecs[2])
    }
    private fun getCrossProduct(){
        if(normal){
            res_Vec[0] = (vecs[1]*vecs[5] - vecs[2]*vecs[4])/getScale();
            res_Vec[1] = (vecs[2]*vecs[3] - vecs[0]*vecs[5])/getScale();
            res_Vec[2] = (vecs[0]*vecs[4] - vecs[1]*vecs[3])/getScale();
        } else{
            res_Vec[0] = vecs[1]*vecs[5] - vecs[2]*vecs[4];
            res_Vec[1] = vecs[2]*vecs[3] - vecs[0]*vecs[5];
            res_Vec[2] = vecs[0]*vecs[4] - vecs[1]*vecs[3];
        }
    }
    private fun getDotProduct(){
        res_scl = acos((vecs[0]*vecs[3] + vecs[1]*vecs[4] + vecs[2]*vecs[5])/getScale())
    }

    private fun readiV1() : Boolean = binding.etNumAX.text.isNotEmpty() && binding.etNumBX.text.isNotEmpty() && binding.etNumCX.text.isNotEmpty()
    private fun readiV2() : Boolean = binding.etNumAY.text.isNotEmpty() && binding.etNumBY.text.isNotEmpty() && binding.etNumCY.text.isNotEmpty()
}