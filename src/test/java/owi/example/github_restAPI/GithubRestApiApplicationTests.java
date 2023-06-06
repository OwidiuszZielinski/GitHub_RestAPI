package owi.example.github_restAPI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import owi.example.github_restAPI.app.AppService;
import owi.example.github_restAPI.branch.Branch;
import owi.example.github_restAPI.exceptions.GitHubUserNotExisting;
import owi.example.github_restAPI.repository.GitRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

@SpringBootTest
class GithubRestApiApplicationTests {
    private static final String TOKEN = "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";
    private static final String EXAMPLE_LOGIN = "login123";
    private static final String BASE_URL = "https://api.github.com/";


    private AppService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = Mockito.mock(AppService.class);

    }

    @Test
    public void gitHubAPIConnection() {
        //given
        WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();
        //when
        ResponseEntity<String> response = webClient.get().uri(BASE_URL).retrieve().toEntity(String.class).block();
        //then
        assert response != null;
        Assertions.assertEquals(200, response.getStatusCode().value());

    }

    @Test
    public void checkAuthorizationValidToken() {
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(BASE_URL).build();
        webTestClient.get().uri(BASE_URL)
                .headers(http -> http.setBearerAuth(TOKEN))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);

    }
    @Test
    public void testInvalidToken() {
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(BASE_URL).build();
        webTestClient.get().uri(BASE_URL)
                .headers(http -> http.setBearerAuth("WRONG_TOKEN"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }



    @Test
    public void getRepositoriesValidUsername() {
        // given
        service = Mockito.mock(AppService.class);
        Flux<GitRepository> expectedRepositories = Flux.just(
                new GitRepository(),
                new GitRepository(),
                new GitRepository()
        );
        when(service.getUserRepository(EXAMPLE_LOGIN)).thenReturn(expectedRepositories);

        // when
        Flux<GitRepository> result = service.getUserRepository(EXAMPLE_LOGIN);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertSame(expectedRepositories, result);
        Assertions.assertEquals(3, Objects.requireNonNull(result.collectList().block()).size());

        verify(service).getUserRepository(EXAMPLE_LOGIN);
        verifyNoMoreInteractions(service);
    }
    @Test
    public void getBranchesForRepository() {
        // given
        service = Mockito.mock(AppService.class);
        List<Branch> expectedBranches = List.of(new Branch(), new Branch(), new Branch());
        final GitRepository repository = new GitRepository();
        when(service.getBranchesForRepository(repository)).thenReturn(expectedBranches);

        // when
        List<Branch> result = service.getBranchesForRepository(repository);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertSame(expectedBranches, result);
        Assertions.assertEquals(3, result.size());
        verify(service).getBranchesForRepository(repository);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getRepositoriesInValidUsername() {
        // given
        service = Mockito.mock(AppService.class);
        doThrow(new GitHubUserNotExisting("GitHub user not found"))
                .when(service)
                .getRepositoriesByLogin(EXAMPLE_LOGIN);

        // when and then
        GitHubUserNotExisting exception = Assertions.assertThrows(
                GitHubUserNotExisting.class,
                () -> service.getRepositoriesByLogin(EXAMPLE_LOGIN)
        );
        Assertions.assertEquals("GitHub user not found", exception.getMessage());

        verify(service).getRepositoriesByLogin(EXAMPLE_LOGIN);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void forkFalseCheckerTrueValue(){
        //given
        final int expected = 0;
        Flux<GitRepository> repository = Flux.just(new GitRepository(),new GitRepository(),new GitRepository());
        repository.doOnNext(repo->repo.setFork(true)).subscribe();
        //when
        final int falseElements = Objects.requireNonNull(repository.filter(repo -> !repo.isFork()).count().block()).intValue();
        //then
        Assertions.assertEquals(falseElements,expected);

    }
    @Test
    public void forkFalseCheckerFalseValue(){
        //given
        final int expected = 3;
        Flux<GitRepository> repository = Flux.just(new GitRepository(),new GitRepository(),new GitRepository());
        //when
        final int falseElements = Objects.requireNonNull(repository.filter(repo -> !repo.isFork()).count().block()).intValue();
        //then
        Assertions.assertEquals(falseElements,expected);

    }


}
