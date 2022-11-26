package com.example.a4tatarea.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4tatarea.MainActivity;
import com.example.a4tatarea.R;
import com.example.a4tatarea.model.Pelicula;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class PeliculaAdapter extends FirestoreRecyclerAdapter<Pelicula, PeliculaAdapter.viewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    FragmentManager fm;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *  @param options
     *
     */
    public PeliculaAdapter(@NonNull FirestoreRecyclerOptions<Pelicula> options, Activity activity, FragmentManager fm) {
        super(options);
        this.activity = activity;
        this.fm = fm;
    }

    public PeliculaAdapter(FirestoreRecyclerOptions<Pelicula> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Pelicula Pelicula) {
        holder.año.setText(Pelicula.getAño());
        holder.director.setText(Pelicula.getDirector());
        holder.duracion.setText(Pelicula.getDuracion());
        holder.genero.setText(Pelicula.getGenero());
        holder.resolucion.setText(Pelicula.getResolucion());
        holder.sinopsis.setText(Pelicula.getSinopsis());
        holder.titulo.setText(Pelicula.getTitulo());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pelicula, parent,false);
        return new viewHolder(v);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView año, director, duracion, genero, resolucion, sinopsis, titulo;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            año = itemView.findViewById(R.id.año);
            director = itemView.findViewById(R.id.director);
            duracion = itemView.findViewById(R.id.duracion);
            genero = itemView.findViewById(R.id.genero);
            resolucion = itemView.findViewById(R.id.resolucion);
            sinopsis = itemView.findViewById(R.id.sinopsis);
            titulo = itemView.findViewById(R.id.titulo);
        }

    }
}
