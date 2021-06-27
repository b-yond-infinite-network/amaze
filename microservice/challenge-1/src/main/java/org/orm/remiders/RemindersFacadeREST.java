/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.orm.remiders;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Isuru
 */
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/path")
public class RemindersFacadeREST {
    private final ReminderRepository reminderRepository;
    private final RemindersPaginationRepository remindersPaginationRepository;

    @PostMapping
    public void create(@RequestBody Reminders entity) {
        reminderRepository.save(entity);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable("id") Integer id, Reminders entity) {
        reminderRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Integer id) {
        reminderRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Reminders find(@PathVariable("id") Integer id) {
        return reminderRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("record not found"));
    }

    @GetMapping
    public List<Reminders> findAll() {
        return reminderRepository.findAll();
    }

    @GetMapping("{from}/{to}")
    public List<Reminders> findRange(@PathVariable("from") Integer from, @PathVariable("to") Integer to) {
        return remindersPaginationRepository.findAll(PageRequest.of(from - to, 10))
                                            .getContent();
    }

    @GetMapping("/count")
    public String countREST() {
        return String.valueOf(reminderRepository.count());
    }
}