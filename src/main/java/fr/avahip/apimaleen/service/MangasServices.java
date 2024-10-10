package fr.avahip.apimaleen.service;

import fr.avahip.apimaleen.dto.MangaDto;
import fr.avahip.apimaleen.generic.service.GenericMangasScrapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangasServices {
    private final GenericMangasScrapperService service;

    public MangasServices(GenericMangasScrapperService service) {
        this.service = service;
    }

    public List<MangaDto> findMostPopularManga(String page) {
        return service.scrapMangasList("classements/popularite/manga/" + page + "/");
    }

    public List<MangaDto> findTrendingManga(String page) {
        return service.scrapMangasList("classements/tendance/manga/" + page + "/");
    }

    public MangaDto findMangaByTitle(String title) {
        return service.scrapMangaInfo(title);
    }
}
