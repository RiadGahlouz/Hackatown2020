package ca.teamrocket.polyeats.restoActivity

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.models.MenuItem


import ca.teamrocket.polyeats.restoActivity.FoodOptionFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_foodoption.view.*

/**
 * [RecyclerView.Adapter] that can display a [MenuItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class FoodOptionRecyclerViewAdapter(
    private val mValues: List<MenuItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<FoodOptionRecyclerViewAdapter.ViewHolder>() {

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
            .inflate(R.layout.fragment_foodoption, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mFoodNameView.text = item.name
        holder.mFoodPriceView.text = "9.99$"
        holder.mFoodImgView.isVisible = true

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mFoodNameView: TextView = mView.foodName
        val mFoodPriceView: TextView = mView.foodPrice
        val mFoodImgView: ImageView = mView.foodImg

        override fun toString(): String {
            return super.toString() + " '" + mFoodNameView.text + "'"
        }
    }
}
