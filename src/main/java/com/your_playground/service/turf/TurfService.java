package com.your_playground.service.turf;

import com.your_playground.dto.turf.*;

import java.util.List;

public interface TurfService {

    TurfResponseDTO create(TurfRequestDTO request);

    List<TurfResponseDTO> getAll();

    TurfResponseDTO getById(Long id);

    TurfResponseDTO update(Long id, TurfRequestDTO request);

    void delete(Long id);
}