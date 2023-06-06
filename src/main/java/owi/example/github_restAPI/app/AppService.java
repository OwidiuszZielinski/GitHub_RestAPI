package owi.example.github_restAPI.app;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import owi.example.github_restAPI.branch.Branch;
import owi.example.github_restAPI.exceptions.GitHubUserNotExisting;
import owi.example.github_restAPI.repository.GitRepository;
import owi.example.github_restAPI.repository.RepositoryDTO;
import reactor.core.publisher.Flux;
import java.util.List;

@Service
public class AppService {


    private final WebClient.Builder webClientBuilder;
    private static final String TOKEN = "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";
    private static final String REPOS_URL ="https://api.github.com/users/%s/repos";
    private static final String BRANCHES_URL ="/branches";


    public AppService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<RepositoryDTO> getRepositoriesByLogin(String gitHubLogin) {
        return RepositoryDTO
                .fromApiGitHub(
                        setBranchesForRepository(getUserRepository(gitHubLogin)));
    }

    public Flux<GitRepository> getUserRepository(String gitHubLogin) {

        String resourceUrl = String.format(REPOS_URL,gitHubLogin);
        return checkFork(webClientBuilder.build()
                .get()
                .uri(resourceUrl)
                .headers(h -> h.setBearerAuth(TOKEN))
                .retrieve()
                .onStatus(
                        HttpStatus -> HttpStatus.isSameCodeAs(HttpStatusCode.valueOf(404)),
                        clientResponse -> {
                            throw new GitHubUserNotExisting("GitHub user not found");
                        }
                )
                .onStatus(
                        HttpStatus -> HttpStatus.isSameCodeAs(HttpStatusCode.valueOf(403)),
                        clientResponse -> {
                            throw new GitHubUserNotExisting("Query limit reached");
                        }
                )
                .bodyToFlux(GitRepository.class));
    }

    public List<Branch> getBranchesForRepository(GitRepository repository) {
        String resourceUrl = repository.getUrl() + BRANCHES_URL;
        Flux<Branch> branchFlux = webClientBuilder.build()
                .get()
                .uri(resourceUrl)
                .headers(h -> h.setBearerAuth(TOKEN))
                .retrieve()
                .bodyToFlux(Branch.class);
        return branchFlux.collectList().block();
    }

    private Flux<GitRepository> setBranchesForRepository(Flux<GitRepository> gitRepositories) {
        return gitRepositories.doOnNext(repository ->
            repository
                    .setBranches(getBranchesForRepository(repository)));

    }

    private static Flux<GitRepository> checkFork(Flux<GitRepository> gitRepositories) {
        return gitRepositories
                .filter(e -> !e.isFork());
    }

}
