package eg.foureg.freedraw.ui.boards.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eg.foureg.freedraw.R


class BoardsRecyclerAdapter(val context: Context, val itemsList: ArrayList<String>, val listener : BoardsListingFragment) :
    RecyclerView.Adapter<BoardsRecyclerAdapter.BoardsListViewHolder>() {

    /**
     * Adapte View Holder
     */
    class BoardsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView
        init {
            nameTextView = itemView.findViewById<View>(R.id.board_listing_item_title_text_view) as TextView
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardsListViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.board_listing_recycler_item, null)

        return BoardsListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: BoardsListViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.itemSelected(position)
        }

        holder.nameTextView.text = itemsList[position]
    }

}