package com.example.passwordvault;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Utils.RecyclerTouchListener;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import database.DatabaseHelper;
import database.model.Note;

public class MainActivity extends AppCompatActivity {


    private NotesAdapter madapter;
    private List<Note> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView textView;
    private FloatingActionButton fab;

    private DatabaseHelper db;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        recyclerView = findViewById(R.id.recyclerview);
        textView = findViewById(R.id.empty_notes_view);

        db = new DatabaseHelper(this);

        notesList.addAll(db.getAllValues());

        fab = findViewById(R.id.floatingactionbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });


        madapter = new NotesAdapter(this, notesList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(madapter);

        toggleEmptyNotes();


        /*on long pressing the item,
         alert dialog must appear
          to edit or delete*/

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

    }

    //Insert new item in db and refresh list
    private void createNote(String username, String password, String website) {

        //int _pos = pos;
        String _username,_password,_website;
        _username = username;
        _password=password;
        _website = website;
        long id = db.insertData(_username, _password, _website);
        Note n = db.getValues(id);
        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);

            // refreshing the list
            madapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }

    private void updateNote(String username, String password, String website, int position) {

        Note n = notesList.get(position);
        n.setUsername(username);
        n.setPassword(password);
        n.setWebsite(website);
       // db.updateValues(n);

        notesList.set(position,n);
        madapter.notifyDataSetChanged();
        toggleEmptyNotes();

    }

    private void deleteNote(int position) {
        // deleting the note from db
        db.deleteValues(notesList.get(position));

        // removing the note from the list
        notesList.remove(position);
        madapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    //open dialog box with edit and delete option

    private void showActionsDialog(final int position) {

        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }


    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position) {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder builderUserInput = new AlertDialog.Builder(MainActivity.this);
        builderUserInput.setView(view);

        final EditText etUsername = view.findViewById(R.id.etusername);
        final EditText etPassword = view.findViewById(R.id.etpassword);
        final EditText etWebsite = view.findViewById(R.id.etwebsite);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);

        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            etUsername.setText(note.getUsername());
            etPassword.setText(note.getPassword());
            etWebsite.setText(note.getWebsite());
        }

        builderUserInput.setCancelable(false).setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter All values", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    updateNote(etUsername.getText().toString(), etPassword.getText().toString(), etWebsite.getText().toString(), position);
                } else {
                    // create new note
                    createNote(etUsername.getText().toString(), etPassword.getText().toString(), etWebsite.getText().toString());
                   //pos++;
                }
            }
        });

    }

    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (db.getValuesCount() > 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }
}
