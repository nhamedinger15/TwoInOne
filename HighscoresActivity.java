package hamedinger.nico.whowantstobeamillionaire;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HAMED on 19.06.2018.
 */

public class HighscoresActivity extends ListActivity
{
    ArrayList<String> names = new ArrayList<>();

    @Override
    public void onCreate (Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.highscore_activity);

        displayNothing(); // Saving the Highscores forever still in progress!
        askHighscore();

    }

    private void displayNothing()
    {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }

        };

        setListAdapter(adapter);
    }

    private void askHighscore()
    {
        final Intent intent = getIntent();
        final Bundle param = intent.getExtras();

        if(param != null) {

            final EditText txtNewName = new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Name eingeben!")
                    .setCancelable(false)
                    .setView(txtNewName)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newName = txtNewName.getText().toString();

                            String score = "";

                            score = param.getString("Score");

                            names.add(newName + " mit " + score);
                        }
                    })

                    .setNegativeButton("Nein Danke!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    public void returnToMenu(View view)
    {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}

