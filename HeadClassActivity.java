package hamedinger.nico.whowantstobeamillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by HAMED on 19.06.2018.
 */

public class HeadClassActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_class);
    }

    public void WWMPressed(View view)
    {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void CAHPressed(View view)
    {
        //Intent intent = new Intent(this, MainActivityCAH.class);
        //startActivity(intent);
    }
}
