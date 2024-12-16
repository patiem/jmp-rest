package com.epa.m.multi.jmprest.controller;

import com.epa.m.multi.jmprest.model.User;
import com.epa.m.multi.jmprest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")

// @Transactional
class UserRestControllerFunctionalTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository repository;

    private final List<User> users = List.of(
            new User("Uncle", "Bob", 99),
            new User("Mac", "Donald", 18),
            new User("Chicken", "Nuggets", 35));

    private List<Long> ids;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.saveAll(users);
        ids = repository.findAll().stream().map(User::getId).toList();
    }

    @Test
    void getAllUsers() {
        client.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .consumeWith(
                        response -> assertThat(response.getResponseBody())
                                .hasSize(3)
                                .containsAll(users)
                );
    }

    @Test
    void getUsersThatExist() {
        users.forEach(product -> client.get()
                .uri("/users/{id}", product.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(product)
        );
    }

    @Test
    void getUserThatDoesNotExist() {
        assertThat(ids).doesNotContain(999L);
        client.get()
                .uri("/users/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void insertUser() {
        User user = new User("Ice", "Cream", 1);
        client.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo("Ice")
                .jsonPath("$.lastName").isEqualTo("Cream")
                .jsonPath("$.age").isEqualTo(1);
    }

    @Test
    void updateUser() {
        User product = users.get(0);
        product.setAge(product.getAge() + 1);

        client.put()
                .uri("/users/{id}", product.getId())
                .body(Mono.just(product), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .consumeWith(System.out::println);
    }

    @Test
    void deleteSingleUser() {
        // given:
        client.get()
                .uri("/users/{id}", ids.get(0))
                .exchange()
                .expectStatus().isOk();

        // when:
        client.delete()
                .uri("/users/{id}", ids.get(0))
                .exchange()
                .expectStatus().isNoContent();

        // then:
        client.get()
                .uri("/users/{id}", ids.get(0))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteAllUsers() {
        // when:
        client.delete()
                .uri("/users")
                .exchange()
                .expectStatus().isNoContent();

        // then:
        client.get()
                .uri("/users")
                .exchange()
                .expectBodyList(User.class)
                .hasSize(0);
    }
}