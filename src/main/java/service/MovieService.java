package service;

public interface MovieService {
    Integer searchMovies(String title, Integer page);
    void movieDetail(Integer id);

}
