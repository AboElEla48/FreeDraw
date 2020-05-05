package eg.foureg.freedraw.ui

import eg.foureg.freedraw.R
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.ui.boards.editor.BoardEditorFragment
import eg.foureg.freedraw.ui.boards.listing.BoardsListingFragment

class MainAcNavigator {

    fun navigateToBoardsListingFragment(activity: MainActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, BoardsListingFragment.newInstance())
            .commitNow()
    }

    fun navigateToBoardEditorFragment(activity: MainActivity, board: Board?) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, BoardEditorFragment.newInstance(board))
            .commitNow()
    }
}