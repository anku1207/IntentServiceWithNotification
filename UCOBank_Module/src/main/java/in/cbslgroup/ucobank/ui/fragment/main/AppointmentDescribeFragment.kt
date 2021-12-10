package `in`.cbslgroup.ucobank.ui.fragment.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentAppointmentDescribeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder


class AppointmentDescribeFragment : Fragment() {
    lateinit var binding: FragmentAppointmentDescribeBinding
    private val args: AppointmentDescribeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         //Disable the optio nmenu
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_appointment_describe,container,false)
        binding.tvAptDescTokenId.setText(args.tokenId)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //generateQrCode("Ankit Roy",binding.ivAptDescQrCode)
        /**
         * Generating the qrcode from given tokenid
         */
        generateQrCode(args.tokenId, binding.ivAptDescQrCode)
        //setting the tokenid
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
       // super.onPrepareOptionsMenu(menu)
        menu.clear()
    }


    fun generateQrCode(text: String, imageView: ImageView) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}