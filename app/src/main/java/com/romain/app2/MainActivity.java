package com.romain.app2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Définition des pointeurs momentanément initialisés à null
    private EditText idTxtNumber = null;
    private Button btnValider;
    private TextView lblResult;
    private ProgressBar idProgressBar;
    private TextView idTxtHistory;

    private int searchedValue;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Le xml a été lu et traité et le système produit des instances à notre place (boutons,
        // zones de texte) puis inbjectés dans l'interface graphique
        setContentView(R.layout.activity_main);

        // Initialisation des pointeurs sur ces objets définis dans le layout
        idTxtNumber = (EditText) findViewById(R.id.idTxtNumber);
        btnValider = (Button) findViewById(R.id.btnValider);
        lblResult = (TextView) findViewById(R.id.lblResult);
        idProgressBar = (ProgressBar) findViewById(R.id.idProgressBar);
        idTxtHistory = (TextView) findViewById(R.id.idTxtHistory);

        // L'écouteur OnClickLister écoute bien le bon bouton
        btnValider.setOnClickListener(btnValiderListener);

        // Au cas où on recommence une partie
        init();

    }

    // Méthode d'initialisation
    private void init(){
        score = 0;
        searchedValue = 1 + (int) (Math.random() * 100);
        Log.i("DEBUG", "Valeur cherchée : " + searchedValue);

        idTxtNumber.setText("");
        idProgressBar.setProgress(score);
        lblResult.setText("");
        idTxtHistory.setText("");

        idTxtNumber.requestFocus();
    }

    // Gestionnaire d'événement qui réagit au click
    // La classe mère de tous les comportements et de l'interface graphique est View
    // OnClickListener est une interface d'écoute implémentée, elle est déclarée sur la classe View
    // Après le signe égal, c'est la valeur d'initialisation de l'attribut btnValiderListener

    // L'interface OnClickListenber est portée par View et fournit la méthode onClick (qu'il faut obligatoirement surcharger)
    private View.OnClickListener btnValiderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("DEBUG", "Bouton cliqué");

            String TxtNumber = idTxtNumber.getText().toString();
            // Return = Break
            if(TxtNumber.equals("")){
                return;
            }

            idTxtHistory.append(TxtNumber + "\r\n");
            idProgressBar.incrementProgressBy(1);
            score ++;

            int enteredValue = Integer.parseInt(TxtNumber);
            if(enteredValue == searchedValue){
                congratulation();
            }else if(enteredValue < searchedValue){
                lblResult.setText(R.string.plusGrand);
            }else{
                lblResult.setText(R.string.plusPetit);
            }


            idTxtNumber.setText("");
            idTxtNumber.requestFocus();



        }
    };

    private void congratulation(){
        idTxtHistory.setText("Félicitations");

        // La sous classe Builder permet de construire in builder à partir de notre activité
        AlertDialog retryAlert = new AlertDialog.Builder(this).create();
        retryAlert.setTitle(R.string.app_name);
        retryAlert.setMessage(getString(R.string.messageFelicitation, score));

        // Bouton oui, nouvelle partie
        // Au nouveau gestionnaire d'événement, l'interface OnClickListener estr portée par AlertDialog
        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, "@string/strYes", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        });

        // Bouton non, on arrête
        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "@string/strNo", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        retryAlert.show();

    }

}
