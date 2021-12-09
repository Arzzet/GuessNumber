package com.example.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

/*
    Abans de començar hem de crear
    (1) Un layout per al main amb una ListView anomenada recordsView
            R.id.recordsView
    (2) Un layout (lineal) per a la ListView anomenat list_item
            R.layout.list_item
        Aquest contindrà 2 TextView amb IDs: nom i intents
            R.id.nom
            R.id.intents

    Referències
        - https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */


public class Results extends AppCompatActivity {
    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }
        public int getIntents(){
            return this.intents;
        }

        public String getNom(){
            return this.nom;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    static ArrayList<Record> records = new ArrayList<Record>();

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);


        // Inicialitzem model
//        records = new ArrayList<Record>();
        // Afegim alguns exemples
//        records.add( new Record(33,"Manolo") );
//        records.add( new Record(12,"Pepe") );
//        records.add( new Record(42,"Laura") );
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] result = message.split(",");
        records.add(new Record(Integer.parseInt(result[1]),result[0]));
        System.out.println(records.get(0).getIntents());

        //Ordenacion por intentos
        int i, j, aux;
        for (i = 0; i < records.size() - 1; i++) {
            for (j = 0; j < records.size() - i - 1; j++) {

                if (records.get(j + 1).getIntents() < records.get(j).getIntents()) {

                    aux = records.get(j + 1).getIntents();
                    records.set(j + 1,records.get(j)).getIntents();
                    records.set(j,records.get(aux)).getIntents();

                    String aux2 = records.get(j + 1).getNom();
                    records.set(j + 1,records.get(j)).getNom();
                    records.set(j,records.get(aux)).getNom();

//                    aux = min.get(j + 1);
//                    min.set(j + 1,min.get(j));
//                    min.set(j,min.get(aux));
//
//                    aux = seg.get(j + 1);
//                    seg.set(j + 1,seg.get(j));
//                    seg.set(j,seg.get(aux));
//
//                    aux = mili.get(j + 1);
//                    mili.set(j + 1,mili.get(j));
//                    mili.set(j,mili.get(aux));

                }
            }
        }

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_view_ranking, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_view_ranking, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.rankingName)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.rankingScore)).setText(Integer.toString(getItem(pos).intents));
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.record_table);
        lv.setAdapter(adapter);


//        Intent intent = getIntent();
//        System.out.println("Intent ");
//        System.out.println(intent);
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//        String[] result =  message.split(",");
//        System.out.println("Message from intent: ");
//        System.out.println(message);
//        System.out.println(result.toString());
//        Ranking.add(result);
//        System.out.println(Ranking.toString());
//
//        TextView textView = findViewById(R.id.result);
//        System.out.println("textview: ");
//        textView.setText(message);
//        System.out.println("Set Text OK ");

    }









}