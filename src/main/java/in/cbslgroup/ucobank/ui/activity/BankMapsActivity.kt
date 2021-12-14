package `in`.cbslgroup.ucobank.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.ucobank_module.R

class BankMapsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_maps)

      /*  val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.maps_frag_view, MapsFragment())
        ft.commit()*/

    }
}