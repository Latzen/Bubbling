package de.bubbling.game.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import de.bubbling.game.components.MyPreferenceManager;
import de.bubbling.game.difficulty.DifficultyProperties;

import static android.graphics.BitmapFactory.*;

public class MainMenuActivity extends Activity {

    private static final int CHOOSE_DIFFICULTY = 1;
    ImageButton playButton, statisticButton, settingsButton;
    MyPreferenceManager manager;
    int width, height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_to_left, R.anim.slide_out_to_left);
        if (android.os.Build.VERSION.SDK_INT <= 14) {
            setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        } else {
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
        }
        super.onCreate(savedInstanceState);
        manager = new MyPreferenceManager(this);
        initializeGUI();
    }

    private void initializeGUI() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        setContentView(R.layout.main);
        playButton = (ImageButton) findViewById(R.id.playButton);
        statisticButton = (ImageButton) findViewById(R.id.helpButton);
        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton playButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.playbuttonpressed);
                    playButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.playbutton);
                    playButton.setImageBitmap(image);
                    boolean skip = true;
                    if (skip || manager.getRemDiff()) {
                        startActivity(new Intent(MainMenuActivity.this, BubblingGameActivity.class));
                    } else {
                        showDialog(CHOOSE_DIFFICULTY);
                    }

                }
                return true;
            }
        });

        statisticButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton playButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.statistcbuttonpressed);
                    playButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(v.getResources(), R.drawable.statistcbutton);
                    playButton.setImageBitmap(image);
                    startActivity(new Intent(MainMenuActivity.this, StatisticActivity.class));
                }
                return true;
            }
        });

        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton playButton = (ImageButton) v;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap image = decodeResource(getResources(), R.drawable.settingsbuttonpressed);
                    playButton.setImageBitmap(image);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bitmap image = decodeResource(getResources(), R.drawable.settingsbutton);
                    playButton.setImageBitmap(image);
                    startActivity(new Intent(MainMenuActivity.this, PrefActivity.class));
                }
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                startActivity(new Intent(this, PrefActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CHOOSE_DIFFICULTY:
                View chooseView = View.inflate(this, R.layout.choosediff, null);
                ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, DifficultyProperties.difficulties) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        switch (position) {
                            case 0:
                                textView.setTextColor(Color.BLUE);
                                break;
                            case 1:
                                textView.setTextColor(Color.RED);
                                break;
                            case 2:
                                textView.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                final CheckBox remember = (CheckBox) chooseView.findViewById(R.id.remembermydecision);
                ListView diff = (ListView) chooseView.findViewById(R.id.diffList);
                diff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MainMenuActivity.this, BubblingGameActivity.class);
                        manager.setDifficulty(DifficultyProperties.difficulties[position]);
                        manager.setRemDiff(remember.isChecked());
                        dismissDialog(CHOOSE_DIFFICULTY);
                        startActivity(i);
                    }
                });
                diff.setAdapter(adapter);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose difficulty");
                builder.setView(chooseView);
                return builder.create();
        }
        return super.onCreateDialog(id);
    }

    private void showCustomDialog(int id) {

    }
}
