package eg.foureg.freedraw.ui.boards.listing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eg.foureg.freedraw.R
import eg.foureg.freedraw.ui.BaseActorFragment
import eg.foureg.freedraw.ui.MainActivity
import kotlinx.android.synthetic.main.boards_listing_fragment.*

class BoardsListingFragment : BaseActorFragment() {

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

        viewModel.notifyLoadingItemsFinished.observe(viewLifecycleOwner, Observer {

            val adapter = BoardsRecyclerAdapter(activity as Context, viewModel.boardsNamesList)
            boards_listing_list_view.adapter = adapter
        })

        boards_listing_list_view.layoutManager = LinearLayoutManager(activity)

        boards_listing_open_editor_btn.setOnClickListener {
            (activity as MainActivity).navigator.navigateToBoardEditorFragment(activity as MainActivity, null)
        }

        viewModel.initViewModel(activity as Context)



    }

}
