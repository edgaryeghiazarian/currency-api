package com.currencyapi.demo.controller;

import com.currencyapi.demo.model.HistoryDTO;
import com.currencyapi.demo.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHistory(@PathVariable long id) {
        HistoryDTO history = historyService.getHistory(id);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }
}
