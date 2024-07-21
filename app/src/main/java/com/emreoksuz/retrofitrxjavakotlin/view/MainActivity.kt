package com.emreoksuz.retrofitrxjavakotlin.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emreoksuz.retrofitrxjavakotlin.R
import com.emreoksuz.retrofitrxjavakotlin.adapter.RecyclerViewAdapter
import com.emreoksuz.retrofitrxjavakotlin.databinding.ActivityMainBinding
import com.emreoksuz.retrofitrxjavakotlin.model.CryptoModel
import com.emreoksuz.retrofitrxjavakotlin.service.CryptoAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.Listener{

    //private val BASE_URL="ENTER BASE_URL"
    private val BASE_URL="https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter:RecyclerViewAdapter?=null
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val layoutManager :RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()

    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){

                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
                            binding.recyclerView.adapter=recyclerViewAdapter
                        }
                    }

                }
            }

        }


        /*
                val service = retrofit.create(CryptoAPI::class.java)
                val call = service.getData()

                call.enqueue(object: Callback<List<CryptoModel>>{
                    override fun onResponse(
                        call: Call<List<CryptoModel>>,
                        response: Response<List<CryptoModel>>
                    ) {
                        if (response.isSuccessful){
                            response.body()?.let {
                                cryptoModels = ArrayList(it)

                                cryptoModels?.let {
                                    recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
                                    binding.recyclerView.adapter=recyclerViewAdapter
                                }


                                for (cryptoModel : CryptoModel in cryptoModels!!){
                                    println(cryptoModel.currency)
                                    println(cryptoModel.price)
                                }

                            }
                        }
                    }

                    override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                        t.printStackTrace()
                    }

                })

                 */
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}