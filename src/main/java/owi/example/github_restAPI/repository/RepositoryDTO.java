package owi.example.github_restAPI.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import owi.example.github_restAPI.branch.Branch;
import owi.example.github_restAPI.owner.Owner;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RepositoryDTO {
    private String name;
    private boolean fork;
    private Owner owner;
    private Branch[] branches;


    public static RepositoryDTO from(GitRepository repository) {
        return RepositoryDTO.builder().name(repository.getName())
                .fork(repository.isFork())
                .owner(repository.getOwner())
                .branches(repository.getBranches())
                .build();
    }

    public static List<RepositoryDTO> fromApiGitHub(List<GitRepository> repositories) {
        return repositories.stream().map(RepositoryDTO::from).toList();
    }
}
