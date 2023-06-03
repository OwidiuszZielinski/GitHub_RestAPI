package owi.example.github_restAPI.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {
    public ResponseEntity<List<RepositoryDTO>> getRepositoriesByLogin(String gitHubLogin) {

        RepositoryDTO.fromApiGitHub(gitHubLogin);
        return null;
    }
}
