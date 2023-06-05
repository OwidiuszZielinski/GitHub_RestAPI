package owi.example.github_restAPI.branch;

import lombok.Getter;
import lombok.ToString;
import owi.example.github_restAPI.commit.Commit;

@ToString
@Getter
public class Branch {
    private String name;
    private Commit commit;

}
