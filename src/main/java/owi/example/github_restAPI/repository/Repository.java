package owi.example.github_restAPI.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import owi.example.github_restAPI.branch.Branch;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {


        private String name;
        private boolean fork;
        private String url;
}
