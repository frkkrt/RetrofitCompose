package com.example.retrofitcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitcompose.Model.CryptoModel
import com.example.retrofitcompose.Service.CryptApi
import com.example.retrofitcompose.ui.theme.FurkanKrt
import com.example.retrofitcompose.ui.theme.RetrofitComposeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeTheme {
                // A surface container using the 'background' color from the theme
                    MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

        var cryptoModels= remember { mutableStateListOf<CryptoModel>() }


        val BASE_URL="https://api.nomics.com/v1/"
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CryptApi::class.java)

    val call=retrofit.getData()
        call.enqueue(object:Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>?,
                response: Response<List<CryptoModel>>
            ) {
              if(response.isSuccessful)
              {
                  response.body()?.let {
                          //List
                          cryptoModels.addAll(it)
                  }
              }
            }

            override fun onFailure(call: Call<List<CryptoModel>>?, t: Throwable?) {
                if (t != null) {
                    t.printStackTrace()
                }
            }
        })



        Scaffold(topBar = {AppBar()}) {
            CryptoList(cryptos = cryptoModels)
        }
}
@Composable
fun AppBar(){
    TopAppBar(contentPadding = PaddingValues(10.dp), backgroundColor = FurkanKrt) {
        Text(text = "Retrofit Compose", fontSize = 26.sp)
    }


}
@Composable
fun CryptoList(cryptos : List<CryptoModel>)
{
    LazyColumn(contentPadding = PaddingValues(5.dp))
    {
        items(cryptos) { crypto ->
            CrytptoRow(crypto = crypto)
            
         
            
        }
    }
}
@Composable
fun CrytptoRow(crypto : CryptoModel)
{
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.surface)) {
        Text(text = crypto.currency,
            color=MaterialTheme.colors.onSurface,
            style=MaterialTheme.typography.h4,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold)
        Text(text = crypto.price,
            style=MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp))
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitComposeTheme {
        CrytptoRow(crypto = CryptoModel("BITCOİN" , price = "12123"))
    }
}