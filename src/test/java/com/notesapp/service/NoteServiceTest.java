package com.notesapp.service;

import com.notesapp.dto.NoteRequest;
import com.notesapp.dto.NoteResponse;
import com.notesapp.entity.Note;
import com.notesapp.exception.ResourceNotFoundException;
import com.notesapp.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note testNote;
    private NoteRequest testNoteRequest;

    @BeforeEach
    void setUp() {
        testNote = new Note();
        testNote.setId(1L);
        testNote.setTitle("Test Note");
        testNote.setContent("Test Content");
        testNote.setCreatedAt(LocalDateTime.now());
        testNote.setUpdatedAt(LocalDateTime.now());

        testNoteRequest = new NoteRequest();
        testNoteRequest.setTitle("Test Note");
        testNoteRequest.setContent("Test Content");
    }

    @Test
    void createNote_ShouldReturnNoteResponse() {
        // Arrange
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        // Act
        NoteResponse response = noteService.createNote(testNoteRequest);

        // Assert
        assertNotNull(response);
        assertEquals(testNote.getId(), response.getId());
        assertEquals(testNote.getTitle(), response.getTitle());
        assertEquals(testNote.getContent(), response.getContent());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void getAllNotes_ShouldReturnListOfNotes() {
        // Arrange
        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Second Note");
        note2.setContent("Second Content");
        note2.setCreatedAt(LocalDateTime.now());
        note2.setUpdatedAt(LocalDateTime.now());

        List<Note> notes = Arrays.asList(testNote, note2);
        when(noteRepository.findAll()).thenReturn(notes);

        // Act
        List<NoteResponse> responses = noteService.getAllNotes();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(testNote.getTitle(), responses.get(0).getTitle());
        assertEquals(note2.getTitle(), responses.get(1).getTitle());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getAllNotes_WhenNoNotes_ShouldReturnEmptyList() {
        // Arrange
        when(noteRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<NoteResponse> responses = noteService.getAllNotes();

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getNoteById_WhenNoteExists_ShouldReturnNote() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));

        // Act
        NoteResponse response = noteService.getNoteById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(testNote.getId(), response.getId());
        assertEquals(testNote.getTitle(), response.getTitle());
        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    void getNoteById_WhenNoteDoesNotExist_ShouldThrowException() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> noteService.getNoteById(1L));
        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    void deleteNote_WhenNoteExists_ShouldDeleteNote() {
        // Arrange
        when(noteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(noteRepository).deleteById(1L);

        // Act
        noteService.deleteNote(1L);

        // Assert
        verify(noteRepository, times(1)).existsById(1L);
        verify(noteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNote_WhenNoteDoesNotExist_ShouldThrowException() {
        // Arrange
        when(noteRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> noteService.deleteNote(1L));
        verify(noteRepository, times(1)).existsById(1L);
        verify(noteRepository, never()).deleteById(1L);
    }
}
