package api.infrastructure.api.controllers;

import api.domain.models.Creator;
import api.application.services.CreatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creator")
public class CreatorController {
    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService=creatorService;
    }
    @GetMapping
    public List<Creator> getAll() {
        return creatorService.getAll();
    }

    @PostMapping
    public Creator create(@RequestBody Creator creador) {
        return creatorService.save(creador);
    }
}
