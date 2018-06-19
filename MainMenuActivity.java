package hamedinger.nico.whowantstobeamillionaire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by HAMED on 19.06.2018.
 */

public class MainMenuActivity extends Activity {


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main_menu);
    }

    public void startPressed(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void highPressed(View view)
    {
        Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
    }

    public void endPressed(View view)
    {
        Intent intent = new Intent(this, HeadClassActivity.class);
        startActivity(intent);
    }
}
