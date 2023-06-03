package owi.example.github_restAPI.repository;


import lombok.*;
import org.springframework.web.client.RestTemplate;
import owi.example.github_restAPI.branch.Branch;
import owi.example.github_restAPI.owner.Owner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ToString
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RepositoryDTO {
    private String name;
    private boolean fork;
    private Owner owner;
    private Branch[] branches;


    private static List<GitRepository> setRepos(String gitHubLogin) {
        String resourceUrl = "https://api.github.com/users/" + gitHubLogin +"/repos";
        RestTemplate restTemplate = new RestTemplate();
        GitRepository[] response = restTemplate.getForObject(resourceUrl, GitRepository[].class);
        return setBranches(response);
    }
    private static List<GitRepository> setBranches(GitRepository[] response){
        List<GitRepository> repositories = Arrays.stream(Objects.requireNonNull(response))
                .filter(e -> !e.isFork()).toList();
        for(GitRepository repo : repositories){
            String resourceUrl = repo.getUrl() + "/branches";
            RestTemplate restTemplate = new RestTemplate();
            Branch[] branches = restTemplate.getForObject(resourceUrl,Branch[].class);
            repo.setBranches(branches);

        }
        return repositories;
    }

    public static RepositoryDTO from(GitRepository repository){
        return RepositoryDTO.builder().name(repository.getName())
                .fork(repository.isFork())
                .owner(repository.getOwner())
                .branches(repository.getBranches())
                .build();
    }


    public static List<RepositoryDTO> fromApiGitHub(String gitHubLogin) {
        List<GitRepository> repositories = setRepos(gitHubLogin);
        return repositories.stream().map(RepositoryDTO::from).toList();
    }
}
