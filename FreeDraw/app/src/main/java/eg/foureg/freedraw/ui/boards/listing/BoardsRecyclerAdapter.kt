package eg.foureg.freedraw.ui.boards.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eg.foureg.freedraw.R


class BoardsRecyclerAdapter(val context: Context,
                            private val itemsList: ArrayList<String>,
                            private val listener : BoardsListingFragment,
                            private val isSelectionMode: Boolean) :
    RecyclerView.Adapter<BoardsRecyclerAdapter.BoardsListViewHolder>() {

    /**
     * Adapte View Holder
     */
    class BoardsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.board_listing_item_title_text_view)
        val itemCheckBox: CheckBox = itemView.findViewById(R.id.board_listing_item_selection_check_box)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardsListViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.board_listing_recycler_item, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp

        return BoardsListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: BoardsListViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.itemClicked(position)
        }

        holder.itemView.setOnLongClickListener{
            listener.showSelectionMode()
            true
        }

        holder.nameTextView.text = itemsList[position]

        if(isSelectionMode) {
            holder.itemCheckBox.visibility = View.VISIBLE
        }
    }

}