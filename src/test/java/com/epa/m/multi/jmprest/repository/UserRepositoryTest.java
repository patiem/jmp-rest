package com.epa.m.multi.jmprest.repository;

import com.epa.m.multi.jmprest.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void countUsers() {
        assertEquals(7, repository.count());
    }

    @Test
    void findById() {
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void findAll() {
        List<User> users = repository.findAll();
        assertEquals(7, users.size());
    }

    @Test
    void insertProduct() {
        User user = new User("Unkle", "Bob", 99);
        repository.save(user);
        assertAll(
                () -> assertNotNull(user.getId()),
                () -> assertEquals(8, repository.count())
        );
    }

    @Test
    void deleteProduct() {
        repository.deleteById(1L);
        assertEquals(6, repository.count());
    }

    @Test
    void deleteAllInBatch() {
        repository.deleteAllInBatch();
        assertEquals(0, repository.count());
    }

    @Test
    void deleteAllUsers() {
        repository.deleteAll();
        assertEquals(0, repository.count());
    }
}