package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getListByUser(String userName){
        return noteMapper.findNoteByUser(userName);
    }

    public int addNote (Note note){
        return noteMapper.insertNote(new Note(null,note.getNoteTitle(),note.getNoteDescription(),note.getUserId()));
    }

    public void deleteNote(int noteID){
        noteMapper.deleteNote(noteID);
    }

    public int update(Note note){
        Note noteUd = noteMapper.findNoteById(note.getNoteId());
        noteUd.setNoteDescription(note.getNoteDescription());
        noteUd.setNoteTitle(note.getNoteTitle());
        return noteMapper.updateNote(noteUd);
    }

    public Note findNoteById(int id){
        return noteMapper.findNoteById(id);
    }




}
