package project1.elen4901.example.android.myapplication;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.media.MediaPlayer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.EventListener;

public class MainActivity extends Activity {

    //added
    private ImageView imgView, animView;  //the first ImageView and animation in the view
    private ImageButton m_imgBtn, c_imgBtn, e_imgBtn;  // mouse, cat, elephant
    private Button s_Btn; //start button

    private TextView result_tv, computer_score, your_score;  //the textView of result and count
    int count = 0, computer_count = 0, your_count = 0; // initialize the count

    //intialize a listener to monitoring the three buttons
    MyOnClickListener myOnClickListener = new MyOnClickListener();

    //add two MediaPlayer objects
    private MediaPlayer mp_background;
    private MediaPlayer mp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //added

        //initialize buttons
        m_imgBtn = (ImageButton) findViewById(R.id.btnMouse);
        c_imgBtn = (ImageButton) findViewById(R.id.btnCat);
        e_imgBtn = (ImageButton) findViewById(R.id.btnElephant);
        s_Btn = (Button) findViewById(R.id.start);

        //initialize imgView
        imgView = (ImageView) findViewById(R.id.viewCmp);

        //initialize animView
        animView = (ImageView) findViewById(R.id.animation);
        animView.setImageResource(R.drawable.animation_list);

        //initialize result and count TextView
        result_tv = (TextView) findViewById(R.id.result);
        computer_score = (TextView) findViewById(R.id.computer_score);
        your_score = (TextView) findViewById(R.id.your_score);

        m_imgBtn.setOnClickListener(myOnClickListener);
        c_imgBtn.setOnClickListener(myOnClickListener);
        e_imgBtn.setOnClickListener(myOnClickListener);
        s_Btn.setOnClickListener(myOnClickListener);

        //declare the audio resource to these two MediaPlayer objects
        mp_background = MediaPlayer.create(this, R.raw.main);
        mp_button = MediaPlayer.create(this, R.raw.blaster);

        //play background music here
        mp_background.start();

        //end
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //create an AnimationDrawable object
            AnimationDrawable animationDrawable = (AnimationDrawable) animView.getDrawable();
            switch (v.getId()) {
                case R.id.start:
                    imgView.setVisibility(View.INVISIBLE);
                    animView.setVisibility(View.VISIBLE);
                    //start the animation
                    animationDrawable.start();
                    break;
                default:
                    //stop the animation
                    animationDrawable.stop();
                    animView.setVisibility(View.INVISIBLE);
                    //play button sound here
                    mp_button.start();

                    // get a random number form 1 to 3
                    int rand = (int) (Math.random() * 3 + 1);
                    count++;//

                    //store times of game in device
                    //storeData(count+"");
                    String tmp = "Result For Round " + count + ": ";
                    switch (rand) {
                        /**
                         * rand = 1 means computer is mouse,
                         * 2 represents cat,
                         * 3 represents elephant
                         */
                        case 1:
                            imgView.setImageResource(R.drawable.mouse);  //computer choose mouse
                            switch (v.getId()) {
                                case R.id.btnMouse:   //player choose mouse
                                    result_tv.setText(tmp + "Tied!");
                                    break;
                                case R.id.btnCat:  //player choose cat
                                    result_tv.setText(tmp + "Win!");
                                    your_count++;
                                    break;
                                case R.id.btnElephant:  //player choose elephant
                                    result_tv.setText(tmp + "Lose!");
                                    computer_count++;
                                    break;
                            }
                            break;
                        case 2:
                            imgView.setImageResource(R.drawable.cat);  //computer choose cat
                            switch (v.getId()) {
                                case R.id.btnMouse:
                                    result_tv.setText(tmp + "Lose!");
                                    computer_count++;
                                    break;
                                case R.id.btnCat:
                                    result_tv.setText(tmp + "Tie!");
                                    break;
                                case R.id.btnElephant:
                                    result_tv.setText(tmp + "Win!");
                                    your_count++;
                                    break;
                            }
                            break;
                        case 3:
                            imgView.setImageResource(R.drawable.elephant);  //computer choose elephant
                            switch (v.getId()) {
                                case R.id.btnMouse:
                                    result_tv.setText(tmp + "Win!");
                                    your_count++;
                                    break;
                                case R.id.btnCat:
                                    result_tv.setText(tmp + "Lose!");
                                    computer_count++;
                                    break;
                                case R.id.btnElephant:
                                    result_tv.setText(tmp + "Tie!");
                                    break;
                            }
                            break;
                    }
                    if (imgView.getVisibility() == View.INVISIBLE) {
                        imgView.setVisibility(View.VISIBLE);
                    }
                    computer_score.setText(String.valueOf(computer_count));
                    your_score.setText(String.valueOf(your_count));
            }
        }
    }

    @Override
    protected void onDestroy() {
        mp_background.stop();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
