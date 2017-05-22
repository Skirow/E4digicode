package com.example.slam_2017_17.digicode_hertel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText ztLogin;
    private EditText ztMdp;
    private Button btConnect;
    DataBaseHelper myDbHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ztLogin= (EditText) findViewById(R.id.ztLogin);
        ztMdp= (EditText) findViewById(R.id.ztPassword);

        myDbHelper = new DataBaseHelper(getApplicationContext());
        btConnect = (Button) findViewById(R.id.btConnect);
        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = ztLogin.getText().toString();
                String mdp = ztMdp.getText().toString();
                if((login.length() == 0)|| (mdp.length() == 0))
                {
                    Toast.makeText(MainActivity.this, "Vous devez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }else {
                    if(myDbHelper.login(login,mdp))
                    {
                        Toast.makeText(MainActivity.this, "Connecté avec succès", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ChoixJourSalle.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(MainActivity.this, "Combinaison login/mdp invalide", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
