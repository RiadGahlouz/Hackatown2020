package ca.teamrocket.polyeats

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.teamrocket.polyeats.historyFragment.HistoryFragment
import ca.teamrocket.polyeats.historyFragment.transaction.TransactionContent
import ca.teamrocket.polyeats.network.models.Resto
import ca.teamrocket.polyeats.network.models.Suggestion
import ca.teamrocket.polyeats.restoFragment.RestoFragment
import ca.teamrocket.polyeats.searchFragment.SearchFragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.mapboxsdk.Mapbox
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(),
    HistoryFragment.OnListFragmentInteractionListener,
    RestoFragment.OnListFragmentInteractionListener,
    SearchFragment.OnListFragmentInteractionListener{

    lateinit var requestQueue: RequestQueue
//    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onListFragmentInteraction(item: Suggestion?) {
        Log.d("SEARCH", "click")
    }

    override fun onListFragmentInteraction(item: Resto?) {
        Log.d("RESTO", "click")
    }

    override fun onListFragmentInteraction(item: TransactionContent.TransactionItem?) {
        Log.d("HISTORY", "click")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Mapbox.getInstance(this, "pk.eyJ1IjoidWx0aW1hdGVnYW1lcjg0IiwiYSI6ImNrNWszcGZmNTBhM3Yza3FsMmhhY3RvbjEifQ.yvR1ICDa2Hn6z05setMxrQ")
        val intent = Intent(this, IndoorMapActivity::class.java).apply {}
        startActivity(intent)

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
