package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetail {
    private String title;
    private String releaseDate;
    private Double rating;
    private Integer runtime;
    private Integer budget;
    private String genres;
    private String originCountry;
}
