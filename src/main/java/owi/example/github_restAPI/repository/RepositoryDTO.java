package owi.example.github_restAPI.repository;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import owi.example.github_restAPI.branch.Branch;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@RequiredArgsConstructor
public class RepositoryDTO {
    private String name;
    private String owner;
    private boolean fork;
    private Set<Branch> branches;


    public static RepositoryDTO fromApiGitHub(String gitHubLogin) {

        String resourceUrl = "https://api.github.com/users/" + gitHubLogin +"/repos";
        RestTemplate restTemplate = new RestTemplate();
        Repository[] response = restTemplate.getForObject(resourceUrl,Repository[].class);
        for(Repository x : Objects.requireNonNull(response)){
            System.out.println(x.getName());
            System.out.println(x.getUrl());

        }

        return null;
    }


}
