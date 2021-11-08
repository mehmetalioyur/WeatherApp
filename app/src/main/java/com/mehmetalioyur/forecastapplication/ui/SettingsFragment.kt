package com.mehmetalioyur.forecastapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mehmetalioyur.forecastapplication.databinding.FragmentSettingsBinding
import com.mehmetalioyur.forecastapplication.util.Constants.PERMISSION_LOCATION_REQUEST_CODE
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog


class SettingsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var longitude: Double? = null
    private var latitude: Double? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("com.mehmetalioyur.forecastapplication",Context.MODE_PRIVATE)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (hasLocationPermission()) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    latitude = it.result.latitude
                    longitude = it.result.longitude

                    sharedPreferences.edit().putString("latitude", latitude.toString()).apply()
                    sharedPreferences.edit().putString("longitude", longitude.toString()).apply()

                    val action =
                        SettingsFragmentDirections.settingsFragmentToCurrentForecastFragment()
                    findNavController().navigate(action)
                }
                else {
                    sharedPreferences.edit().putString("latitude", "39.922312").apply()
                    sharedPreferences.edit().putString("longitude", "32.8578795").apply()

                    binding.errorLayout.visibility = View.VISIBLE
                    binding.locationLoadingLayout.visibility = View.GONE

                    binding.locationTryAgainButton.setOnClickListener {
                        recreate(requireActivity())
                    }


                }

            }
        } else {
            requestLocationPermission()
        }
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms.first())) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
