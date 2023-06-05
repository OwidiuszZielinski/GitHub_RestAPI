## GitHub_RestAPI

## Requirements

For building and running the application you need:

- [JDK 1.7]
- [Maven 3]

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Endpoints 

```
http://localhost:8080/api/{GitHubLogin}
```

When we set Accept header to application/xml, we recive : 

```
{
	"status": 406,
	"Message":" Bad Accept header"
}
```
When we set bad {GitHubLogin} we recive : 

```
{
    "status": 404,
    "message": "GitHub user not found"
}
```

Happy path gives us result like this : 

```
 {
        "name": "-Console-Storehouse",
        "fork": false,
        "owner": {
            "login": "OwidiuszZielinski"
        },
        "branches": [
            {
                "name": "master",
                "commit": {
                    "sha": "a78da28b0122c738f266d0265e52e093d995b0a7"
                }
            },
            {
                "name": "settery",
                "commit": {
                    "sha": "2f212b9a1dbf4426c834c941b6e5dee26e101b88"
                }
            }
        ]
    },
}
```
