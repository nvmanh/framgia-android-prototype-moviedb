package com.nvmanh.themoviedb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.data.source.remote.APIService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 16/01/2017.
 */

public class Common {
    private static Map<Integer, Genre> genreMap;

    public static String getImageMovie(String path) {
        return String.format(APIService.IMAGE_URL, path, APIService.API_KEY);
    }

    public static void updateGenres(Movie movie, MoviesRepository repository) {
        StringBuilder builder = new StringBuilder();
        if (genreMap == null || genreMap.isEmpty()) loadGenres(repository);
        for (int id : movie.getGenreIds()) {
            if (!genreMap.containsKey(id)) continue;
            builder.append(genreMap.get(id).getName()).append(", ");
        }
        if (builder.length() > 0) {
            movie.setGenreList(builder.toString().substring(0, builder.length() - 2));
        }
    }

    public static void updateGenres(List<Movie> movies, MoviesRepository repository) {
        for (Movie movie : movies) {
            updateGenres(movie, repository);
        }
    }

    private static void loadGenres(MoviesRepository repository) {
        List<Genre> genres = repository.getGenres();
        genreMap = new HashMap<>();
        for (Genre genre : genres) {
            genreMap.put(genre.getId(), genre);
        }
    }

    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivity =
                (ConnectivityManager) App.self().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null || connectivity.getAllNetworkInfo() == null) return false;
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        for (NetworkInfo anInfo : info) {
            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
