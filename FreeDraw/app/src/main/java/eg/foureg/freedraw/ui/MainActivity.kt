package eg.foureg.freedraw.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eg.foureg.freedraw.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {

            navigator.navigateToBoardsListingFragment(this)
        }
    }

    fun updateActionBarTitle(title : String) {
        supportActionBar?.title = title
    }

    val navigator : MainAcNavigator = MainAcNavigator()
}
