package br.com.indutiva.service;

import br.com.indutiva.model.entity.User;
import br.com.indutiva.model.exceptions.BusinessException;
import br.com.indutiva.model.repository.UserRepository;
import br.com.indutiva.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    UserService service;

    @MockBean
    private UserRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new UserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve lançar erro quando tentar salvar um usuário com email existente")
    public void shouldNotSaveUserWithEmailExisting(){
        //$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a = admin
        User user = User.builder().name("Moises").email("moises@ig.com").password("$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a").build();
        when(repository.existsUserByEmail(Mockito.anyString())).thenReturn(true);

        Throwable throwable = catchThrowable(() -> service.save(user));
        assertThat(throwable)
                .isInstanceOf(BusinessException.class)
                        .hasMessage("Email já cadastrado.");

        Mockito.verify(repository, Mockito.never()).save(user);
    }

    @Test
    @DisplayName("Deve salvar um usuário")
    public void saveUserTest(){
        //$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a = admin
        User user = User.builder().name("Moises").email("moises@ig.com").password("$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a").build();
        when(repository.existsUserByEmail(Mockito.anyString())).thenReturn(false);
        when(repository.save(user)).thenReturn(User.builder().id(1l).name("Moises").email("moises@ig.com").password("$2a$10$qVE9Z6iiLav2FIDy.pNlceIKyQVWyDpAEXb44Ru/KTBA31uBiVr6a").build());

        User savedUser = service.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Moises");
        assertThat(savedUser.getEmail()).isEqualTo("moises@ig.com");

    }

}
