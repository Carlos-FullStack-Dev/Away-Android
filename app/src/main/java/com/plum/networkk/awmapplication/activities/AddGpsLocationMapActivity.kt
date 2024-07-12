package com.plum.networkk.awmapplication.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.databinding.ActivityAddGpsLocationMapActivityBinding


class AddGpsLocationMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signin)
        var vBinding = ActivityAddGpsLocationMapActivityBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        val sydney = LatLng((-34).toDouble(), (151).toDouble())
        mMap!!.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker")
        )
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}