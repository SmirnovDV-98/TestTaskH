package com.example.testtaskharman

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class ThirdFragment : Fragment(){
    override fun onStart() {
        super.onStart()
        val v:ImageView = requireView().findViewById(R.id.ThirdFragmentImageView)
        when(ChosenPicture.chosenOne){
            Picture.CAT->{
                v.setImageResource(R.drawable.cat)
            }
            Picture.DOG->{
                v.setImageResource(R.drawable.doggy)
            }
            Picture.PARROT->{
                v.setImageResource(R.drawable.parrot)
            }
            Picture.DEFAULT->{
                v.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.third_fregment_layout,null)
    }
}