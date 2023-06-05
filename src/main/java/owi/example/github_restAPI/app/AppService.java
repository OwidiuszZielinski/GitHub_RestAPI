package owi.example.github_restAPI.app;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import owi.example.github_restAPI.branch.Branch;
import owi.example.github_restAPI.exceptions.GitHubUserNotExisting;
import owi.example.github_restAPI.repository.GitRepository;
import owi.example.github_restAPI.repository.RepositoryDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AppService {

    private final RestTemplate restTemplate;


    public AppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<RepositoryDTO> getRepositoriesByLogin(String gitHubLogin) {
        return RepositoryDTO.fromApiGitHub(getReposFromAPI(gitHubLogin));
    }

    private List<GitRepository> getReposFromAPI(String gitHubLogin) {


        String resourceUrl = "https://api.github.com/users/" + gitHubLogin + "/repos";
        GitRepository[] gitRepositories;
        try {
            gitRepositories = restTemplate.getForObject(resourceUrl, GitRepository[].class);
        } catch (HttpClientErrorException e) {
            throw new GitHubUserNotExisting("GitHub user not found");
        }
        return setBranchesInRepositories(gitRepositories);

    }


    private List<GitRepository> setBranchesInRepositories(GitRepository[] gitRepositories) {
        List<GitRepository> repositories = checkFork(gitRepositories);

        for (GitRepository repo : repositories) {
            String resourceUrl = repo.getUrl() + "/branches";
            Branch[] branches = restTemplate.getForObject(resourceUrl, Branch[].class);
            repo.setBranches(branches);

        }
        return repositories;
    }

    private static List<GitRepository> checkFork(GitRepository[] gitRepositories) {
        return Arrays.stream(Objects.requireNonNull(gitRepositories))
                .filter(e -> !e.isFork()).toList();
    }

}
