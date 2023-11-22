package ignacio.campillos.androidstudio2_example_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ignacio.campillos.androidstudio2_example_sharedpreferences.configuracion.Constantes;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnGuardar;
    private Button btnBorrarTodos;
    private Button btnJSON;

    private ImageButton btnBorrarNombre;
    private ImageButton btnBorrarEdad;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVista();

        sp = getSharedPreferences(Constantes.PERSONA,MODE_PRIVATE); //Crear nuevo archivo SharedPreferences con nombre persona y modo privado
        //para que nadie pueda acceder a el

        rellenarDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNombre.getText().toString().isEmpty()|| txtEdad.getText().toString().isEmpty()){

                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_LONG).show();

                }else {

                    String nombre = txtNombre.getText().toString();
                    int edad = Integer.parseInt(txtEdad.getText().toString());

                    SharedPreferences.Editor editor = sp.edit(); //Crear un editor de SharedPreferences para editar sp (persona)
                    editor.putString(Constantes.NOMBRE,nombre); //Poner un String en sp con ID nombre y con la variable nombre (txtNombre)
                    editor.putInt(Constantes.EDAD,edad);

                    editor.apply(); //Push a los cambios
                    // editor.commit(); -> Lo mismo pero hasta que no se guarde no sigue el programa
                }

            }
        });

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,JSONActivity.class));
            }
        });

        btnBorrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear(); //Borra toda la informacion del fichero sp persona
                editor.apply(); //Aplicar cambios
                txtNombre.setText("");
                txtEdad.setText("");
            }
        });

        btnBorrarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.NOMBRE); // Borrar del fichero sp persona el dato guardado nombre
                editor.apply();
                txtNombre.setText("");
            }
        });

        btnBorrarEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constantes.EDAD);
                editor.apply();
                txtEdad.setText("");
            }
        });
    }

    private void rellenarDatos() {

        String nombre = sp.getString(Constantes.NOMBRE,""); //Para coger los datos del sp, se usa un get con el ID del contenido y un valor por defecto

        int edad = sp.getInt(Constantes.EDAD, -1);

        if (!nombre.isEmpty()){
            txtNombre.setText(nombre);
        }
        if (edad != -1){
            txtEdad.setText((String.valueOf(edad)));
        }
    }

    private void inicializarVista() {
        txtNombre = findViewById(R.id.txtNombre_Main);
        txtEdad = findViewById(R.id.txtEdad_Main);
        btnGuardar = findViewById(R.id.btnGuardar_Main);
        btnBorrarEdad = findViewById(R.id.btnBorrarEdadMain);
        btnBorrarNombre = findViewById(R.id.btnBorrarNombreMain);
        btnBorrarTodos = findViewById(R.id.btnBorrarMain);
        btnJSON = findViewById(R.id.btnJSON_Main);
    }
}