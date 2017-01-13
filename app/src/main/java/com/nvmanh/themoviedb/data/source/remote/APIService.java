package com.nvmanh.themoviedb.data.source.remote;

import com.nvmanh.themoviedb.data.GenreWrapper;
import com.nvmanh.themoviedb.data.MovieWrapper;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public interface APIService {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String API_KEY = "311992c9f89dcc572f422d18e2f830e8";
    String IMAGE_URL = "http://image.tmdb.org/t/p/w500%s?api_key=%s";
    int DEFAULT_LIMIT = 20;

    @GET("movie/now_playing")
    Observable<MovieWrapper> getPlayingMovies(@Query("api_key") String apiKey,
            @Query("language") String languageCode, @Query("page") int page);

    @GET("genre/movie/list")
    Observable<GenreWrapper> getGenres(@Query("api_key") String apiKey,
            @Query("language") String languageCode);
}