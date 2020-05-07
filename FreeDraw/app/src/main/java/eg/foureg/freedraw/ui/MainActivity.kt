package eg.foureg.freedraw.ui

import android.os.Bundle
import eg.foureg.freedraw.R
import eg.foureg.freedraw.common.actor.ActorMessage
import eg.foureg.freedraw.common.actor.ActorMessageDispatcher
import eg.foureg.freedraw.data.messageBackToFragment
import eg.foureg.freedraw.data.messageNavigateToBoardsListFragmentID
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment

class MainActivity : BaseActorActivity() {

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
        }
    }

    val navigator : MainAcNavigator = MainAcNavigator()
}
