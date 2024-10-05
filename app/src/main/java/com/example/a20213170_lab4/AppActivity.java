package com.example.a20213170_lab4;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private int currentFragmentIndex = 0; // Marcador de posición actual
    private LigasFragment ligasFragment = new LigasFragment();
    private PosicionesFragment posicionesFragment = new PosicionesFragment();
    private ResultadosFragment resultadosFragment = new ResultadosFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomView);
        frameLayout = findViewById(R.id.frameLayout);

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ligasFragment)
                    .commit();
        }

        //logica de bottomNav
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.navLigas){
                    if (currentFragmentIndex != 0) {  // Solo reemplazar si no está en Inicio
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.swipe_in_right, R.anim.swipe_out_right)
                                .replace(R.id.frameLayout, ligasFragment)
                                .addToBackStack(null)  // Agregar a la pila de retroceso
                                .commit();
                        currentFragmentIndex = 0;  // Actualizar el índice actual del fragmento
                    }
                    return true;
                } else if (itemId == R.id.navPosiciones) {
                    if (currentFragmentIndex == 0) {  // Solo reemplazar si no está en Inicio
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.swipe_in_left, R.anim.swipe_out_left)
                                .replace(R.id.frameLayout, posicionesFragment)
                                .addToBackStack(null)  // Agregar a la pila de retroceso
                                .commit();
                        currentFragmentIndex = 1;  // Actualizar el índice actual del fragmento
                    } else if (currentFragmentIndex == 2) {
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.swipe_in_right, R.anim.swipe_out_right)
                                .replace(R.id.frameLayout, posicionesFragment)
                                .addToBackStack(null)  // Agregar a la pila de retroceso
                                .commit();
                        currentFragmentIndex = 1;  // Actualizar el índice actual del fragmento

                    }
                    return true;
                } else if (itemId == R.id.navResultados) {
                    if (currentFragmentIndex != 2) {  // Solo reemplazar si no está en Inicio
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.swipe_in_left, R.anim.swipe_out_left)
                                .replace(R.id.frameLayout, resultadosFragment)
                                .addToBackStack(null)  // Agregar a la pila de retroceso
                                .commit();
                        currentFragmentIndex = 2;  // Actualizar el índice actual del fragmento
                    }
                    return true;
                }
                cargarFragmento(new LigasFragment(), true);
                return true;
            }
        });

    }

    private void cargarFragmento (Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout, fragment);
        }else{
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();

    }

}