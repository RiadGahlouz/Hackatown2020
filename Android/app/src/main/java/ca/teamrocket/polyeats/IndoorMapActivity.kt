package ca.teamrocket.polyeats

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.Style.MAPBOX_STREETS
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.ColorUtils
import kotlinx.android.synthetic.main.activity_indoor_map.*
import java.io.IOException
import java.nio.charset.Charset
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.os.Looper
import com.google.android.gms.location.*


class IndoorMapActivity : AppCompatActivity() {
    private lateinit var indoorBuildingSource: GeoJsonSource
    private lateinit var boundingBoxList: MutableList<MutableList<Point>>
    private lateinit var circleManager: CircleManager
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    fun requestPermission(strPermission: String, _c: Context, _a: Activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(_a, strPermission)) {
            Toast.makeText(
                applicationContext,
                "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {

            ActivityCompat.requestPermissions(_a, arrayOf(strPermission), 1)
        }
    }

    fun checkPermission(strPermission: String, _c: Context, _a: Activity): Boolean {
        val result = ContextCompat.checkSelfPermission(_c, strPermission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun getColorFromAltitude(altitude: Double, accuracy: Float): Int {
        val POLY_RED = Color.argb(255, 185, 30, 50)
        val POLY_ORANGE = Color.argb(255,250, 150,30)
        val POLY_GREEN = Color.argb(255,140,200,60)
        val POLY_BLUE = Color.argb(255,65,170,230)
        // 102 POLY_RED
        // 105 POLY_RED
        // 109 POLY_ORANGE
        // 112 POLY_ORANGE
        // 117 POLY_GREEN
        // 120 POLY_GREEN - POLY_BLUE
        // 120-123 POLY_BLUE

        /*
        < 104 -> POLY_RED
        > 107 -> POLY_ORANGE
        > 115 -> POLY_GREEN
        > 119 -> POLY_BLUE
         */
        return when (altitude) {
            in 90.0..107.0 -> POLY_RED
            in 107.0..115.0 -> POLY_ORANGE
            in 115.0..119.5 -> POLY_GREEN
            in 119.5..130.0 -> POLY_BLUE
            else -> Color.YELLOW
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, applicationContext,this)) {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,applicationContext,this);
            }

            if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, applicationContext,this)) {
                requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION,applicationContext,this);
            }

        val latPoly = 45.504750
        val lonPoly = -73.614749

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create()?.apply {
            interval = 4000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                val circleOptions = CircleOptions()
                    .withLatLng(LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude))
                    .withCircleColor(ColorUtils.colorToRgbaString(getColorFromAltitude(locationResult.lastLocation.altitude, locationResult.lastLocation.verticalAccuracyMeters)))
                    .withCircleRadius(8f)
                    .withDraggable(false)
                circleManager.deleteAll()
                circleManager.create(circleOptions)
                println("LOCATION DETERMINED to ${locationResult.lastLocation.latitude}  ${locationResult.lastLocation.longitude}")
                deviceHeight.text = "Alt: ${locationResult.lastLocation.altitude} Acc: ${locationResult.lastLocation.verticalAccuracyMeters} Speed: ${locationResult.lastLocation.speed}"
            }
        }



//        fusedLocationClient.lastLocation.addOnSuccessListener {
//            val circleOptions = CircleOptions()
//                .withLatLng(LatLng(it.latitude, it.longitude))
//                .withCircleColor(ColorUtils.colorToRgbaString(Color.YELLOW))
//                .withCircleRadius(6f)
//                .withDraggable(false)
//            circleManager.deleteAll()
//            circleManager.create(circleOptions)
//            println("LOCATION DETERMINED to ${it.latitude}  ${it.longitude}")
//        }

        setContentView(R.layout.activity_indoor_map)
        mapViewElement.onCreate(savedInstanceState)
        mapViewElement.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(MAPBOX_STREETS) { style ->
                circleManager = CircleManager(mapViewElement, mapboxMap, style)
                fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper())
                // Map is set up and the style has loaded. Now you can add data or make other map adjustments.
                val boundingBox = ArrayList<Point>()

                boundingBox.add(Point.fromLngLat(-77.03791, 38.89715))
                boundingBox.add(Point.fromLngLat(-77.03791, 38.89811))
                boundingBox.add(Point.fromLngLat(-77.03532, 38.89811))
                boundingBox.add(Point.fromLngLat(-77.03532, 38.89708))
                boundingBoxList = ArrayList()
                boundingBoxList.add(boundingBox)
                // create a fixed circle
//                mapboxMap.addOnCameraMoveListener {
//                    if (mapboxMap.getCameraPosition().zoom > 16) {
//                        if (TurfJoins.inside(Point.fromLngLat(mapboxMap.getCameraPosition().target.getLongitude(),
//                                mapboxMap.getCameraPosition().target.getLatitude()), Polygon.fromLngLats(boundingBoxList))) {
//                            if (levelButtons.getVisibility() != View.VISIBLE) {
//                                showLevelButton();
//                            }
//                        } else {
//                            if (levelButtons.getVisibility() == View.VISIBLE) {
//                                hideLevelButton();
//                            }
//                        }
//                    } else if (levelButtons.getVisibility() == View.VISIBLE) {
//                        hideLevelButton();
//                    }
//                }
                indoorBuildingSource = GeoJsonSource(
                    "indoor-building", loadJsonFromAsset("white_house_lvl_0.geojson")
                )

                style.addSource(indoorBuildingSource)

                // Add the building layers since we know zoom levels in range
                loadBuildingLayer(style)
                }
            }
        }
    private fun loadBuildingLayer(style: Style) {
        // Method used to load the indoor layer on the map. First the fill layer is drawn and then the
        // line layer is added.

        val indoorBuildingLayer =
            FillLayer("indoor-building-fill", "indoor-building").withProperties(
                fillColor(Color.parseColor("#eeeeee")),
                // Function.zoom is used here to fade out the indoor layer if zoom level is beyond 16. Only
                // necessary to show the indoor map at high zoom levels.
                fillOpacity(
                    interpolate(
                        exponential(1f), zoom(),
                        stop(16f, 0f),
                        stop(16.5f, 0.5f),
                        stop(17f, 1f)
                    )
                )
            )

        style.addLayer(indoorBuildingLayer)

        val indoorBuildingLineLayer =
            LineLayer("indoor-building-line", "indoor-building").withProperties(
                lineColor(Color.parseColor("#50667f")),
                lineWidth(0.5f),
                lineOpacity(
                    interpolate(
                        exponential(1f), zoom(),
                        stop(16f, 0f),
                        stop(16.5f, 0.5f),
                        stop(17f, 1f)
                    )
                )
            )
        style.addLayer(indoorBuildingLineLayer)
    }

    private fun loadJsonFromAsset(filename: String): String? {
        // Using this method to load in GeoJSON files from the assets folder.

        try {
            val `is` = assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            return String(buffer, Charset.forName("UTF-8"))

        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

    }
    override fun onStart() {
        super.onStart()
        mapViewElement.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapViewElement.onResume()
    }

    override fun onStop() {
        super.onStop()
        mapViewElement.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewElement.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewElement.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            mapViewElement.onSaveInstanceState(outState)
        }
    }

}
