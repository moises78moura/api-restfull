package br.com.indutiva.api.resource;


import br.com.indutiva.api.dto.UserDTO;
import br.com.indutiva.model.entity.User;
import br.com.indutiva.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Api(value = "Api de teste criado para a Indutiva", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, tags = {"User"})
public class UserController {

    private final UserService service;
    private final ModelMapper mapper;

    public UserController(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria um usuário", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO create(@RequestBody @Valid UserDTO dto){

        User savedUser = mapper.map(dto, User.class);
        savedUser = service.save(savedUser);
        return mapper.map(savedUser, UserDTO.class);

    }

}
