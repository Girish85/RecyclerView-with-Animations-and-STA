package com.example.usgir.recycler;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<listinfo> list;
    int a[],x;
    String s[],s1;
    SpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle1);
        list = new ArrayList<>();
        a = new int[]{R.drawable.enpt,R.drawable.yvm,R.drawable.rock,R.drawable.orange,R.drawable.neevalle};
        s = new String[]{"Maruvarthai", "Yenthentha", "Rockstar", "Orange", "Junepothey"};
        for (int i = 0; i < 50; i++) {
            listinfo currenter = new listinfo();
            currenter.text = s[i % 5];
            currenter.imgid = a[i % 5];
            list.add(currenter);
        }
        Adapt adapt = new Adapt(getApplicationContext(), list);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(getApplicationContext(),"Listening.....",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> arrayList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                int a = arrayList.size();
                if (a>0)
                {
                    s1 = arrayList.get(0);
                    if (s1.equalsIgnoreCase("down"))
                    {
                        anim();
                        speak();
                        //Toast.makeText(getApplicationContext(),"You said it",Toast.LENGTH_LONG).show();
                    }
                    else if (s1.equalsIgnoreCase("stop"))
                    {
                        stop();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
                        speak();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Couldn't figure out...",Toast.LENGTH_LONG).show();
                    speak();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };
        speechRecognizer.setRecognitionListener(recognitionListener);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        speak();
        return true;
    }
    void speak()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.startListening(intent);
    }
    void stop()
    {
        speechRecognizer.stopListening();
    }
    void anim()
    {
        ValueAnimator animator = ValueAnimator.ofInt(0,50);
        animator.setDuration(1000);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y = (int) animation.getAnimatedValue();
                recyclerView.scrollBy(0,y);
            }
        });
        animator.start();
    }
        }