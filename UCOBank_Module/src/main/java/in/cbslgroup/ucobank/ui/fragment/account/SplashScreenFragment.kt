package `in`.cbslgroup.ucobank.ui.fragment.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {


    lateinit var job: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = lifecycleScope.launch {

            delay(2000)

//            val directions = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
//            findNavController().navigate(directions)
            //findNavController().popBackStack()

        }


    }

    override fun onStop() {
        super.onStop()

        job.cancel()

    }


}