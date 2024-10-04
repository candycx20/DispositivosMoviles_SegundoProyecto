package com.example.googlemapsapi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
        Places.initialize(applicationContext, "TU_GOOGLE_MAPS_API_KEY")

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el fragmento del mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        // Botón de búsqueda por coordenadas (latitud y longitud)
        val latEditText: EditText = findViewById(R.id.latEditText)
        val lngEditText: EditText = findViewById(R.id.lngEditText)
        val btnSearch: Button = findViewById(R.id.btnSearch)

        btnSearch.setOnClickListener {
            val lat = latEditText.text.toString().toDoubleOrNull()
            val lng = lngEditText.text.toString().toDoubleOrNull()

            if (lat != null && lng != null) {
                val latLng = LatLng(lat, lng)
                mMap.addMarker(MarkerOptions().position(latLng).title("Marker at (${lat}, ${lng})"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            } else {
                // Manejar el caso donde las coordenadas no son válidas
                Log.e("MapsActivity", "Invalid coordinates")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Puedes iniciar el mapa en una ubicación específica si deseas
        val initialLocation = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(initialLocation).title("Initial Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialLocation))
    }
}
