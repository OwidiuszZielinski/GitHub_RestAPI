package owi.example.github_restAPI.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owi.example.github_restAPI.exceptions.ExceptionResponse;
import owi.example.github_restAPI.exceptions.GitHubUserNotExisting;
import owi.example.github_restAPI.repository.RepositoryDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AppController {

    private final AppService appService;

    @ExceptionHandler({ GitHubUserNotExisting.class })
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ExceptionResponse(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = "{gitHubLogin}", produces = "application/json")
    public List<RepositoryDTO> getRepositoriesByLogin(@PathVariable String gitHubLogin, @RequestHeader HttpHeaders headers) {
        return appService.getRepositoriesByLogin(gitHubLogin);

    }


}
