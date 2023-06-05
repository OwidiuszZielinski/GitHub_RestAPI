package owi.example.github_restAPI.branch;

import lombok.Getter;
import owi.example.github_restAPI.commit.Commit;

@Getter
public class Branch {
    private String name;
    private Commit commit;

}
