package fontys.sem3.proconnectbackend.controller;

import fontys.sem3.proconnectbackend.business.dtos.CreateUserRequest;
import fontys.sem3.proconnectbackend.business.usecases.user.CreateUserUseCase;
import fontys.sem3.proconnectbackend.business.usecases.user.DeleteUserUseCase;
import fontys.sem3.proconnectbackend.business.usecases.user.GetUserByIdUseCase;
import fontys.sem3.proconnectbackend.business.usecases.user.UpdateUserUseCase;
import fontys.sem3.proconnectbackend.configuration.security.JwtService;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import jakarta.servlet.http.Cookie;


import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = JwtService.class)
public class UserConrollerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserByIdUseCase getUserByIdUseCaseMock;

    @MockBean
    private CreateUserUseCase createUserUseCaseMock;

    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @MockBean
    private DeleteUserUseCase deleteUserUseCase;


    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser
    void createUserShouldReturn201ResponseWithSuccessMessage() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .email("g.alex@gmail.com")
                .password("asdASD123@")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+31616022126")
                .city("Eindhoven")
                .address("Kruisakker 60A")
                .isExpert(true)
                .build();

        mockMvc
            .perform(
                post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                "email": "g.alex@gmail.com",
                "password": "asdASD123@",
                "firstName": "Alex",
                "lastName": "Getov",
                "phone": "+31616022126",
                "city": "Eindhoven",
                "address": "Kruisakker 60A",
                "isExpert": true
                }
                """)
            )
            .andDo(print())
                .andExpect(status().isCreated());

        verify(createUserUseCaseMock).createUser(request);
    }
}
