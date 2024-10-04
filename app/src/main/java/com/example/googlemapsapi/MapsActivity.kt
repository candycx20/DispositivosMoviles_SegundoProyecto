package com.example.googlemapsapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemapsapi.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.api.model.Place
import com.google.android.gms.common.api.Status

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la API de Google Places
        Places.initialize(applicationContext, "AIzaSyBpxVtx4_SXgamC12QNHczRqARanmXXQRk")

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el fragmento del mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Inicializa el AutocompleteSupportFragment
        val autocompleteFragment = supportFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Especifica los tipos de datos del lugar que deseas obtener
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Configura el PlaceSelectionListener
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Cuando un lugar es seleccionado, mueve la cámara y coloca un marcador en el mapa
                Log.i("MapsActivity", "Place: ${place.name}, ${place.id}")
                val latLng = place.latLng
                if (latLng != null) {
                    mMap.addMarker(MarkerOptions().position(latLng).title(place.name))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                }
            }

            override fun onError(status: Status) {
                Log.i("MapsActivity", "An error occurred: $status")
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Puedes iniciar el mapa en una ubicación específica si deseas
        val initialLocation = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(initialLocation).title("Initial Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialLocation))
    }
}
