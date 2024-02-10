package com.cwtstudio.notesappwithsp.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cwtstudio.notesappwithsp.Adapters.NotesAdapter;
import com.cwtstudio.notesappwithsp.Interfaces.ClickListener;
import com.cwtstudio.notesappwithsp.Managers.SPManager;
import com.cwtstudio.notesappwithsp.Models.Notes;
import com.cwtstudio.notesappwithsp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView mainRV;
    private FloatingActionButton flotingButton;
    private List<Notes> notesList;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeAll();
        setupRV();

        flotingButton.setOnClickListener(v -> {
            addNoteDialog();


        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);


                return true;
            }
        });


    }

    private void filter(String newtext) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNotes : notesList) {
            if (singleNotes.getNoteTitle().toLowerCase().contains(newtext.toLowerCase()) ||
                    singleNotes.getNoteDescription().toLowerCase().contains(newtext.toLowerCase())) {
                filteredList.add(singleNotes);


            }


        }
        adapter.filterList(filteredList);


    }

    private void addNoteDialog() {
        TextView dialog_title;
        EditText edtTitle, edtDescrioption;
        AppCompatButton btnAction;


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_note_dialog);
        //initalize views
        dialog_title = dialog.findViewById(R.id.dialog_title);
        edtTitle = dialog.findViewById(R.id.edtTitle);
        edtDescrioption = dialog.findViewById(R.id.edtDescrioption);
        btnAction = dialog.findViewById(R.id.btnAction);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
        Date dates = new Date();


        btnAction.setOnClickListener(v -> {
            String date = simpleDateFormat.format(dates);
            String title = edtTitle.getText().toString().trim();
            String description = edtDescrioption.getText().toString().trim();


            if (!title.isEmpty() && !description.isEmpty()) {
                notesList.add(new Notes(title, description, date, false));
                SPManager.saveNotesList(this, notesList);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            } else {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }

        });

        dialog.show();


    }


    private void setupRV() {
        notesList = SPManager.getNotesList(this);
        adapter = new NotesAdapter(notesList, this, clickListener);
        mainRV.setAdapter(adapter);

    }

    private void initializeAll() {
        searchView = findViewById(R.id.searchView);
        mainRV = findViewById(R.id.mainRV);
        flotingButton = findViewById(R.id.flotingButton);


    }

    private ClickListener clickListener = new ClickListener() {
        @Override
        public void onClickListener(int index) {
            showUpdateNoteDialog(index);

        }

        @Override
        public void onLongClickListener(int index) {
            setPinOrDeleteDialog(index);

        }
    };

    private void setPinOrDeleteDialog(int index) {
        new AlertDialog.Builder(this)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesList.remove(index);
                        SPManager.saveNotesList(MainActivity.this, notesList);
                        adapter.notifyDataSetChanged();

                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                })
                .setNeutralButton("Pin/Unpin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (notesList.get(index).getisPinned()) {
                            notesList.get(index).setPinned(false);
                        } else {

                            notesList.get(index).setPinned(true);
                        }
                        SPManager.saveNotesList(MainActivity.this, notesList);
                        adapter.notifyDataSetChanged();


                    }
                }).show();


    }

    private void showUpdateNoteDialog(int index) {
        TextView dialog_title;
        EditText edtTitle, edtDescrioption;
        AppCompatButton btnAction;


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_note_dialog);
        //initalize views
        dialog_title = dialog.findViewById(R.id.dialog_title);
        edtTitle = dialog.findViewById(R.id.edtTitle);
        edtDescrioption = dialog.findViewById(R.id.edtDescrioption);
        btnAction = dialog.findViewById(R.id.btnAction);
        dialog_title.setText("Update Note");
        Toast.makeText(this, "" + notesList.get(index).getisPinned(), Toast.LENGTH_SHORT).show();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
        Date dates = new Date();
        edtDescrioption.setText(notesList.get(index).getNoteDescription());
        edtTitle.setText(notesList.get(index).getNoteTitle());


        btnAction.setOnClickListener(v -> {
            String date = simpleDateFormat.format(dates);
            String title = edtTitle.getText().toString().trim();
            String description = edtDescrioption.getText().toString().trim();


            if (!title.isEmpty() && !description.isEmpty()) {
                notesList.set(index, new Notes(title, description, date, false));
                SPManager.saveNotesList(this, notesList);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            } else {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }

        });

        dialog.show();


    }


}