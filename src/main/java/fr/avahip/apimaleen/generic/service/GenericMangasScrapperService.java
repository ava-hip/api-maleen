package fr.avahip.apimaleen.generic.service;

import fr.avahip.apimaleen.dto.MangaDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GenericMangasScrapperService {
    public List<MangaDto> scrapMangasList(String url) {
        List<MangaDto> mangas = new ArrayList<>();
        try {
            String baseUrl = "https://www.nautiljon.com/";
            Document document = Jsoup.connect(baseUrl + url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://www.google.com")
                    .get();

            for (Element manga : document.select("tr")) {
                String title = manga.select("td > a").text();
                String summary = Objects.requireNonNull(manga.select("td > p").first()).text();
                String imgUrl = Objects.requireNonNull(manga.select("img").first()).absUrl("src");
                MangaDto mangaDto = new MangaDto();
                if (!title.isEmpty()) {
                    mangaDto.setTitle(title);
                    mangaDto.setSummary(summary);
                    mangaDto.setImgUrl(imgUrl);
                    mangas.add(mangaDto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mangas;
    }
}
