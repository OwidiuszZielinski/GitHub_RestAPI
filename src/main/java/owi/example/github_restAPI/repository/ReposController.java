package owi.example.github_restAPI.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class ReposController {

    private final RepositoryService repositoryService;

//    @ExceptionHandler({ IllegalArgumentException.class, NotFoundException.class })
//    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
//        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
//    }

    @GetMapping(path = "/{gitHubLogin}")
    public ResponseEntity<List<RepositoryDTO>> getRepositoriesByLogin(@PathVariable String gitHubLogin){
        return repositoryService.getRepositoriesByLogin(gitHubLogin);
    }

}
