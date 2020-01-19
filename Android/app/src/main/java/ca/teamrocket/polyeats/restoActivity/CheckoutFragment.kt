package ca.teamrocket.polyeats.restoActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import ca.teamrocket.polyeats.IndoorMapActivity
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.Backend
import ca.teamrocket.polyeats.network.models.FullMenuItem
import ca.teamrocket.polyeats.network.models.Resto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_menuitem_list.*
import kotlinx.android.synthetic.main.fragment_menuitem_list.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CheckoutFragment.OnListFragmentInteractionListener] interface.
 */
class CheckoutFragment: Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private val GSON = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menuitem_list, container, false)

        // Set the adapter
        if (view is ConstraintLayout) {
            with(view.list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val responseType = object : TypeToken<MutableList<FullMenuItem>>() {}.type
                val order = GSON.fromJson<MutableList<FullMenuItem>>(arguments?.getString("order"), responseType)

                adapter = MenuItemRecyclerViewAdapter(order, listener)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirmBtn.setOnClickListener{
            val intent = Intent(activity, IndoorMapActivity::class.java).apply {}
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: FullMenuItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
