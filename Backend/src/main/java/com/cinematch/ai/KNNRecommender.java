package com.cinematch.ai;

import com.cinematch.entity.Movie;
import java.util.*;

public class KNNRecommender {
    private List<Movie> allMovies;

    public KNNRecommender(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public List<Movie> recommend(List<Movie> likedMovies, int k) {
        Map<Movie, Double> similarityMap = new HashMap<>();

        for (Movie candidate : allMovies) {
            if (likedMovies.contains(candidate)) continue; // skip already liked

            double similaritySum = 0.0;
            for (Movie liked : likedMovies) {
                similaritySum += computeSimilarity(candidate, liked);
            }
            double avgSimilarity = similaritySum / likedMovies.size();
            similarityMap.put(candidate, avgSimilarity);
        }

        return similarityMap.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }

    private double computeSimilarity(Movie m1, Movie m2) {
        List<String> genres1 = Arrays.asList(m1.getGenres().split(","));
        List<String> genres2 = Arrays.asList(m2.getGenres().split(","));
        double genreScore = computeGenreSimilarity(genres1, genres2);

        double ratingDiff = Math.abs(m1.getRating() - m2.getRating());
        double runtimeDiff = Math.abs(m1.getRuntime() - m2.getRuntime());

        return genreScore - (ratingDiff / 10.0) - (runtimeDiff / 200.0);
    }

    private double computeGenreSimilarity(List<String> genres1, List<String> genres2) {
        Set<String> set1 = new HashSet<>(genres1);
        Set<String> set2 = new HashSet<>(genres2);

        int intersection = (int) set1.stream().filter(set2::contains).count();
        int union = (int) set1.stream().distinct().count() + (int) set2.stream().filter(g -> !set1.contains(g)).count();

        return union == 0 ? 0 : (double) intersection / union;
    }
}