package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public Page<Student> getAll(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return keyword != null && !keyword.isEmpty() ?
                repository.findByNameContainingIgnoreCaseOrStudentClassContainingIgnoreCase(keyword, keyword, pageable) :
                repository.findAll(pageable);
    }

    public Optional<Student> getById(Long id) {
        return repository.findById(id);
    }

    public void save(Student student) {
        repository.save(student);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
