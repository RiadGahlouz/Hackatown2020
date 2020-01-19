package ca.teamrocket.polyeats.restoFragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.models.Resto


import ca.teamrocket.polyeats.restoFragment.RestoFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_resto.view.*

/**
 * [RecyclerView.Adapter] that can display a [Resto] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class RestoRecyclerViewAdapter(
    private val mValues: List<Resto>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<RestoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Resto
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_resto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        val imgID = holder.mImageView.context.resources.getIdentifier("ic_${item.id}" , "drawable", holder.mImageView.context.packageName)
        holder.mImageView.setImageResource(imgID)
        holder.mNameView.text = item.name
        holder.mHourView.text = item.hours
        // todo: in red if outside opening hours
        holder.mPlaceView.text = item.location

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView.restoLogo
        val mNameView: TextView = mView.restoName
        val mHourView: TextView = mView.restoHours
        val mPlaceView: TextView = mView.restoPlace

        override fun toString(): String {
            return super.toString() + " '" + mNameView.text + "'"
        }
    }
}
