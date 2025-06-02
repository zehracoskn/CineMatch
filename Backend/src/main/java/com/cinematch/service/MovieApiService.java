package com.cinematch.service;

import com.cinematch.entity.Movie;
import com.cinematch.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import jakarta.annotation.PostConstruct;

import java.util.*;

@Service
public class MovieApiService {
    @Autowired
    private MovieRepository movieRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey = System.getenv("TMDB_API_KEY");


    private Map<Integer, String> genreIdNameMap = new HashMap<>();


    @PostConstruct
    public void init() {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey + "&language=en-US";
        JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        JsonNode genres = response.get("genres");
        for (JsonNode genre : genres) {
            int id = genre.get("id").asInt();
            String name = genre.get("name").asText();
            genreIdNameMap.put(id, name);
        }
        System.out.println("Genre eşleme haritası oluşturuldu: " + genreIdNameMap);
    }


    public void fetchAndSaveMoviesByCategory(int genreId) {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&with_genres=" + genreId + "&language=en-US";
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode results = response.getBody().get("results");
        int count = 0;

        for (JsonNode node : results) {
            if (count >= 10) break; //each genre 10 film

            String title = node.get("title").asText();
            String releaseDate = node.get("release_date").asText();
            Integer releaseYear = null;
            if (!releaseDate.isEmpty() && releaseDate.length() >= 4) {
                releaseYear = Integer.parseInt(releaseDate.substring(0, 4));
            }

            // Duplicate kontrol
            if (movieRepository.findByTitleAndReleaseYear(title, releaseYear).isPresent()) {
                continue;
            }

            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setOverview(node.get("overview").asText());
            movie.setPosterUrl("https://image.tmdb.org/t/p/w500" + node.get("poster_path").asText());
            movie.setReleaseYear(releaseYear);


            List<String> genreNames = new ArrayList<>();
            if (node.has("genre_ids")) {
                for (JsonNode genreIdNode : node.get("genre_ids")) {
                    int id = genreIdNode.asInt();
                    String name = genreIdNameMap.get(id);
                    if (name != null) {
                        genreNames.add(name);
                    }
                }
            }
            String genresStr = String.join(",", genreNames); // Tek satır string!
            movie.setGenres(genresStr);


            Long movieId = node.get("id").asLong();
            String creditsUrl = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + apiKey + "&language=en-US";
            JsonNode creditsResponse = restTemplate.getForObject(creditsUrl, JsonNode.class);

           //Actor
            List<String> actorNames = new ArrayList<>();
            JsonNode castArray = creditsResponse.get("cast");
            for (int i = 0; castArray != null && i < castArray.size() && i < 3; i++) {
                actorNames.add(castArray.get(i).get("name").asText());
            }
            String actorsStr = String.join(",", actorNames);
            movie.setActors(actorsStr);

            // Director
            String directorName = "";
            JsonNode crewArray = creditsResponse.get("crew");
            if (crewArray != null) {
                for (JsonNode crew : crewArray) {
                    if ("Director".equals(crew.get("job").asText())) {
                        directorName = crew.get("name").asText();
                        break;
                    }
                }
            }
            movie.setDirector(directorName);

            movieRepository.save(movie);
            count++;

            movieId = node.get("id").asLong();

            // runtime ve rating endpoint
            String detailUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey + "&language=en-US";
            JsonNode detailResponse = restTemplate.getForObject(detailUrl, JsonNode.class);

        // runtime
            if (detailResponse != null && detailResponse.has("runtime") && !detailResponse.get("runtime").isNull()) {
                movie.setRuntime(detailResponse.get("runtime").asInt());
            }

                // rating
            if (detailResponse != null && detailResponse.has("vote_average") && !detailResponse.get("vote_average").isNull()) {
                movie.setRating(detailResponse.get("vote_average").asDouble());
            } else if (node.has("vote_average") && !node.get("vote_average").isNull()) {
                movie.setRating(node.get("vote_average").asDouble());
            }
        }
    }
}