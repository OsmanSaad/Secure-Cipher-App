package com.example.securecipher.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.securecipher.algorithms.CaesarCipher
import com.example.securecipher.algorithms.Hill
import com.example.securecipher.algorithms.Hill_Cipher
import com.example.securecipher.algorithms.Monoalphabetic
import com.example.securecipher.algorithms.OneTimePad
import com.example.securecipher.algorithms.Playfair
import com.example.securecipher.algorithms.RailFance
import com.example.securecipher.algorithms.RowColumnTransportation
import com.example.securecipher.databinding.FragmentAlgorithmBinding

class AlgorithmFragment : Fragment() {
    lateinit var binding: FragmentAlgorithmBinding
    private var operation = true;
    val args:AlgorithmFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlgorithmBinding.inflate(layoutInflater,container,false)
        bindViews()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radiogroub.setOnCheckedChangeListener { group, checkedId ->

            when(checkedId){
                binding.encripselect.id-> handelEncryption()
                binding.decripselect.id->handelDecryption()
            }
        }

        binding.operationbtn.setOnClickListener {
            if(validateData()){
                val text = binding.plaintextdefaultfield.text.toString()
                val key  = binding.keyfield.text.toString()
                val result:String
                if(operation){
                    result =  encryptionOperation(text,key)
                }
                else{
                    result = decryptionOperation(text,key)
                }
                binding.ciphertextdefaultfield.setText(result)
            }
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handelDecryption() {
        operation = false
        binding.plaintextdefaultfield.setText("")
        binding.keyfield.setText("")
        binding.ciphertextdefaultfield.setText("")
        binding.operationbtn.text = "Decryption"
        binding.plaintextdefault.hint = "Cipher Text"
        binding.ciphertextdefault.hint = "Plain Text"
        binding.ciphertextdefault.isEnabled = false
    }

    private fun handelEncryption() {
        operation = true
        binding.plaintextdefaultfield.setText("")
        binding.keyfield.setText("")
        binding.ciphertextdefaultfield.setText("")
        binding.operationbtn.text = "Encryption"
        binding.plaintextdefault.hint = "Plain Text"
        binding.ciphertextdefault.hint = "Cipher Text"
        binding.ciphertextdefault.isEnabled = false
    }

    private fun encryptionOperation(plainText:String,key:String):String{
        return  when(args.algorithm.id){
            1-> CaesarCipher.encrypt(plainText,key.toInt())
            2->Playfair.playfairEncrypt(plainText,key)
            3->Hill_Cipher.encrypt(plainText,parseKeyMatrix(key))
            4-> OneTimePad.oneTimePad(plainText,key,true)
            5->RowColumnTransportation.columnarEncrypt(plainText,key)
            6->RailFance.encryptRailFence(plainText,key.toInt())
            7-> Monoalphabetic.encrypt(plainText,key)
            else -> ""
        }
    }


    private fun decryptionOperation(plainText:String,key:String):String{
        return  when(args.algorithm.id){
            1-> CaesarCipher.decrypt(plainText,key.toInt())
            2-> Playfair.playfairDecrypt(plainText,key)
            3-> Hill_Cipher.decrypt(plainText,parseKeyMatrix(key))!!
            4-> OneTimePad.oneTimePad(plainText,key,false)
            5-> RowColumnTransportation.columnarDecrypt(plainText,key)
            6-> RailFance.decryptRailFence(plainText,key.toInt())
            7-> Monoalphabetic.decrypt(plainText,key)
            else -> ""
        }
    }

    private fun bindViews(){
        val algorithm = args.algorithm
        binding.algorithmImage.setImageResource(algorithm.image)
        binding.algorithmDesc.text = getString(algorithm.description)
        binding.toolbartext.text = algorithm.name
        binding.algorithmRate.rating = algorithm.rate
    }

    private fun validateData():Boolean{
        var validate = false
        if(binding.plaintextdefaultfield.text.isNullOrBlank()){
            binding.plaintextdefaultfield.error = "This field is required"
            validate = false
        }else{
            validate = true
        }

        if(binding.keyfield.text.isNullOrBlank()){
            binding.keyfield.error = "This field is required"
            validate = false
        }else{
            validate = true
        }

        return validate
    }

    private fun parseKeyMatrix(key: String): Array<IntArray> {
        val elements = key.trim().split("\\s+".toRegex())

        if (elements.size != 4) {
            throw IllegalArgumentException("Invalid number of elements. Please enter 4 elements.")
        }

        return try {
            val matrix = Array(2) { IntArray(2) }
            matrix[0][0] = elements[0].toInt()
            matrix[0][1] = elements[1].toInt()
            matrix[1][0] = elements[2].toInt()
            matrix[1][1] = elements[3].toInt()
            matrix
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid input. Please enter valid integer values.")
        }
    }
}