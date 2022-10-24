package com.ielts.preparation.ui.translate

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentTraslateBottomSheetBinding
import com.ielts.preparation.utils.Progress


class TranslateBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTraslateBottomSheetBinding
    private val args: TranslateBottomSheetFragmentArgs by navArgs()
    private var progress: Progress? = null
    private var isLoaded: Boolean = false
    private var urll: String? = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTraslateBottomSheetBinding.inflate(layoutInflater)
        this.isCancelable = false
        binding.wvGoogleTranslator.apply {
            true.also {
                settings.javaScriptEnabled = it
                settings.allowFileAccess = it
                settings.domStorageEnabled = it
                settings.javaScriptCanOpenWindowsAutomatically = it
                settings.supportMultipleWindows()
            }
        }
        progress = Progress(requireContext(), R.string.please_wait)
        urll = args.url

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isOnline()) {
            showToast(getString(R.string.no_internet))
            binding.tvHeading.text = getString(R.string.no_internet)
            val snack =
                dialog?.window?.let {
                    Snackbar.make(it.decorView,
                        getString(R.string.enable_wifi),
                        Snackbar.LENGTH_INDEFINITE)
                }
            snack?.setAction(getString(R.string.settings)) {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                activity?.onBackPressed()
            }
            snack?.show()
        } else if (isOnline() && !isLoaded) loadWebView()

        binding.btnDialogDismiss.setOnClickListener {
            activity?.onBackPressed()

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if (isOnline() && !isLoaded) loadWebView()
    }

    private fun loadWebView() {
        showProgress(true)
        binding.tvHeading.text = args.heading
        urll?.let {
            binding.wvGoogleTranslator.apply {
                loadUrl(it)
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        val url = request?.url.toString()
                        view?.loadUrl(url)
                        return true
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        showProgress(true)
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        isLoaded = true
                        showProgress(false)
                        super.onPageFinished(view, url)
                    }

                    override fun onReceivedError(
                        view: WebView,
                        request: WebResourceRequest,
                        error: WebResourceError,
                    ) {
                        isLoaded = false
                        val errorMessage = "Got Error! $error"
                        showToast(errorMessage)
                        binding.tvHeading.text = errorMessage
                        showProgress(false)
                        super.onReceivedError(view, request, error)
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun showProgress(visible: Boolean) {
        progress?.apply { if (visible) show() else dismiss() }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}