package multiplemaps.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.googlemap_button:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("GoogleMap", true);
                startActivity(intent);
                break;
            case R.id.heremap_button:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("GoogleMap", false);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
