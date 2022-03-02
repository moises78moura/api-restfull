package br.com.indutiva.model.repository;

import br.com.indutiva.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando tentat salvar um usuário com um email existente")
    public void returnTrueWhenEmailExists(){

        String email = "moises@ig.com";
        //$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a = admin
        User user = User.builder().name("Moises").email("moises@ig.com").password("$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a").build();

        entityManager.persist(user);

        boolean existsUserByEmail = repository.existsUserByEmail(email);

        assertThat(existsUserByEmail).isTrue();

    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso.")
    public void saveUserTest(){
        User user = User.builder().name("Moises").email("moises@ig.com").password("123456").build();

        User savedUser = repository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Moises");

    }

    @Test
    @DisplayName("Deve validar os campos obrigatórios.")
    public void shouldNotSaveUser(){

        User user = User.builder().name("").email("").password("").build();

        Exception exception = assertThrows(ConstraintViolationException.class,
                () -> {
                    repository.save(user);
                    entityManager.flush();
                } );

        assertTrue(exception.getMessage().contains("Campo nome Obrigatório"));

    }

}
