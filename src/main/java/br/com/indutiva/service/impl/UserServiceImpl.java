package br.com.indutiva.service.impl;


import br.com.indutiva.model.entity.User;
import br.com.indutiva.model.exceptions.BusinessException;
import br.com.indutiva.model.repository.UserRepository;
import br.com.indutiva.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {

        if (repository.existsUserByEmail(user.getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }
        return repository.save(user);
    }
}
