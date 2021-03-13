package com.example.findme;


import android.content.Context;
import android.media.AudioAttributes;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class speak  {
TextToSpeech textToSpeech;
Context context;
//constructor
    public speak( Context context) {
       textToSpeech =new TextToSpeech(context, new TextToSpeech.OnInitListener() {
           @Override
           public void onInit(int status) {
               if(status!=TextToSpeech.ERROR){


                   textToSpeech.setLanguage(Locale.CANADA);

                   textToSpeech.setSpeechRate(0.85f);








               }
           }
       });
        this.context = context;
    }


    public void speakit(String text){
textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);



    }
    public void speakit(EditText editText){
        String text=editText.getText().toString();
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);



    }
    public void speakit(TextView editText){
        String text=editText.getText().toString();
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);



    }
    public void speakit(Object obText){
        String text=obText.toString();

        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);



    }
    public void stopit(){
        textToSpeech.stop();
    }





}



