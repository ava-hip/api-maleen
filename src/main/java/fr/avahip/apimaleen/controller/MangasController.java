package fr.avahip.apimaleen.controller;

import fr.avahip.apimaleen.dto.MangaDto;
import fr.avahip.apimaleen.service.MangasServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mangas")
public class MangasController {
    private final MangasServices service;

    public MangasController(MangasServices service) {
        this.service = service;
    }


    @GetMapping("/tendance")
    public List<MangaDto> getTrendingMangas(@RequestParam String page) {
        return service.findTrendingManga(page);
    }

    @GetMapping("/populaires")
    public List<MangaDto> getPopularMangas(@RequestParam String page) {
        return service.findMostPopularManga(page);
    }
}
