package ignacio.campillos.androidstudio2_example_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ignacio.campillos.androidstudio2_example_sharedpreferences.configuracion.Constantes;
import ignacio.campillos.androidstudio2_example_sharedpreferences.modelos.ContactosMatricula;

public class JSONActivity extends AppCompatActivity {

    private TextView lbResultado;
    private Button btnCargar, btnGuardar;
    private List<ContactosMatricula> listaContactos; //Tiene que ser una lista aunque luego la iniciamos en ArrayList

    private SharedPreferences spContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonactivity);

        spContactos = getSharedPreferences(Constantes.CONTACTOS,MODE_PRIVATE);

        listaContactos = new ArrayList<>();
        inicializarVista();
        crearContactosMatricula();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //IMPORTAR GSON:
                //Desde File -> Project Structure -> Dependences -> Seleccionar APP y de la derecha -> Library Dependences -> com.google.code.gson / search
                
                String datosJSon = new Gson().toJson(listaContactos); //Convertir la lista contactos a un String con formato JSON
                SharedPreferences.Editor editor = spContactos.edit();
                editor.putString(Constantes.DATOS,datosJSon); //Poner los datos String con formato JSON en el archivo con ID datos.
                editor.apply(); //Guardar cambios
            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type tipo = new TypeToken<ArrayList<ContactosMatricula>>(){}.getType(); //Convertir a Type ArrayList de Contactos Matricula

                ArrayList<ContactosMatricula> temp = new Gson().fromJson(spContactos.getString(Constantes.DATOS,"[]"),tipo); //metemos la informacion del JSON de tipo "tipo" (ArrayList de ContactosMatricula)
                //en un arraylist temporal que luego lo meteremos en nuestra lista principal para imprimirla (Lineas de abajo)

                listaContactos.addAll(temp); //Meter lista temporal en lista buena
                lbResultado.setText("Tenemos "+listaContactos.size()+" contactos"); //Mostrar lista buena
            }
        });
    }

    private void crearContactosMatricula() {
        for (int i = 0; i < 10; i++) {
            listaContactos.add(new ContactosMatricula("Nombre: "+i,"Ciclo: "+i, "Email: "+i, "Telefono: "+i));
        }
    }

    private void inicializarVista() {
        lbResultado = findViewById(R.id.lbResultado_JSON);
        btnCargar = findViewById(R.id.btnCargar_JSON);
        btnGuardar = findViewById(R.id.btnGuardar_JSON);
    }
}