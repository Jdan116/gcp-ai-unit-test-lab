//package com.example.lab.service;
//
//import com.example.lab.exception.EmailAlreadyExistsException;
//import com.example.lab.model.UserDto;
//import com.example.lab.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.Mockito;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//    @InjectMocks
//    private UserService userService;
//
//    // test: should throw when email exists
//    @Test
//    void shouldThrowWhenEmailExists() {
//        UserDto dto = new UserDto("John Doe", "john@example.com");
//        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);
//
//        assertThrows(EmailAlreadyExistsException.class, () -> {
//            userService.createUser(dto);
//        });
//    }
//}
