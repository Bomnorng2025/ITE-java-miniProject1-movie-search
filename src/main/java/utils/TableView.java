package utils;

import model.Movie;
import model.MovieDetail;
import model.MovieResponse;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TableView {
    CellStyle center = new CellStyle(CellStyle.HorizontalAlign.CENTER);

    public void displayAll(MovieResponse movieResponse) {
        List<Movie> movies = movieResponse.getResults();
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        String[] columnName = {"ID", "TITLE", "RELEASE_DATE", "RATING", "TRAILER_URL"};
        for (String column : columnName) {
            table.addCell(" " + column + " ", center);
        }
        for (int i = 0; i < columnName.length; i++) {
            table.setColumnWidth(i, 10, 60 );
        }

        for (Movie movie : movies) {
            table.addCell(" " + movie.getId() + " ", center);
            table.addCell(" " + movie.getTitle() + " ");
            table.addCell(" " + movie.getReleaseDate() + " ", center);
            table.addCell(" " + String.format("%.2f", movie.getRating()) + " ", center);
            table.addCell(" " + movie.getTrailer() + " ");
        }
        String pagination =
                " >> Page " + movieResponse.getPage() +
                " of " + movieResponse.getTotalPages();
        table.addCell(pagination, 2);
        table.addCell(" >> Total results: " + movieResponse.getTotalResults(), 3);

        System.out.println(table.render());
    }

    public void displayMovieDetail(MovieDetail movieDetail) {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        for (int i = 0; i < 2; i++) {
            table.setColumnWidth(i, 20, 60 );
        }
        table.addCell("MOVIE INFORMATION", center,2);

        table.addCell("TITLE", center);
        table.addCell(" " + movieDetail.getTitle() + " ");
        table.addCell("GENRES", center);
        table.addCell(" " + movieDetail.getGenres() + " ");
        table.addCell("RELEASE_DATE", center);
        table.addCell(" " + movieDetail.getReleaseDate() + " ");
        table.addCell("RATING", center);
        table.addCell(" " + String.format("%.2f", movieDetail.getRating()) + " / 10 ");
        table.addCell("RUNTIME", center);
        table.addCell(" " + movieDetail.getRuntime().toString() + " min ");
        table.addCell("BUDGET", center);
        table.addCell(" " + NumberFormat.getCurrencyInstance(Locale.US).format(movieDetail.getBudget()) + " ");
        table.addCell("COUNTRY", center);
        table.addCell(" " + movieDetail.getOriginCountry() + " ");

        System.out.println(table.render());
    }
}
