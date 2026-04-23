package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Integer page;
    private Integer totalPages;
    private Integer totalResults;
    private List<Movie> results;
}
