package com.todoapp.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todo")
    public ResponseEntity<Todo> get(@RequestParam(value = "id") int id)
    {
        // get todo from db by id
        Optional<Todo> todoInDB = todoRepository.findById(id);

        if(todoInDB.isPresent())
        {
            return new ResponseEntity<Todo>(todoInDB.get(), HttpStatus.OK);
        }

        return new ResponseEntity("No todo found with id " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todo/all")
    public ResponseEntity<Iterable<Todo>> getAll()
    {
        Iterable<Todo> allTodosInDb = todoRepository.findAll();
        return new ResponseEntity<Iterable<Todo>>(allTodosInDb, HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> create(@RequestBody Todo newTodo)
    {
        // save todo in db
        todoRepository.save(newTodo);
        return new ResponseEntity<Todo>(newTodo, HttpStatus.OK);
    }

    @DeleteMapping("/todo")
    public ResponseEntity delet(@RequestParam(value = "id") int id)
    {
        Optional<Todo> todoInDb = todoRepository.findById(id);
        if(todoInDb.isPresent())
        {
            todoRepository.deleteById(id);
            return new ResponseEntity("Todo deleted", HttpStatus.OK);
        }

        return new ResponseEntity("No todo to delete found with id " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> edit(@RequestBody Todo editedTodo)
    {
        Optional<Todo> todoInDb = todoRepository.findById(editedTodo.getId());

        if(todoInDb.isPresent())
        {
            // update
            Todo savedTodo = todoRepository.save(editedTodo);
            return new ResponseEntity<Todo>(savedTodo, HttpStatus.OK);
        }
        return new ResponseEntity("No todo to update found with id " + editedTodo.getId(), HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/todo/setDone")
    public ResponseEntity<Todo> setDone(@RequestParam(value = "isDone") boolean isDone,
                                        @RequestParam(value = "id") int id)
    {
        Optional<Todo> todoInDb = todoRepository.findById(id);

        if(todoInDb.isPresent())
        {
            // update isDone
            todoInDb.get().setIsDone(isDone);
            Todo savedTodo = todoRepository.save(todoInDb.get());
            return new ResponseEntity<Todo>(savedTodo, HttpStatus.OK);
        }

        return new ResponseEntity("No todo to isDone found with id " + id, HttpStatus.NOT_FOUND);
    }
}
