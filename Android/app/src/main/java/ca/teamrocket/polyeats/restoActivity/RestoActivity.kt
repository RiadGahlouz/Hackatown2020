package ca.teamrocket.polyeats.restoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.network.models.FullMenuItem
import ca.teamrocket.polyeats.network.models.MenuItem
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

import kotlinx.android.synthetic.main.fragment_popup_menu_item.*




class RestoActivity : AppCompatActivity(), FoodOptionFragment.OnListFragmentInteractionListener, CheckoutFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: FullMenuItem?) {
        Log.d("CHECKOUT", "click")
    }

    var order: MutableList<FullMenuItem> = ArrayList()
    lateinit var requestQueue: RequestQueue
    private var checkoutFragment: CheckoutFragment = CheckoutFragment()
    private val GSON = Gson()

    override fun onListFragmentInteraction(item: MenuItem?) {
        if (item == null) return
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.fragment_popup_menu_item, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle(item.name)
        val mAlertDialog = mBuilder.show()

        mAlertDialog.cancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mAlertDialog.addToCartBtn.setOnClickListener {
            var options = ""
            if(mAlertDialog.extra1.isChecked)
                options += "Extra Sauce, "
            if(mAlertDialog.extra2.isChecked)
                options += "Extra Cheese, "
            if(mAlertDialog.extra3.isChecked)
                options += "No Meat, "

            options = options.take(options.length - 2)

            val fmi = FullMenuItem()
            fmi.id = item.id
            fmi.price = "10.00$"
            fmi.options = options
            fmi.name = item.name
            fmi.specs = mAlertDialog.specsVal.text.toString()

            order.add(fmi)
            mAlertDialog.dismiss()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = Volley.newRequestQueue(this)
        //.navigationItem.title = @"The title"
        // activity?.intent?.getSerializableExtra("Resto") as Resto
        setContentView(R.layout.activity_resto)

        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.host_fragment, FoodOptionFragment())
        fragmentTransaction.commit()
        }

    fun swapFrag(){
        val bundle = Bundle()
        bundle.putString("order", GSON.toJson(order))
        checkoutFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.host_fragment, checkoutFragment, "checkout")
            .addToBackStack(null)
            .commit()
    }
    }
