package com.example.testtaskharman

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val CAT_ID = 1
    private val DOG_ID:Int=2
    private val PARROT_ID:Int=3
    private val DEFAULT_ID:Int=0
    private val ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB: Int = Color.rgb(254,176,5)
    private val NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB:Int = Color.rgb(210,88,3)

    val frag1:FirstFragment = FirstFragment()
    val frag2:SecondFragment = SecondFragment()
    val frag3:ThirdFragment=ThirdFragment()
    val fragManager = supportFragmentManager

    lateinit var currentActiveFragment:CurrentFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ChosenPicture.chosenOne=Picture.DEFAULT
        findViewById<Button>(R.id.firstBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
        findViewById<Button>(R.id.secondBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
        findViewById<Button>(R.id.thirdButton).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
    }

    override fun onPause() {
        super.onPause()
        saveLastChoose()
    }

    override fun onResume() {
        super.onResume()
        loadLastChoose()
    }

    /**
     * Function for reacting on interaction with top buttons.
     * Changing fragments in fragment layout and change active color button
      */
    fun onClick(v: View){
        val transactor = fragManager.beginTransaction()
        when(v.id){
            R.id.firstBtn->{
                transactor.replace(R.id.FragmentArea,frag1)
                currentActiveFragment=CurrentFragment.FIRST
                findViewById<Button>(R.id.firstBtn).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                findViewById<Button>(R.id.secondBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                findViewById<Button>(R.id.thirdButton).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
            }
            R.id.secondBtn->{
                transactor.replace(R.id.FragmentArea,frag2)
                currentActiveFragment=CurrentFragment.SECOND
                findViewById<Button>(R.id.firstBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                findViewById<Button>(R.id.secondBtn).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                findViewById<Button>(R.id.thirdButton).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
            }
            R.id.thirdButton->{
                transactor.replace(R.id.FragmentArea,frag3)
                currentActiveFragment=CurrentFragment.THIRD
                findViewById<Button>(R.id.firstBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                findViewById<Button>(R.id.secondBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                findViewById<Button>(R.id.thirdButton).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
            }
        }
        transactor.commit()
    }

    /**
     * Swipe listener. Based on class OnSwipeListener.kt
     * implements override onSwipeLeft() and onSwipeRight() functions
     */
    fun onSwipeListener(v:View){
        v.setOnTouchListener(object:OnSwipeTouchListener(this@MainActivity){
            override fun onSwipeLeft() {
                val transactor = fragManager.beginTransaction()
                when(currentActiveFragment){
                    CurrentFragment.FIRST->{
                        transactor.replace(R.id.FragmentArea,frag2)
                        currentActiveFragment=CurrentFragment.SECOND
                        findViewById<Button>(R.id.firstBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.secondBtn).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.thirdButton).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                    }
                    CurrentFragment.SECOND->{
                        transactor.replace(R.id.FragmentArea,frag3)
                        currentActiveFragment=CurrentFragment.THIRD
                        findViewById<Button>(R.id.firstBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.secondBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.thirdButton).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                    }
                    CurrentFragment.THIRD->{
                        //Do nothing, don't swap to first
                    }
                }
                transactor.commit()
            }

            override fun onSwipeRight() {
                val transactor = fragManager.beginTransaction()
                when(currentActiveFragment){
                    CurrentFragment.FIRST->{
                        //Dp nothing, don't swap to third
                    }
                    CurrentFragment.SECOND->{
                        transactor.replace(R.id.FragmentArea,frag1)
                        currentActiveFragment=CurrentFragment.FIRST
                        findViewById<Button>(R.id.firstBtn).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.secondBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.thirdButton).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                    }
                    CurrentFragment.THIRD->{
                        transactor.replace(R.id.FragmentArea,frag2)
                        currentActiveFragment=CurrentFragment.SECOND
                        findViewById<Button>(R.id.firstBtn).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.secondBtn).setBackgroundColor(ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                        findViewById<Button>(R.id.thirdButton).setBackgroundColor(NOT_ACTIVE_BTH_FRAGMENT_COLOR_INT_RGB)
                    }
                }
                transactor.commit()
            }
        })
    }

    /**
     * Reaction on buttons from second fragment.
     * This button used to declare picket, that will be shown at third fragment
     */
    fun onClickButImage(v:View){
        when(v.id){
            R.id.secondFirstButton->{
                ChosenPicture.chosenOne=Picture.CAT
            }
            R.id.SecondSecondButton->{
                ChosenPicture.chosenOne=Picture.DOG
            }
            R.id.SecondThirdButton->{
                ChosenPicture.chosenOne=Picture.PARROT
            }
        }
    }

    /**
     * Functions for save and load last chosen variant of picture from third fragment
     */
    private fun saveLastChoose(){
        val saveChosenOne:SharedPreferences=getSharedPreferences("save", MODE_PRIVATE)
        val editor=saveChosenOne.edit()
        when(ChosenPicture.chosenOne){
            Picture.CAT->{
                editor.putInt("Picture id",CAT_ID)
            }
            Picture.DOG->{
                editor.putInt("Picture id",DOG_ID)
            }
            Picture.PARROT->{
                editor.putInt("Picture id",PARROT_ID)
            }
            Picture.DEFAULT->{
                editor.putInt("Picture id",DEFAULT_ID)
            }
        }
        editor.apply()
    }
    private fun loadLastChoose(){
        val saveChosenOne:SharedPreferences=getSharedPreferences("save", MODE_PRIVATE)
        when(saveChosenOne.getInt("Picture id",0)){
            CAT_ID->{
                ChosenPicture.chosenOne=Picture.CAT
            }
            DOG_ID->{
                ChosenPicture.chosenOne=Picture.DOG
            }
            PARROT_ID->{
                ChosenPicture.chosenOne=Picture.PARROT
            }
            DEFAULT_ID->{
                ChosenPicture.chosenOne=Picture.DEFAULT
            }
        }
    }
}

object ChosenPicture{
    var chosenOne:Picture=Picture.DEFAULT
}