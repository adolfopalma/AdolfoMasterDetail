package com.example.adolfo.masterdetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.adolfo.masterdetail.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JugadorListActivity extends AppCompatActivity {

    private String TAG = JugadorListActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    private String url_consulta;
    private JSONArray jSONArray;
    private JSONObject jsonObject;
    private DevuelveJSON devuelveJSON;
    public static ArrayList<Jugador> arrayJugadores;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_list);

        arrayJugadores = new ArrayList<Jugador>();
        url_consulta = "http://iesayala.ddns.net/adolfopalma/php.php";
        devuelveJSON = new DevuelveJSON();
        new ListaAlbum().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());



        View recyclerView = findViewById(R.id.album_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.album_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.jugador_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
           // holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).nombreJugador + " - " + mValues.get(position).equipo);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(JugadorDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        JugadorDetailFragment fragment = new JugadorDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.album_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, JugadorDetailActivity.class);
                        intent.putExtra(JugadorDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            //public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
               // mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    class ListaAlbum extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(JugadorListActivity.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONArray doInBackground(String... args) {
            try {
                HashMap<String, String> parametrosPost = new HashMap<>();
                parametrosPost.put("ins_sql","SELECT * FROM EQUIPOS,JUGADORES WHERE EQUIPOS.CODEQUIPO = JUGADORES.CODEQUIPOS ORDER BY NOMEQUIPO");
                jSONArray = devuelveJSON.sendRequest(url_consulta, parametrosPost);
                if (jSONArray != null) {
                    return jSONArray;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                //Toast.makeText(MainActivity.this, json.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(JugadorListActivity.this, "JSON Array nulo", Toast.LENGTH_LONG).show();
            }
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        Jugador jugador = new Jugador();
                        jugador.setNombreJugador(jsonObject.getString("NomJugador"));
                        jugador.setDorsal(jsonObject.getInt("Dorsal"));
                        jugador.setLink(jsonObject.getString("link"));
                        jugador.setNombreEquipo(jsonObject.getString("NomEquipo"));
                        arrayJugadores.add(jugador);
                        System.out.println(jugador.getNombreJugador());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                DummyContent.rellenarItems();

            } else {
                Toast.makeText(JugadorListActivity.this, "JSON Array nulo", Toast.LENGTH_LONG).show();
            }
        }
    }
}
