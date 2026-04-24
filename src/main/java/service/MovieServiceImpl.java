package service;

import config.GetApi;
import model.MovieDetail;
import model.MovieResponse;
import utils.TableView;

public class MovieServiceImpl implements MovieService {
    TableView tableView = new TableView();
    GetApi getApi = new GetApi();

    @Override
    public Integer searchMovies(String title, Integer page) {
        MovieResponse movieResponse = getApi.getMoviesResponse(title, page);
        tableView.displayAll(movieResponse);
        return movieResponse.getTotalPages();
    }

    @Override
    public void movieDetail(Integer id) {
        MovieDetail movieDetail = getApi.getMovieDetail(id);
        if (movieDetail == null) {
            System.out.println(" -> (!) Invalid movie ID..!");
            return;
        }
        tableView.displayMovieDetail(movieDetail);
    }
}
