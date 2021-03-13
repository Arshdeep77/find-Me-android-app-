package com.example.findme

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapToFind : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mymap:GoogleMap
    private lateinit var myLatLng: LatLng
    private lateinit var adr:String
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_to_find)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        myLatLng=genLocation()

googleMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        mMap=googleMap
        plotMap()






    }

    private fun plotMap() {




        // Add a marker in Sydney and move the camera
        val sydney = myLatLng

        mMap.addMarker(MarkerOptions().position(sydney).title(adr)).tag="hello"
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16f))


    }

    private fun genLocation():LatLng {
        val intent:Intent=getIntent()
        val longi=intent.getDoubleExtra("longitude",0.0)
        val lat=intent.getDoubleExtra("latitude",0.0)
        adr=intent.getStringExtra("Address").toString()

        var latlon:LatLng=LatLng(lat,longi)
        return latlon
    }

    //overriden functions
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.map_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.map1 -> {
                mMap.mapType=GoogleMap.MAP_TYPE_HYBRID
                plotMap()
                true
            }
            R.id.map2 -> {
                mMap.mapType=GoogleMap.MAP_TYPE_NORMAL
                plotMap()
                true
            }
            R.id.map3-> {
                mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
                plotMap()

                true
            }
            R.id.map4 -> {
                mMap.mapType=GoogleMap.MAP_TYPE_TERRAIN
                plotMap()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
