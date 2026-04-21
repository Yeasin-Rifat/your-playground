package com.your_playground.service.turf;

import com.your_playground.dto.turf.*;
import com.your_playground.entity.Turf;
import com.your_playground.entity.User;
import com.your_playground.exception.UserNotFoundException;
import com.your_playground.repository.TurfRepository;
import com.your_playground.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurfServiceImpl implements TurfService {

    private final TurfRepository turfRepository;
    private final UserRepository userRepository;

    public TurfServiceImpl(TurfRepository turfRepository,
                           UserRepository userRepository) {
        this.turfRepository = turfRepository;
        this.userRepository = userRepository;
    }

    // CREATE
    @Override
    public TurfResponseDTO create(TurfRequestDTO request) {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        if (!owner.getRole().equalsIgnoreCase("OWNER")) {
            throw new RuntimeException("Only OWNER can create turf");
        }

        Turf turf = new Turf();
        turf.setLocation(request.getLocation());
        turf.setSportType(request.getSportType());
        turf.setPricePerHour(request.getPricePerHour());
        turf.setOwner(owner);

        return mapToDTO(turfRepository.save(turf));
    }

    // GET ALL
    @Override
    public List<TurfResponseDTO> getAll() {
        return turfRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @Override
    public TurfResponseDTO getById(Long id) {

        Turf turf = turfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turf not found"));

        return mapToDTO(turf);
    }

    // UPDATE
    @Override
    public TurfResponseDTO update(Long id, TurfRequestDTO request) {

        Turf turf = turfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turf not found"));

        if (request.getLocation() != null) {
            turf.setLocation(request.getLocation());
        }

        if (request.getSportType() != null) {
            turf.setSportType(request.getSportType());
        }

        if (request.getPricePerHour() != null) {
            turf.setPricePerHour(request.getPricePerHour());
        }

        if (request.getOwnerId() != null) {
            User owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new UserNotFoundException("Owner not found"));
            turf.setOwner(owner);
        }

        return mapToDTO(turfRepository.save(turf));
    }

    // DELETE
    @Override
    public void delete(Long id) {
        Turf turf = turfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turf not found"));

        turfRepository.delete(turf);
    }

    // MAPPER
    private TurfResponseDTO mapToDTO(Turf turf) {

        TurfResponseDTO dto = new TurfResponseDTO();
        dto.setTurfId(turf.getTurfId());
        dto.setLocation(turf.getLocation());
        dto.setSportType(turf.getSportType());
        dto.setPricePerHour(turf.getPricePerHour());

        dto.setOwnerId(turf.getOwner().getUserId());
        dto.setOwnerName(turf.getOwner().getName());

        return dto;
    }
}