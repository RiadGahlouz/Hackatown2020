package ca.teamrocket.polyeats.searchFragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.teamrocket.polyeats.MainActivity
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.Backend
import ca.teamrocket.polyeats.network.models.MenuItem
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SearchFragment.OnListFragmentInteractionListener] interface.
 */
class SearchFragment : Fragment() {

    private var columnCount = 1
    private val suggestions: MutableList<MenuItem> = ArrayList()
    private val filtered: MutableList<MenuItem> = ArrayList()
    private var listener: OnListFragmentInteractionListener? = null
    internal var textlength = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backend.getAllMenuItems((activity as MainActivity).requestQueue, ::populateSuggestions)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    private fun populateSuggestions(listMenuItems:List<MenuItem>?) {
        if(listMenuItems==null) {
            Log.d("ERROR", "AUCUN ITEM DANS LA DB")
            return
        }

        suggestions.addAll(listMenuItems)
        filtered.addAll(listMenuItems)
        list.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Set the adapter
        if (view is ConstraintLayout) {
            with(view.list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = SuggestionRecyclerViewAdapter(suggestions, listener)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textlength = search!!.text.length
                filtered.clear()
                for (i in suggestions.indices) {
                    if (textlength <= suggestions[i].name?.length ?: 0) {
                        if (suggestions[i].name?.toLowerCase()?.trim()?.contains(
                                search!!.text.toString().toLowerCase().trim { it <= ' ' })!!
                        ) {
                            filtered.add(suggestions[i])
                        }
                    }
                }
                list.adapter = SuggestionRecyclerViewAdapter(filtered, listener)
                list!!.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            }
        })
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
        fun onListFragmentInteraction(item: MenuItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
