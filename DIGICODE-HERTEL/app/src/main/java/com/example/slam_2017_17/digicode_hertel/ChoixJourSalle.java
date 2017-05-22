package com.example.slam_2017_17.digicode_hertel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChoixJourSalle extends AppCompatActivity {

    private DatePicker datePicker;
    TextView salleChoix;
    int numSalle;
    DataBaseHelper dbHelper;

    int jour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_jour_salle);

        dbHelper = new DataBaseHelper(getApplicationContext());
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        Button btDigi = (Button) findViewById(R.id.btDigi);

        //Constituion de la zone de liste des salles
        ListView listeSalles = null;

        /** Tableau des salles **/
        listeSalles = (ListView) findViewById(R.id.lvSalles);

        //On ajoute un adaptateur qui affiche les salles dans la liste
        listeSalles.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, dbHelper.recupSalles()));

        btDigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jour = datePicker.getDayOfMonth();
                System.out.println("Jour" + jour);
                Toast.makeText(getApplicationContext(), "Digicode : "+ dbHelper.recupDigicode(jour, numSalle), Toast.LENGTH_SHORT).show();}});

        //ajout d'un listener pour recuperer la salle choisie
        listeSalles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //on recupere le nom de la salle choisie
                salleChoix = (TextView) view;
                // on recupere le numero de la salle choisie
                numSalle = i + 1;

                Toast.makeText(getApplicationContext(), " choix N° "+ numSalle + " "+ salleChoix.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
