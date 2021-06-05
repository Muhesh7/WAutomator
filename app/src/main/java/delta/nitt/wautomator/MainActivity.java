package delta.nitt.wautomator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText)findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(v -> {
            if(!editText.getText().toString().isEmpty()){
                Log.d("AAA","Suceess");
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivityForResult(intent, 0);
                String[] s = {"+91 7358669054"};
                try{
                    PackageManager packageManager = MainActivity.this.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone="+ s[0] +"&text=" + URLEncoder.encode("HI", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }else {
                        Log.e("No WHATSAPP","no");
                    }
                } catch(Exception e) {
                    Log.e("ERROR WHATSAPP",e.toString());

                }

            }
            else{
                Log.d("AAA","fail");
            }

        });
    }
}