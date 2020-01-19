package ca.teamrocket.polyeats.searchFragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.models.MenuItem


import ca.teamrocket.polyeats.searchFragment.SearchFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_suggestion.view.*

/**
 * [RecyclerView.Adapter] that can display a [Suggestion] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class SuggestionRecyclerViewAdapter(
    private val mValues: List<MenuItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<SuggestionRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MenuItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
