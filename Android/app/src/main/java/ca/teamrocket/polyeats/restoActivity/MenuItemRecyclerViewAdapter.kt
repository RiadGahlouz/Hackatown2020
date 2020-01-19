package ca.teamrocket.polyeats.restoActivity

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.models.FullMenuItem


import ca.teamrocket.polyeats.restoActivity.CheckoutFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_menuitem.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MenuItemRecyclerViewAdapter(
    private val mValues: List<FullMenuItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MenuItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as FullMenuItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_menuitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mQtView.text = "1"
        holder.mNameView.text = item.name
        holder.mPriceView.text = item.price
        holder.mOptionView.text = item.options
        holder.mSpecsView.text = item.specs

        //holder.mContentView.text = item.content

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mQtView: TextView = mView.qtVal
        val mNameView: TextView = mView.foodName
        val mPriceView: TextView = mView.price
        val mOptionView: TextView = mView.options
        val mSpecsView: TextView = mView.specs

        override fun toString(): String {
            return super.toString() + " '" + mNameView.text + "'"
        }
    }
}
