package eg.foureg.freedraw.ui.dialogs.boardsselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eg.foureg.freedraw.R

class BoardsSelectionRecyclerAdapter(val context: Context,
                                     private val itemsList: ArrayList<String>,
                                     private val listener : BoardsSelectionDialogActivity) :
    RecyclerView.Adapter<BoardsSelectionRecyclerAdapter.BoardsSelectionListViewHolder>() {

    /**
     * Adapte View Holder
     */
    class BoardsSelectionListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.board_listing_item_title_text_view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BoardsSelectionListViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.board_listing_recycler_item, null)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp

        return BoardsSelectionListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: BoardsSelectionListViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.itemSelected(position)
        }

        holder.nameTextView.text = itemsList[position]


    }
}