package com.cwtstudio.notesappwithsp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cwtstudio.notesappwithsp.Interfaces.ClickListener;
import com.cwtstudio.notesappwithsp.Models.Notes;
import com.cwtstudio.notesappwithsp.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHoler> {
    List<Notes> notes;
    Context context;
    ClickListener clickListener;

    public NotesAdapter(List<Notes> notes, Context context, ClickListener clickListener) {
        this.notes = notes;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NotesViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHoler(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHoler holder, int position) {
        holder.txt_title.setText(notes.get(position).getNoteTitle());
        holder.txt_description.setText(notes.get(position).getNoteDescription());
        holder.txtDate.setText(notes.get(position).getDate());
        holder.txtDate.setSelected(true);
        if (notes.get(position).getisPinned()) {
            holder.imgPin.setImageResource(R.drawable.ic_pin);

        } else {
            holder.imgPin.setImageResource(0);


        }


        holder.mainCardView.setOnClickListener(v -> {
            clickListener.onClickListener(position);

        });
        holder.mainCardView.setOnLongClickListener(v -> {
            clickListener.onLongClickListener(position);
            return true;
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public void filterList(List<Notes> filteredList){
        notes = filteredList;
        notifyDataSetChanged();
    }
    public class NotesViewHoler extends RecyclerView.ViewHolder {
        private CardView mainCardView;
        private TextView txt_title, txt_description, txtDate;
        private ImageView imgPin;


        public NotesViewHoler(@NonNull View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgPin = itemView.findViewById(R.id.imgPin);


        }
    }
}
