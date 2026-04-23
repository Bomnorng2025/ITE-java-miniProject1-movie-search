package config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Movie;
import model.MovieDetail;
import model.MovieResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GetApi {

    HttpClient client = HttpClient.newHttpClient();
    public HttpRequest requestApi(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + ApiConfig.accessToken)
                .header("Accept", "application/json")
                .GET()
                .build();
    }

//    get video trailer url ------------
    public String getVideos(Integer id) {
        String url = ApiConfig.baseUrl + "movie/" + id + "/videos";

        try {
            HttpResponse<String> responseVideo = client.send(requestApi(url), HttpResponse.BodyHandlers.ofString());

            JsonArray results = JsonParser.parseString(responseVideo.body()).getAsJsonObject().getAsJsonArray("results");
            String key = "";
            for (int i = 0; i < results.size(); i++) {
                JsonObject video = results.get(i).getAsJsonObject();
                String type  = video.get("type").getAsString();
                if(type.equals("Trailer")) {
                    key = video.get("key").getAsString();
                }
            }
            return ((!key.isEmpty())? "https://www.youtube.com/watch?v=" + key : "N/A");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    get movies -------------------------
    public MovieResponse getMoviesResponse(String title, Integer page) {
        String url = ApiConfig.baseUrl + "search/movie?" + ApiConfig.apiKey + "&query=" + title + "&page=" + page;

        try {
            HttpResponse<String> responseMovie = client.send(requestApi(url), HttpResponse.BodyHandlers.ofString());
            JsonObject root = JsonParser.parseString(responseMovie.body()).getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");

            MovieResponse movieResponse = new MovieResponse();

            movieResponse.setPage(root.get("page").getAsInt());
            movieResponse.setTotalPages(root.get("total_pages").getAsInt());
            movieResponse.setTotalResults(root.get("total_results").getAsInt());

            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                JsonObject movieResult = results.get(i).getAsJsonObject();
                Movie movie = new Movie();

                movie.setId(movieResult.get("id").getAsString());
                movie.setTitle(movieResult.get("title").getAsString());
                movie.setReleaseDate(movieResult.get("release_date").getAsString());
                movie.setRating(movieResult.get("vote_average").getAsDouble());
                movie.setTrailer(getVideos(movieResult.get("id").getAsInt()));

                movies.add(movie);
            }
            movieResponse.setResults(movies);

            return movieResponse;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    get movie detail -------------------
    public MovieDetail getMovieDetail(Integer id) {
        String url = ApiConfig.baseUrl + "movie/" + id;

        try {
            HttpResponse<String> responseMovieDetail = client.send(requestApi(url), HttpResponse.BodyHandlers.ofString());

            JsonObject root = JsonParser.parseString(responseMovieDetail.body()).getAsJsonObject();
            JsonArray countryArray = root.getAsJsonArray("origin_country");
            String originCountry = "";
            for (int i = 0; i < countryArray.size(); i++) {
                originCountry += countryArray.get(i).getAsString();

                if (i < countryArray.size() - 1) {
                    originCountry += ", ";
                }
            }

            JsonArray genresArray = root.getAsJsonArray("genres");
            String genres = "";
            for (int i = 0; i < genresArray.size(); i++) {
                JsonObject genreObject = genresArray.get(i).getAsJsonObject();
                genres += genreObject.get("name").getAsString();

                if (i < genresArray.size() - 1) {
                    genres += ", ";
                }
            }

            return new MovieDetail(
                    root.get("title").getAsString(),
                    root.get("release_date").getAsString(),
                    root.get("vote_average").getAsDouble(),
                    root.get("runtime").getAsInt(),
                    root.get("budget").getAsInt(),
                    genres,
                    originCountry
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
