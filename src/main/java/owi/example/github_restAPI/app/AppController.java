package owi.example.github_restAPI.app;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import owi.example.github_restAPI.repository.RepositoryDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AppController {

    private final AppService appService;

//    @ExceptionHandler({ IllegalArgumentException.class, NotFoundException.class })
//    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
//        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
//    }

    @GetMapping(path = "/{gitHubLogin}")
    public List<RepositoryDTO> getRepositoriesByLogin(@PathVariable String gitHubLogin){
        return appService.getRepositoriesByLogin(gitHubLogin);
    }

}
