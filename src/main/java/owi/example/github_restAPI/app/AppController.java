package owi.example.github_restAPI.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import owi.example.github_restAPI.exceptions.ExceptionResponse;
import owi.example.github_restAPI.exceptions.GitHubUserNotExisting;
import owi.example.github_restAPI.repository.GitRepository;
import owi.example.github_restAPI.repository.RepositoryDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AppController {

    private final AppService appService;

    @ExceptionHandler({ GitHubUserNotExisting.class })
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ExceptionResponse(404,ex.getMessage()), HttpStatus.NOT_FOUND);
    }


bab
    @GetMapping(path = "{gitHubLogin}")
    public List<RepositoryDTO> getRepositoriesByLogin(@PathVariable String gitHubLogin){
        return appService.getRepositoriesByLogin(gitHubLogin);
    }


}
