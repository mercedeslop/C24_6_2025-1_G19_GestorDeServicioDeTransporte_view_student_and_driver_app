package com.jennyfer.sapallanay.view_student_app.ui.view.passenger

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.jennyfer.sapallanay.view_student_app.databinding.FragmentQrBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class QRFragment : Fragment() {

    private var _binding: FragmentQrBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(nombreRuta: String, paradero: String): QRFragment {
            val fragment = QRFragment()
            val args = Bundle().apply {
                putString("ruta", nombreRuta)
                putString("paradero", paradero)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        // Ocultar BottomNavigationView
        //requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        //requireActivity().findViewById<View>(R.id.bottom_navigation)?.visibility = View.GONE
        val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
        val ruta = arguments?.getString("ruta").orEmpty()
        val paradero = arguments?.getString("paradero").orEmpty()
        val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            .format(Date())




        // Codificar en JSON
        val qrData = JSONObject().apply {
            put("uid", userId)
            put("ruta", ruta)
            put("paradero", paradero)
            put("timestamp", timestamp)
        }.toString()


        // Generar el código QR
        binding.qrImageView.setImageBitmap(generateQR(qrData))

        // Botón Volver
        binding.btnVolver.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Botón retroceso superior
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun generateQR(data: String): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}