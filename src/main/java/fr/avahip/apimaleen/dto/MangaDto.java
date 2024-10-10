package fr.avahip.apimaleen.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class MangaDto {
    String title;
    String imgUrl;
    String summary;
    List<String> genres;
}
