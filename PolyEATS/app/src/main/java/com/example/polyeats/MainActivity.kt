package com.example.polyeats

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.polyeats.historyFragment.HistoryFragment
import com.example.polyeats.historyFragment.transaction.TransactionContent
import com.example.polyeats.restoFragment.RestoFragment
import com.example.polyeats.restoFragment.resto.RestoContent
import com.example.polyeats.searchFragment.SearchFragment
import com.example.polyeats.searchFragment.suggestion.SearchContent

class MainActivity : AppCompatActivity(),
    HistoryFragment.OnListFragmentInteractionListener,
    RestoFragment.OnListFragmentInteractionListener,
    SearchFragment.OnListFragmentInteractionListener{
    override fun onListFragmentInteraction(item: SearchContent.SuggestionItem?) {
        Log.d("SEARCH", "click")
    }

    override fun onListFragmentInteraction(item: RestoContent.RestoItem?) {
        Log.d("RESTO", "click")
    }

    override fun onListFragmentInteraction(item: TransactionContent.TransactionItem?) {
        Log.d("HISTORY", "click")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
