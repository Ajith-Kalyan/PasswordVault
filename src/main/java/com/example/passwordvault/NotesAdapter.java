package com.example.passwordvault;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import database.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context context;
    private List<Note> noteslist;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvusername;
        public TextView tvpassword;
        public TextView tvwebsite;
        public TextView tvdot;

        public MyViewHolder(View view) {
            super(view);

            tvdot = view.findViewById(R.id.dot);
            tvusername= view.findViewById(R.id.username);
            tvpassword = view.findViewById(R.id.password);
            tvwebsite = view.findViewById(R.id.website);

        }
    }

    public NotesAdapter(Context context, List<Note> noteslist){
        this.context = context;
        this.noteslist = noteslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_row,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Note note = noteslist.get(position);
        holder.tvdot.setText(Html.fromHtml("&#8226;"));

        holder.tvusername.setText(note.getUsername());
        holder.tvpassword.setText(note.getPassword());
        holder.tvwebsite.setText(note.getWebsite());

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }


}
