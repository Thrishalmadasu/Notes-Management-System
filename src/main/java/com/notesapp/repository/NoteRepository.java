package com.notesapp.repository;

import com.notesapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // JpaRepository provides all basic CRUD operations
    // Additional custom queries can be added here if needed
}
