package com.notesapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notesapp.dto.NoteRequest;
import com.notesapp.dto.NoteResponse;
import com.notesapp.exception.GlobalExceptionHandler;
import com.notesapp.exception.ResourceNotFoundException;
import com.notesapp.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NoteController.class)
@Import(GlobalExceptionHandler.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    private NoteRequest testNoteRequest;
    private NoteResponse testNoteResponse;

    @BeforeEach
    void setUp() {
        testNoteRequest = new NoteRequest();
        testNoteRequest.setTitle("Test Note");
        testNoteRequest.setContent("Test Content");

        testNoteResponse = new NoteResponse();
        testNoteResponse.setId(1L);
        testNoteResponse.setTitle("Test Note");
        testNoteResponse.setContent("Test Content");
        testNoteResponse.setCreatedAt(LocalDateTime.now());
        testNoteResponse.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createNote_WithValidRequest_ShouldReturnCreated() throws Exception {
        // Arrange
        when(noteService.createNote(any(NoteRequest.class))).thenReturn(testNoteResponse);

        // Act & Assert
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testNoteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Test Note"))
                .andExpect(jsonPath("$.data.content").value("Test Content"));

        verify(noteService, times(1)).createNote(any(NoteRequest.class));
    }

    @Test
    void createNote_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange
        NoteRequest invalidRequest = new NoteRequest();
        invalidRequest.setTitle(""); // Invalid: blank title
        invalidRequest.setContent(""); // Invalid: blank content

        // Act & Assert
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validation failed"));

        verify(noteService, never()).createNote(any(NoteRequest.class));
    }

    @Test
    void getAllNotes_ShouldReturnListOfNotes() throws Exception {
        // Arrange
        NoteResponse note2 = new NoteResponse();
        note2.setId(2L);
        note2.setTitle("Second Note");
        note2.setContent("Second Content");
        note2.setCreatedAt(LocalDateTime.now());
        note2.setUpdatedAt(LocalDateTime.now());

        List<NoteResponse> notes = Arrays.asList(testNoteResponse, note2);
        when(noteService.getAllNotes()).thenReturn(notes);

        // Act & Assert
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Notes retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Test Note"))
                .andExpect(jsonPath("$.data[1].title").value("Second Note"));

        verify(noteService, times(1)).getAllNotes();
    }

    @Test
    void getAllNotes_WhenNoNotes_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(noteService.getAllNotes()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));

        verify(noteService, times(1)).getAllNotes();
    }

    @Test
    void deleteNote_WhenNoteExists_ShouldReturnSuccess() throws Exception {
        // Arrange
        doNothing().when(noteService).deleteNote(1L);

        // Act & Assert
        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note deleted successfully"));

        verify(noteService, times(1)).deleteNote(1L);
    }

    @Test
    void deleteNote_WhenNoteDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Note not found with id: 1"))
                .when(noteService).deleteNote(1L);

        // Act & Assert
        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Note not found with id: 1"));

        verify(noteService, times(1)).deleteNote(1L);
    }
}
