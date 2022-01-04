import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_projet.R
import com.example.mini_projet.models.User
import com.example.mini_projet.utilis.ApiInterface
import kotlinx.android.synthetic.main.fragement_subs.*
import kotlinx.android.synthetic.main.fragementsearch.*
import kotlinx.android.synthetic.main.searchtoolbar.*
import kotlinx.android.synthetic.main.searchtoolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.TextView

import android.widget.EditText
import android.widget.TextView.GONE

import android.widget.TextView.OnEditorActionListener
import com.example.mini_projet.views.adapter.SearchAdapter
import kotlinx.android.synthetic.main.accueil.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragementsearch.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragementsearch : Fragment(R.layout.fragementsearch), TextWatcher {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragementsearch, container, false)


        return rootView

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search_text.addTextChangedListener(this)

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
            searchempty.visibility = View.GONE
            rv_search_users.visibility = View.VISIBLE
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
                        val adapter = SearchAdapter(user)
                        rv_search_users.adapter = adapter
                        rv_search_users.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                    else
                    {
                        searchempty.visibility = View.VISIBLE
                        rv_search_users.visibility = View.GONE
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