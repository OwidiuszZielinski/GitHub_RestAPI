package owi.example.github_restAPI.branch;

import lombok.Getter;
import org.springframework.web.client.RestTemplate;
import owi.example.github_restAPI.commit.Commit;
import owi.example.github_restAPI.repository.RepositoryDTO;

import java.util.List;

@Getter
public class Branch {
    private String name;
    private Commit commit;

}
