package fr.avahip.apimaleen.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MangaDto {
    String title;
    String author;
    String imgUrl;
    String summary;
}
