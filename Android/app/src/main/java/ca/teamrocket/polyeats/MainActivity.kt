package ca.teamrocket.polyeats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.teamrocket.polyeats.historyFragment.HistoryFragment
import ca.teamrocket.polyeats.network.Backend
import ca.teamrocket.polyeats.network.models.MenuItem
import ca.teamrocket.polyeats.network.models.Order
import ca.teamrocket.polyeats.network.models.Resto
import ca.teamrocket.polyeats.restoActivity.RestoActivity
import ca.teamrocket.polyeats.restoFragment.RestoFragment
import ca.teamrocket.polyeats.searchFragment.SearchFragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.animation.AlphaAnimation
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import com.mapbox.mapboxsdk.Mapbox


class MainActivity : AppCompatActivity(),
    HistoryFragment.OnListFragmentInteractionListener,
    RestoFragment.OnListFragmentInteractionListener,
    SearchFragment.OnListFragmentInteractionListener{

    lateinit var requestQueue: RequestQueue
    var restoId = "-1"
    var currResto: Resto? = null
    //private val buttonClick = AlphaAnimation(1f, 0.8f)
//    lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onListFragmentInteraction(item: MenuItem?) {
        // startAnimation(buttonClick)
        restoId = item?.id_resto.toString()
        Backend.getRestos(requestQueue, ::startRestoActivity)
    }

    private fun startRestoActivity(listRestos:List<Resto>?){
        if(restoId.toInt() >= 0) {
            currResto = listRestos?.get(restoId.toInt())
            val intent = Intent(this, RestoActivity::class.java).apply {
                putExtra("Resto", currResto)
            }
            startActivity(intent)
        }
    }

    override fun onListFragmentInteraction(item: Resto?) {
        val intent = Intent(this, RestoActivity::class.java).apply {
            putExtra("Resto", item)
        }
        startActivity(intent)
    }

    override fun onListFragmentInteraction(item: Order?) {
        Log.d("HISTORY", "click")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide()
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Mapbox.getInstance(this, "pk.eyJ1IjoidWx0aW1hdGVnYW1lcjg0IiwiYSI6ImNrNWszcGZmNTBhM3Yza3FsMmhhY3RvbjEifQ.yvR1ICDa2Hn6z05setMxrQ")
        if(IndoorMapActivity.AM_I_THE_DELIVERY_GUY) {
            val intent = Intent(this, IndoorMapActivity::class.java).apply {}
            startActivity(intent)
        }

        requestQueue = Volley.newRequestQueue(this)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each`
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_resto, R.id.navigation_search, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
