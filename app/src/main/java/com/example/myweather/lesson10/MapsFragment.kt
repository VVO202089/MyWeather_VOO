package com.example.myweather.lesson10

import android.app.AlertDialog
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myweather.R
import com.example.myweather.databinding.FragmentGoogleMapsMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {


    companion object {
        fun newInstance() = MapsFragment()
    }

    private var _binding: FragmentGoogleMapsMainBinding? = null
    private val binding: FragmentGoogleMapsMainBinding
        get() {
            return _binding!!
        }

    lateinit var map: GoogleMap

    private val markers: ArrayList<Marker> = arrayListOf()
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        val startPlace = LatLng(55.0, 37.0)
        map.addMarker(MarkerOptions().position(startPlace).title("Marker Start"))
        map.moveCamera(CameraUpdateFactory.newLatLng(startPlace))
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true

        /*val isPermissionGranted =
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
                    PackageManager.PERMISSION_GRANTED

        map.setMyLocationEnabled(isPermissionGranted)
         */
        map.uiSettings.isMyLocationButtonEnabled = true

        map.setOnMapLongClickListener { location ->
            moveToPosition(location)
            addMarker(location)
            drawLine()
        }
    }

    private fun addMarker(location: LatLng) {
        markers.add(
            map.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
                    .position(location)
                    .title("")
            )
        )
    }

    private fun drawLine() {
        val last = markers.size - 1
        if (last > 0) {
            val startMarker = markers[last - 1].position
            val endMarker = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(startMarker, endMarker)
                    .color(Color.RED)
                    .width(5f)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoogleMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.buttonSearch.setOnClickListener {
            val geocoder = Geocoder(requireContext())
            val addressRow = binding.searchAddress.text.toString()
            if (addressRow.isNotEmpty()) {
                val address = geocoder.getFromLocationName(addressRow, 1)
                if (address.size > 0) {
                    val location = LatLng(address[0].latitude, address[0].longitude)
                    moveToPosition(location)
                } else {
                    // сообщим об ошибке
                    showAlert(addressRow, "Внимание!")
                }
            } else {
                // сообщим об ошибке
                showAlert(addressRow, "Внимание!")
            }

        }
    }

    private fun showAlert(addressRow: String, title: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage("Адрес $addressRow не найден")
            .setPositiveButton("OK") { dialogInterface, i ->
                dialogInterface.cancel()
            }
        builder.create().show()
    }

    private fun moveToPosition(location: LatLng) {
        //map.clear()
        //map.addMarker(MarkerOptions().position(location).title("Marker Start"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}