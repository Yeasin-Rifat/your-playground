package com.your_playground.controller;

import com.your_playground.dto.turf.*;
import com.your_playground.errorhandler.ApiResponse;
import com.your_playground.service.turf.TurfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turfs")
public class TurfController {

    private final TurfService turfService;

    public TurfController(TurfService turfService) {
        this.turfService = turfService;
    }

    // CREATE
    @PostMapping("/create")
    public ApiResponse<TurfResponseDTO> create(@RequestBody TurfRequestDTO request) {
        return new ApiResponse<>("Turf created successfully", turfService.create(request));
    }

    // GET ALL
    @GetMapping
    public ApiResponse<List<TurfResponseDTO>> getAll() {
        return new ApiResponse<>("All turfs", turfService.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<TurfResponseDTO> getById(@PathVariable Long id) {
        return new ApiResponse<>("Turf found", turfService.getById(id));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ApiResponse<TurfResponseDTO> update(@PathVariable Long id,
                                               @RequestBody TurfRequestDTO request) {
        return new ApiResponse<>("Turf updated", turfService.update(id, request));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        turfService.delete(id);
        return new ApiResponse<>("Turf deleted", null);
    }
}