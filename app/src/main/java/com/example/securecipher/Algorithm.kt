package com.example.securecipher

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Algorithm(
    val id:Int,
    val image:Int,
    val name:String,
    val rate:Float,
    val description:Int
): Parcelable {
    companion object{
        fun getAlgorithms():List<Algorithm>{
            return listOf(
                Algorithm(1,R.drawable.caecercipher,"Caesar Cipher",3F,R.string.caecer_cipher),
                Algorithm(2,R.drawable.playfair,"Playfair Cipher",4F,R.string.playfair_cipher),
                Algorithm(3,R.drawable.hillcipher,"Hill Cipher",4.5F,R.string.hill_cipher),
                Algorithm(4,R.drawable.vegneercipher,"One-Time Pad",5F,R.string.onetimepad_cipher),
                Algorithm(5,R.drawable.rowcolumn,"Row Transposition Cipher",3.5F,R.string.rowcolumn_cipher),
                Algorithm(6,R.drawable.railfance,"Rail Fence Technique",2F,R.string.railfance_cipher),
                Algorithm(7,R.drawable.monoalphabetic,"Monoalphabetic",2F,R.string.monoalphabetic),
            )
        }
    }
}
