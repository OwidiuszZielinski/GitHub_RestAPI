package owi.example.github_restAPI.exceptions;


public class GitHubUserNotExisting extends RuntimeException {
    public GitHubUserNotExisting(String message) {
        super(message);
    }
}
