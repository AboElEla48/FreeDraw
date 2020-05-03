package eg.foureg.freedraw.ui

import eg.foureg.freedraw.R
import eg.foureg.freedraw.ui.boards.listing.BoardsListingFragment

class MainAcNavigator {

    fun navigateToBoardsListingFragment(activity: MainActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, BoardsListingFragment.newInstance())
            .commitNow()
    }
}