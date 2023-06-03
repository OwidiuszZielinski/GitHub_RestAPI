package owi.example.github_restAPI.app;

import org.springframework.stereotype.Service;
import owi.example.github_restAPI.repository.GitRepository;
import owi.example.github_restAPI.repository.RepositoryDTO;


import java.util.List;

@Service
public class AppService {
    public List<RepositoryDTO> getRepositoriesByLogin(String gitHubLogin) {
        return RepositoryDTO.fromApiGitHub(gitHubLogin);
    }
}
