package com.freelance.platform.service;

import com.freelance.platform.domain.model.User;
import com.freelance.platform.domain.repository.UserRepository;
import com.freelance.platform.dto.request.RegisterRequest;
import com.freelance.platform.dto.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.freelance.platform.domain.enums.UserType.EMPLOYER;
import static com.freelance.platform.domain.enums.UserType.FREELANCER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void deveRegistrarUsuario() {

        RegisterRequest user = new RegisterRequest("Gabriel", "gabriel@gmail.com", "gabriel", EMPLOYER);

        User savedUser = User.builder()
                .id(1L)
                .name(user.getName())
                .email(user.getEmail())
                .password("encodedPassword")
                .userType(user.getUserType())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        UserResponse result = userService.register(user);

        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserType(), result.getUserType());

        verify(userRepository).save(any(User.class));
        verify(userRepository).existsByEmail(user.getEmail());
        verify(passwordEncoder).encode(user.getPassword());
    }

    @Test
    @DisplayName("Deve retornar usuário pelo ID")
    void deveRetornarUsuarioPeloId() {

        User user = User.builder()
                .id(1L)
                .name("Gabriel")
                .email("gabriel@gmail.com")
                .password("gabriel")
                .userType(EMPLOYER)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getEntityById(user.getId());

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserType(), result.getUserType());

        verify(userRepository).findById(user.getId());

    }

    @Test
    @DisplayName("Deve retornar usuário pelo Email")
    void deveRetornarUsuarioPeloEmail() {

        User user = User.builder()
                .id(1L)
                .name("Gabriel")
                .email("gabriel@gmail.com")
                .password("gabriel")
                .userType(EMPLOYER)
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = userService.getEntityByEmail(user.getEmail());

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserType(), result.getUserType());

        verify(userRepository).findByEmail(user.getEmail());

    }


    @Test
    @DisplayName("Deve retornar usuário convertido em DTO pelo ID")
    void deveRetornarUsuariomDTOPeloId() {

        User user = User.builder()
                .id(1L)
                .name("Gabriel")
                .email("gabriel@gmail.com")
                .password("gabriel")
                .userType(EMPLOYER)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserResponse result = userService.findById(user.getId());

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserType(), result.getUserType());

        verify(userRepository).findById(user.getId());

    }

    @Test
    @DisplayName("Deve retornar todos usuários do tipo freelancer")
    void getAllFreelancers() {

        User userOne = User.builder()
                .id(1L)
                .name("Gabriel")
                .email("gabriel@gmail.com")
                .password("gabriel")
                .userType(FREELANCER)
                .build();
        User userTwo = User.builder()
                .id(1L)
                .name("Hellen")
                .email("hellencarol@gmail.com")
                .password("hellencarol")
                .userType(FREELANCER)
                .build();

        User userTree = User.builder()
                .id(1L)
                .name("Silvana")
                .email("silvana@gmail.com")
                .password("silvana")
                .userType(EMPLOYER)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(userOne, userTwo, userTree));

        List<UserResponse> result = userService.findAllFreelancers();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(u -> u.getUserType().name().equals("FREELANCER")));
        assertTrue(result.stream().anyMatch(u -> u.getEmail().equals("gabriel@gmail.com")));
        assertEquals(userOne.getName(), result.get(0).getName());
        assertEquals(userTwo.getName(), result.get(1).getName());

    }

    @Test
    @DisplayName("Deve deletar usuário pelo ID")
    void deveDeletarUsuarioPeloId() {

        User user = User.builder()
                .id(1L)
                .name("Gabriel")
                .email("gabriel@gmail.com")
                .password("gabriel")
                .userType(FREELANCER)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.delete(user.getId());

        verify(userRepository).findById(user.getId());
        verify(userRepository).delete(user);

    }
}