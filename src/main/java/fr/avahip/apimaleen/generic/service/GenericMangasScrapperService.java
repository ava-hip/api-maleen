package fr.avahip.apimaleen.generic.service;

import fr.avahip.apimaleen.dto.MangaDto;
import org.jsoup.Connection;
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
    Connection session = Jsoup.newSession()
            .timeout(45 * 1000)
            .maxBodySize(5 * 1024 * 1024);

    public List<MangaDto> scrapMangasList(String url) {
        List<MangaDto> mangas = new ArrayList<>();
        try {
            String baseUrl = "https://www.nautiljon.com/";
            Document document = session.newRequest(baseUrl + url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://www.google.com")
                    .get();

            for (Element manga : document.select("tr")) {
                String title = manga.select("td > a").text();
                String summary = Objects.requireNonNull(manga.select("td > p").first()).text();
                String imgUrl = Objects.requireNonNull(manga.select("img").first()).absUrl("src");
                if (!title.isEmpty()) {
                    MangaDto mangaDto = new MangaDto();
                    mangaDto.setTitle(title);
                    mangaDto.setSummary(summary);
                    mangaDto.setImgUrl(imgUrl);
                    mangaDto.setAuthor(scrapMangaInfo(title).getAuthor());
                    mangas.add(mangaDto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mangas;
    }

    public MangaDto scrapMangaInfo(String name) {
        MangaDto manga = new MangaDto();
        String formatedName = name.replace(" ", "+").replace("'", "-").replace("\"", "-").toLowerCase() + ".html";
        try {
            String baseUrl = "https://www.nautiljon.com/mangas/";
            Document document = session.newRequest(baseUrl + formatedName)
                    .timeout(0)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://www.google.com")
                    .get();

            manga.setAuthor(document.select("span[itemprop=author] span[itemprop=name]").text());
            manga.setTitle(document.select(".h1titre span[itemprop=name]").text());
            manga.setImgUrl(Objects.requireNonNull(document.select(".image_fiche img").first()).absUrl("src"));
            manga.setSummary(document.select(".description").text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return manga;
    }
}
