package com.example.mini_projet.views.fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mini_projet.R
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utilis.ApiInterface
import com.example.mini_projet.views.adapter.RestoAdapter
import kotlinx.android.synthetic.main.fragementresto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.*
import android.widget.Toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class fragementresto : Fragment(R.layout.fragementresto) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView: View = inflater.inflate(R.layout.fragementresto, container, false)


        return rootView

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progBar.visibility = View.VISIBLE
        val apiInterface = ApiInterface.create()
        apiInterface.findAllResto().enqueue(object :
            Callback<MutableList<Restaurant>> {

            override fun onResponse(call: Call<MutableList<Restaurant>>, response:
            Response<MutableList<Restaurant>>
            ) {
                val restos = response.body()
                if(restos != null)
                {


                    val adapter = RestoAdapter(restos)
                    rv_resto.adapter = adapter
                    rv_resto.apply {
                        layoutManager = GridLayoutManager(view.context, 2)
                    }
                }
                else{
                    Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()
                }
                progBar.visibility = View.INVISIBLE
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<MutableList<Restaurant>>, t: Throwable) {
                Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()
                progBar.visibility = View.INVISIBLE
                requireActivity().window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        })


        super.onViewCreated(view, savedInstanceState)
    }

}