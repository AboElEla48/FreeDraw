package eg.foureg.freedraw.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessage
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.*
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment


class MainActivity : BaseActorActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {

            navigator.navigateToBoardsListingFragment(this)
        }

        // Set a Toolbar to replace the ActionBar.
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        drawer =  findViewById(R.id.drawer_layout);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // The action bar home/up action should open or close the drawer.
        when (item.getItemId()) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateActionBarTitle(title : String) {
        supportActionBar?.title = title
    }

    override fun onBackPressed() {

        if(!ActorMessageDispatcher.sendMessage(BoardEditorFragment::class.java, messageBackToFragment)) {
            super.onBackPressed()
        }

    }

    override fun handleMessage(message: ActorMessage) {
        super.handleMessage(message)

        when(message.what) {
            messageNavigateToBoardsListFragmentID -> {
                navigator.navigateToBoardsListingFragment(this)
            }

            messageNavigateToEditBoardFragmentID -> {
                navigator.navigateToBoardEditorFragment(this,
                    messageNavigateToEditBoardFragmentMap.get(messageNavigateToEditBoardParam) as Board?)
            }
        }
    }

    val navigator : MainAcNavigator = MainAcNavigator()
    lateinit var  drawer : DrawerLayout
}
