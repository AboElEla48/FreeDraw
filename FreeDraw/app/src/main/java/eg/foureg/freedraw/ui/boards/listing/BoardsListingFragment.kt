package eg.foureg.freedraw.ui.boards.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import eg.foureg.freedraw.R
import eg.foureg.freedraw.data.Board
import eg.foureg.freedraw.data.Shape
import eg.foureg.freedraw.ui.MainActivity
import kotlinx.android.synthetic.main.boards_listing_fragment.*

class BoardsListingFragment : Fragment() {

    companion object {
        fun newInstance() = BoardsListingFragment()
    }

    private lateinit var viewModel: BoardsListingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.boards_listing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoardsListingViewModel::class.java)

        boards_listing_open_editor_btn.setOnClickListener {

            (activity as MainActivity).navigator.navigateToBoardEditorFragment(activity as MainActivity, null)
        }

    }

}
