package ca.teamrocket.polyeats.restoActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ca.teamrocket.polyeats.R
import ca.teamrocket.polyeats.restoActivity.menu.MenuContent
import kotlinx.android.synthetic.main.fragment_popup_menu_item.*

class RestoActivity : AppCompatActivity(), FoodOptionFragment.OnListFragmentInteractionListener {
    private val order: MutableList<MenuContent.MenuItem> = ArrayList()

    override fun onListFragmentInteraction(item: MenuContent.MenuItem?) {
        if(item == null) return
        val mDialogView  = LayoutInflater.from(this).inflate(R.layout.fragment_popup_menu_item, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle(item?.name)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.cancelBtn.setOnClickListener{
            mAlertDialog.dismiss()
        }
        mAlertDialog.addToCartBtn.setOnClickListener{
            order.add(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resto)
    }
}
