package com.ielts.preparation.utils


import android.Manifest.permission.INTERNET
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

class RequestPermissionClass {
    fun requestPermission(
        requestPermissionLauncher: ActivityResultLauncher<String>,
        requireActivity: FragmentActivity,
        requireContext: Context
    ) {
        when {
            ActivityCompat.checkSelfPermission(
                requireContext,
                INTERNET
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("checkSelfPermission", "in check self permission")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity,
                INTERNET
            ) -> {
                AlertDialog.Builder(requireContext).apply {
                    setTitle("Request Permission")
                    setMessage("You have to provide Internet permission!")
                    setCancelable(false)
                    setPositiveButton("Grant Permission", DialogInterface.OnClickListener { _, _ ->
                        requestPermissionLauncher.launch(INTERNET)
                    })
                    setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
                        requestPermissionLauncher.launch(INTERNET)
                    })
                    show()
                }
            }
            else -> {
                //PERMISSION IS NOT GRANTED YET
                requestPermissionLauncher.launch(INTERNET)
            }
        }
    }
}