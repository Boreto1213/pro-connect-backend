package fontys.sem3.proconnectbackend.controller;

import fontys.sem3.proconnectbackend.business.dtos.CreateUserRequest;
import fontys.sem3.proconnectbackend.business.dtos.UpdateUserRequest;
import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.exeptions.EmailAlreadyExistsException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.business.usecases.user.*;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.domain.User;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserProfileImageUseCase updateUserProfileImageUseCase;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequest request) {
        try {
            createUserUseCase.createUser(request);
        } catch (EmailAlreadyExistsException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") @Valid final Long id) {
        Optional<User> user = getUserByIdUseCase.getUserById(id);

        return user.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping()
    public ResponseEntity<Long> updateUser(@RequestBody @Valid UpdateUserRequest request) {
        try {
            Optional<Long> response = updateUserUseCase.updateUser(request);

            return response.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (DataOwnershipViolationException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/update/image/{id}")
    public ResponseEntity<?> updateUserProfileImage(@PathVariable(value = "id") @NotNull final Long id, @RequestParam("image") MultipartFile image) {
        try {
            updateUserProfileImageUseCase.updateProfileImage(id, image);
            return ResponseEntity.ok().build();
        } catch (IOException exception) {
            return ResponseEntity.internalServerError().build();
        } catch (InvalidUserIdException exception) {
            return ResponseEntity.badRequest().build();
        } catch (DataOwnershipViolationException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteUserById(@PathVariable(value = "id") @NotNull final Long id) {
        try {
            Optional<Long> response = deleteUserUseCase.deleteUser(id);

            return  response.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (DataOwnershipViolationException e) {
            return ResponseEntity.status(403).build();
        }
    }
}
