package com.example.mini_projet.views.fragement

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.ID
import com.example.mini_projet.views.PICTURE
import com.example.mini_projet.views.PREF_NAME
import com.example.mini_projet.views.adapter.SearchAdapter
import com.example.mini_projet.views.adapter.UserAdapter
import kotlinx.android.synthetic.main.accueil.*
import kotlinx.android.synthetic.main.fragement_subs.*
import kotlinx.android.synthetic.main.fragementsearch.*
import kotlinx.android.synthetic.main.searchtoolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragementsubs : Fragment(R.layout.subsfrag), TextWatcher {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragement_subs, container, false)
        refresh(context)
        return rootView

    }
    fun refresh(context: Context?)
    {
        context?.let {
            val fragementManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragementManager?.let {
                val currentFragement = fragementManager.findFragmentById(R.id.container)
                currentFragement?.let {
                    val fragmentTransaction = fragementManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search_text.addTextChangedListener(this)
        /*mSharedPref =  requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val apiInterface = ApiInterface.create()
        apiInterface.getwithuser(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<MutableList<User>> {

            override fun onResponse(call: Call<MutableList<User>>, response:
            Response<MutableList<User>>
            ) {
                if(response.body() == null)
                {
                    NoUser.visibility = View.VISIBLE
                }
                else
                {
                    val  user = response.body() as MutableList<User>
                    if(user !=null)
                    {

                        viewAdapter = UserAdapter(user)
                        viewManager = LinearLayoutManager(context)

                        colorDrawableBackground = ColorDrawable(Color.parseColor("#ff0000"))
                        deleteIcon = context!!.getDrawable(R.drawable.ic_delete)!!
                        rv_users.apply {
                            setHasFixedSize(true)
                            viewAdapter.notifyDataSetChanged()
                            adapter = viewAdapter
                            layoutManager = viewManager
                            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))


                            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder2: RecyclerView.ViewHolder): Boolean {
                                    return false
                                }

                                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                                    (viewAdapter as UserAdapter).removeItem(viewHolder.adapterPosition, viewHolder)
                                }

                                override fun onChildDraw(
                                    c: Canvas,
                                    recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    dX: Float,
                                    dY: Float,
                                    actionState: Int,
                                    isCurrentlyActive: Boolean
                                ) {
                                    val itemView = viewHolder.itemView
                                    val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                                    if (dX > 0) {
                                        colorDrawableBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                                        deleteIcon.setBounds(itemView.left + iconMarginVertical, itemView.top + iconMarginVertical,
                                            itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth, itemView.bottom - iconMarginVertical)
                                    } else {
                                        colorDrawableBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                                        deleteIcon.setBounds(itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth, itemView.top + iconMarginVertical,
                                            itemView.right - iconMarginVertical, itemView.bottom - iconMarginVertical)
                                        deleteIcon.level = 0
                                    }

                                    colorDrawableBackground.draw(c)

                                    c.save()

                                    if (dX > 0)
                                        c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                                    else
                                        c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                                    deleteIcon.draw(c)

                                    c.restore()

                                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                }
                            }

                            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
                            itemTouchHelper.attachToRecyclerView(rv_users)

                            viewAdapter.notifyDataSetChanged()
                        }

                    }
                    else
                    {
                        NoUser.visibility = View.VISIBLE
                    }
                }




            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                Log.e("error","error")
            }
        })*/
        super.onViewCreated(view, savedInstanceState)
    }
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        val string = p0.toString().trim { it <= ' ' }
        if (string.isEmpty()) {
            resetSearchEdit()
        } else {
            searchuserempty.visibility = View.GONE
            rv_users.visibility = View.VISIBLE
            val map: HashMap<String, String> = HashMap()
            map["username"] = search_text.text.toString()
            val apiInterface = ApiInterface.create()
            apiInterface.getByName(map).enqueue(object : Callback<MutableList<User>> {
                override fun onResponse(call: Call<MutableList<User>>, response:
                Response<MutableList<User>>
                ) {
                    val  user = response.body() as MutableList<User>
                    if(!user.isEmpty())
                    {
                        Log.e("users",user.toString())
                        val adapter = UserAdapter(user)
                        rv_users.adapter = adapter
                        rv_users.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                    else
                    {
                        searchuserempty.visibility = View.VISIBLE
                        rv_users.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                    Log.e("error","error")
                }
            })
        }
    }

    private fun resetSearchEdit() {
        search_text.removeTextChangedListener(this)
        search_text.setText("")
        search_text.addTextChangedListener(this)
    }
}