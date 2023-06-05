package owi.example.github_restAPI.repository;

import lombok.Data;
import owi.example.github_restAPI.branch.Branch;
import owi.example.github_restAPI.owner.Owner;

@Data
public class GitRepository {
    private String name;
    private boolean fork;
    private Owner owner;
    private String url;
    private Branch[] branches;

}

