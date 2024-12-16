package com.epa.m.multi.jmprest.controller;

import com.epa.m.multi.jmprest.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles(value = "test")
class UserRestControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private JdbcClient jdbcClient;


    private List<Long> getIds() {
        return jdbcClient.sql("SELECT id FROM userr")
                .query(Long.class)
                .list();
    }

    private User getUser(Long id) {
        return jdbcClient.sql("SELECT * FROM userr WHERE id = ?")
                .param(id)
                .query(User.class)
                .single();
    }

    @Test
    void getAllUsers() {
        List<Long> userIds = getIds();
        System.out.println("There are " + userIds.size() + " users in the database.");
        client.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(7)
                .consumeWith(System.out::println);
    }

    @ParameterizedTest(name = "User ID: {0}")
    @MethodSource("getIds")
    void getUsersThatExist(Long id) {
        client.get()
                .uri("/users/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id);
    }

    @Test
    void getUserThatDoesNotExist() {
        List<Long> userIds = getIds();
        assertThat(userIds).doesNotContain(999L);
        System.out.println("There are " + userIds.size() + " users in the database.");
        client.get()
                .uri("/users/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void insertUser() {
        List<Long> userIds = getIds();
        assertThat(userIds).doesNotContain(999L);
        System.out.println("There are " + userIds.size() + " users in the database.");
        User user = new User("Unkle", "Bob", 99);
        client.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo("Unkle")
                .jsonPath("$.lastName").isEqualTo("Bob")
                .jsonPath("$.age").isEqualTo(99);
    }

    @Test
    void updateUser() {
        User user = getUser(getIds().get(0));
        user.setAge(user.getAge() + 1);

        client.put()
                .uri("/users/{id}", user.getId())
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .consumeWith(System.out::println);
    }

    @Test
    void deleteSingleUser() {
        List<Long> ids = getIds();
        System.out.println("There are " + ids.size() + " users in the database.");
        if (ids.isEmpty()) {
            System.out.println("No ids found");
            return;
        }

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
        List<Long> ids = getIds();
        System.out.println("There are " + ids.size() + " users in the database.");

        // when:
        client.delete()
                .uri("/users")
                .exchange()
                .expectStatus().isNoContent();

        // then:
        client.get()
                .uri("/users")
                .exchange()
                .expectBodyList(User.class).hasSize(0);
    }
}