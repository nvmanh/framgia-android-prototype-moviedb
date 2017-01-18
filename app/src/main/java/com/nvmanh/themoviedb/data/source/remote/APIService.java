package com.nvmanh.themoviedb.data.source.remote;

import com.nvmanh.themoviedb.data.GenreWrapper;
import com.nvmanh.themoviedb.data.MovieWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public interface APIService {
    //IF PUBLIC SOURCE OR PUSH TO VCS PLEASE ENCODE THESE VALUES
    // for more information to encode source and protect key please access
    // https://github.com/nvmanh/protect-key-android
    String BASE_URL = "https://api.themoviedb.org/3/";
    String API_KEY = "311992c9f89dcc572f422d18e2f830e8";
    String IMAGE_URL = "http://image.tmdb.org/t/p/w500%s?api_key=%s";
    int DEFAULT_LIMIT = 20;

    /**
     * request playing movies from the moviedb
     * @param apiKey your api key to request api service
     * @param languageCode default en
     * @param page limitation values return
     * @return {@link MovieWrapper}
     */
    @GET("movie/now_playing")
    Observable<MovieWrapper> getPlayingMovies(@Query("api_key") String apiKey,
            @Query("language") String languageCode, @Query("page") int page);

    /**
     * request genres list from the moviedb
     * @param apiKey your api key to request api service
     * @param languageCode default en
     * @return {@link GenreWrapper}
     */
    @GET("genre/movie/list")
    Observable<GenreWrapper> getGenres(@Query("api_key") String apiKey,
            @Query("language") String languageCode);

    /**
     * request similar movies from the moviedb
     * @param movieId id of current movie which you want to get all similar movies with it
     * @param apiKey your api key to request api service
     * @param languageCode default en
     * @param page limitation values return
     * @return {@link MovieWrapper}
     */
    @GET("movie/{movie_id}/similar")
    Observable<MovieWrapper> getSimilarMovies(@Path("movie_id") int movieId,
            @Query("api_key") String apiKey, @Query("language") String languageCode,
            @Query("page") int page);
}