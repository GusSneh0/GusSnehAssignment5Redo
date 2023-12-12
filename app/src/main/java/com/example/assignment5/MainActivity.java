package com.example.assignment5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ImageView pokemonImageView;
    private TextView nameTextView;
    private ProfileBaseAdapter profileBaseAdapter;
    private WatchlistBaseAdapter watchlistBaseAdapter;
    private List<String> textList;
    private List<Pokemon> watchList;
    private ListView profileListView;
    private ListView watchlistListView;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        pokemonImageView = findViewById(R.id.pokemonImageView); //wont work
        nameTextView = findViewById(R.id.nameTextView);
        textList = new ArrayList<>();
        watchList = new ArrayList<>();
        profileListView = findViewById(R.id.profileListVview);
        profileBaseAdapter = new ProfileBaseAdapter(getApplicationContext(), textList);
        profileListView.setAdapter(profileBaseAdapter);
        watchlistListView = findViewById(R.id.watchlistListView);
        watchlistBaseAdapter = new WatchlistBaseAdapter(getApplicationContext(), watchList);
        watchlistListView.setAdapter(watchlistBaseAdapter);

        watchlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView watchlistIDTV = view.findViewById(R.id.watchlistIDTV);
                search(watchList.get(position).getId());
            }
        });
    }

    public void search(View view) {
        profileRequest(searchEditText.getText().toString().toLowerCase());
    }

    public void search(int id) {
        profileRequest(String.valueOf(id));
    }

    private void profileRequest(String pokemon){
        AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{pokemon}/")
                .addPathParameter("pokemon", pokemon)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textList.clear();
                        try {
                            nameTextView.setText(StringUtils.capitalize(response.getString("name")));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            ID = Integer.parseInt(response.getString("id"));
                            textList.add("ID: "+(response.getString("id")));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            textList.add("Weight: "+response.getString("weight"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            textList.add("Height: "+response.getString("height"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            textList.add("Base XP: "+response.getString("base_experience"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            textList.add("Move: "+response.getJSONArray("moves").getJSONObject(0).getJSONObject("move").getString("name"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            textList.add("Ability: "+response.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            String imageURL = response.getJSONObject("sprites").getString("front_default");
                            //String imageURL = "https://github.com/HybridShivam/Pokemon/tree/master/assets/images/" + ID + ".png";
                            Picasso.get().load(imageURL).into(pokemonImageView);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        profileBaseAdapter.updateAdapter(textList);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(),"Please enter a valid Pokemon name or ID!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void clearProfile(View view) {
        textList.clear();
        profileBaseAdapter.updateAdapter(textList);
        pokemonImageView.setImageResource(android.R.drawable.ic_menu_info_details);
        nameTextView.setText("");
    }

    public void clearWatchlist(View view) {
        watchList.clear();
        watchlistBaseAdapter.updateAdapter(watchList);
    }

    public void add(View view) {
        AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{pokemon}/")
                .addPathParameter("pokemon", searchEditText.getText().toString().toLowerCase())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String name;
                        int id;
                        try {
                            name = StringUtils.capitalize(response.getString("name"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            id = Integer.parseInt(response.getString("id"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Pokemon pokemon = new Pokemon(name, id);
                        watchList.add(pokemon);
                        watchlistBaseAdapter.updateAdapter(watchList);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(),"Oops! You must enter a valid Pokemon name or ID!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}