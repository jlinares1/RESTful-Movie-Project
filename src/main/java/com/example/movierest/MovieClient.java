package com.example.movierest;

import org.hibernate.TypeMismatchException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class MovieClient {
    private String baseURL = "http://localHost:8080";
    private Scanner input;
    private RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        MovieClient client = new MovieClient();
        client.input = new Scanner(System.in);
        boolean askAgain = true;
        do {
            int pick = client.getChoice();
            switch (pick) {
                case 1: {
                    client.getMovieList();
                    break;
                }
                case 2: {
                    client.getMovieByID();
                    break;
                }
                case 3: {
                    client.createMovie();
                    break;
                }
                case 4: {
                    client.updateMovie();
                    break;
                }
                case 5: {
                    client.deleteMovie();
                    break;
                }
                default: {
                    askAgain = false;
                    System.out.println("Good Bye");
                }
            }
        } while (askAgain);

        client.input.close();
    }

    public void getMovieList() {
        try {
            String url = baseURL + "/movies";
            ResponseEntity<List<Movie>> rateResponse =
                    restTemplate.exchange(url,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Movie>>() {
                            });
            List<Movie> movies = rateResponse.getBody();
            for (Movie movie : movies) {
                System.out.println(movie);
            }

        } catch (HttpClientErrorException hce) {
            System.out.println(hce.getMessage());
        }
    }


    public void getMovieByID() {
        try {
            int id = getID("retrieve");
            String url = baseURL + "/movies/" + id;
            Movie movie = restTemplate.getForObject(url, Movie.class);
            System.out.println(movie.toString());
        } catch (HttpClientErrorException hce) {
            System.out.println(hce.getMessage());
        }
    }

    public void createMovie() {
        String url = baseURL + "/movies";
        boolean check = false;
        double rate = 0.0;
        int rateNum = 0;
        try {
            int id = 0;
            System.out.println("What is the title of the movie that you would like to create?");
            String title = input.nextLine();
            System.out.println("What genre is the movie?");
            String genre = input.nextLine();
            System.out.println("What would you rate the movie?");
            do {
                try {
                    rate = Double.parseDouble(input.nextLine());
                    check = false;
                } catch (NumberFormatException e) {
                    System.err.println("Please only enter numbers");
                    check = true;
                }
            } while (check);
            System.out.println("What description would you like to add?");
            String description = input.nextLine();
            System.out.println("What rating number are you using?");
            do {
                try {
                    rateNum = Integer.parseInt(input.nextLine());
                    check = false;
                } catch (NumberFormatException e) {
                    System.err.println("Please only enter numbers!");
                    check = true;
                }
            } while (check);
            Movie newMovie = new Movie(id, title, genre, rate, description, rateNum);
            restTemplate.postForObject(url, newMovie, Movie.class);
        } catch (HttpClientErrorException hce) {
            System.out.println(hce.getMessage());
        }
    }

    public void updateMovie() {
        boolean check = false;
        int rateNum = 0;
        double rate = 0.0;
        try {
            Movie movieUpdate = new Movie();
            int id = getID("update");
            System.out.println("What is the new title?");
            String title = input.nextLine();
            System.out.println("What is the new genre?");
            String genre = input.nextLine();
            System.out.println("What is the new rating?");
            do {
                try {
                    rate = Double.parseDouble(input.nextLine());
                    check = false;
                } catch (NumberFormatException e) {
                    System.err.println("Please only enter numbers! Ex: 0.0");
                    check = true;
                }
            } while (check);
            System.out.println("What is the new description?");
            String description = input.nextLine();
            System.out.println("What is the new rating number");
            do {
                try {
                    rateNum = Integer.parseInt(input.nextLine());
                    check = false;
                } catch (NumberFormatException e) {
                    System.err.println("Please only enter numbers!");
                    check = true;
                }
            } while (check);
            movieUpdate = new Movie(id, title, genre, rate, description, rateNum);
            String url = baseURL + "/movies/" + id;
            restTemplate.put(url, movieUpdate, Movie.class);
        } catch (HttpClientErrorException hce) {
            System.out.println(hce.getMessage());
        }
    }

    public void deleteMovie() {
        try {
            int id = getID("delete");
            String url = baseURL + "/movies/" + id;
            Movie movie = restTemplate.getForObject(url, Movie.class);
            System.out.println(movie.toString());
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id + "");
            System.out.println("Do you want to delete this movie? (Y/N)");
            char response = input.next().charAt(0);
            if (Character.toUpperCase(response) == 'Y') {
                restTemplate.delete(url, params);
            }
        } catch (HttpClientErrorException hce) {
            System.out.println(hce.getMessage());
        }
    }

    private int getChoice() {
        int choice = 0;
        do {
            try {
                System.out.println("Welcome:");
                System.out.println("1. View all movies");
                System.out.println("2. Retrieve a movie by ID");
                System.out.println("3. Create a movie object");
                System.out.println("4. Update a selected movie");
                System.out.println("5. Delete a selected movie");
                System.out.println("6. Exit");
                System.out.println("\nPlease enter your choice.");
                choice = input.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("Please enter an integer between 1 and 5");
            } finally {
                input.nextLine();
            }
        } while (choice < 1 || choice > 6);
        return choice;
    }

    private int getID(String action) {
        boolean valid = false;
        int id = 0;
        do {
            try {
                System.out.println("Please enter the ID of the movie to " + action);
                id = input.nextInt();
                valid = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter an integer for the id");
            } finally {
                input.nextLine();
            }
        } while (!valid);
        return id;
    }
}
