package com.example.guessnumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.guessnumber";
    static int intentCount;
    EditText numberInput;
    int guessedNumber;
    Random r = new Random();
    int secretNumber;
    boolean isOn = false;
    int mili=0, seg=0, min=0;
    String mi = "", s = "", m = "";
    Handler h = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentCount = 0;
        secretNumber = r.nextInt(100);


        setContentView(R.layout.activity_main);

        TextView cronom = (TextView) findViewById(R.id.chrono);


            //Algoritmo del cronometro
            Thread cronos = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (isOn) {

                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                System.out.println("A");
                            }
                            mili++;
                            if (mili == 999) {
                                seg++;
                                mili = 0;
                            }
                            if (seg == 99) {
                                min++;
                                seg = 0;
                            }
                            h.post(new Runnable() {
                                @Override
                                public void run() {
                                    String m = "", s = "", mi = "";
                                    if (mili < 10) {
                                        m = "00" + mili;
                                    } else if (mili < 100) {
                                        m = "0" + mili;
                                    } else {
                                        m = "" + mili;
                                    }
                                    if (seg < 10) {
                                        s = "0" + seg;
                                    } else {
                                        s = "" + seg;
                                    }
                                    if (min < 10) {
                                        mi = "0" + min;
                                    } else {
                                        mi = "" + min;
                                    }
                                    cronom.setText(mi + ":" + s + ":" + m);
                                }

                            });

                        }
                    }
                }
            }
            );
            cronos.start();
        }
    public void startTimer(View view){
        isOn = true;
    }
    public void stopTimer(View view){
        isOn = false;
    }

    public void checkNumber(View view) {
        // Compara el numero del textfield con el numero secreto

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        numberInput = (EditText) findViewById(R.id.editTextNumber);
        try {
            guessedNumber = Integer.parseInt(numberInput.getText().toString());


            intentCount++;

            if (guessedNumber == secretNumber) {
                Toast.makeText(context, "Has acertado", duration).show();
                Intent intent = new Intent(this, Results.class);
//                String message = "Nº intentos: " + intentCount;
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

//                alert.setTitle("Nº Intentos: "+ intentCount+"\nTiempo: "+ min + ":" + seg + ":" + mili);
//                alert.setMessage("¿¿¿¿¿Quieres ingresar al Rangking??????");

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Editable value = input.getText();
                        String message = value + "," + intentCount + ","+ min + ":" + seg + ":" + mili;
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                isOn = false;

                alert.setTitle("Nº Intentos: "+ intentCount+"\nTiempo: "+ min + ":" + seg + ":" + mili );
                alert.setMessage("¿¿¿¿¿Quieres ingresar al Rangking??????");

                alert.show();

//              intent.putExtra(EXTRA_MESSAGE, message);
//              startActivity(intent);
            } else if (guessedNumber < secretNumber) {
                Toast.makeText(context, "El numero secreto es mayor", duration).show();
            } else if (guessedNumber > secretNumber) {
                Toast.makeText(context, "El numero secreto es menor", duration).show();
            }
        } catch (Exception e){
            Toast.makeText(context,"¡Pon un número!",duration).show();
        }
        EditText resetText = (EditText) findViewById(R.id.editTextNumber);
        resetText.getText().clear();
        System.out.println("Guessed number: " + guessedNumber);
        System.out.println("Secret number: " + secretNumber);
        System.out.println("Intentos: " + intentCount);

    }
//    public class PuntosDialog extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("Nº Intentos: "+ intentCount+"\nTiempo: "+ min + ":" + seg + ":" + mili)
//                    .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // guardar en ranking
//                        }
//                    })
//                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
//            // Create the AlertDialog object and return it
//            return builder.create();
//        }
//    }

}

//        System.out.println(guessedNumber);


//        String text = String.valueOf(guessedNumber);
//        int duration = Toast.LENGTH_SHORT;

//        Toast.makeText(context, text, duration).show();

//        numberInput = (EditText) findViewById(R.id.editTextNumber);
//        text = (CharSequence) numberInput;
//        Toast.makeText(context, text, duration).show();
//        guessedNumber = Integer.valueOf(numberInput.getText().toString());
//        text = (CharSequence) guessedNumber;
//        Toast.makeText(context, text, duration).show();


//        if(guessedNumber == secretNumber) {
//            showMessage("Has acertado");
//
//        } else if (guessedNumber < secretNumber) {
//            showMessage("El numero secreto es mayor");
//        } else if (guessedNumber > secretNumber) {
//            showMessage("El numero secreto es menor");
//        };
//    }

//    private void showMessage(String text) {
//        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
//    }




//        final Button button = findViewById(R.id.Verify);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {



//                Context context = getApplicationContext();
//                CharSequence text = "Olivares dale Gas!";
//                int duration = Toast.LENGTH_LONG;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//
//
//
//            }
//        });


