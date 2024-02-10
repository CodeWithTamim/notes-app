package com.cwtstudio.notesappwithsp.Models;

public class Notes {
    private String noteTitle;
    private String noteDescription;
    private String date;
    private boolean isPinned;

    public Notes() {
    }

    public Notes(String noteTitle, String noteDescription, String date, boolean isPinned) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.date = date;
        this.isPinned = isPinned;
    }

    public boolean getisPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
