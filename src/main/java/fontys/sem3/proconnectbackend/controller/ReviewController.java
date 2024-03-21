package fontys.sem3.proconnectbackend.controller;

import fontys.sem3.proconnectbackend.business.dtos.CreateReviewRequest;
import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidRequestDataException;
import fontys.sem3.proconnectbackend.business.exeptions.ServiceIdDoesNotExistException;
import fontys.sem3.proconnectbackend.business.usecases.review.CreateReviewUseCase;
import fontys.sem3.proconnectbackend.business.usecases.review.DeleteReviewUseCase;
import fontys.sem3.proconnectbackend.business.usecases.review.GetReviewsByServiceIdUseCase;
import fontys.sem3.proconnectbackend.domain.Review;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ReviewController {
    private final CreateReviewUseCase createReviewUseCase;
    private final GetReviewsByServiceIdUseCase getReviewsByServiceIdUseCase;
    private final DeleteReviewUseCase deleteReviewUseCase;
    @PostMapping
    @RolesAllowed({"Client"})
    public ResponseEntity<String> createReview(@RequestBody @Valid CreateReviewRequest request) {
        try {
            createReviewUseCase.createReview(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InvalidRequestDataException error) {
            return ResponseEntity.badRequest().body("Invalid request data");
        }
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<ArrayList<Review>> getReviewsByServiceId(@PathVariable(value = "id") @NotNull final Long id) {
        try {
            ArrayList<Review> reviews = getReviewsByServiceIdUseCase.getReviewsByServiceId(id);
            return ResponseEntity.ok().body(reviews);
        } catch (ServiceIdDoesNotExistException error) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"Client"})
    public ResponseEntity<Long> deleteReview(@PathVariable(value = "id") @NotNull final Long id) {
        try {
            Optional<Long> response = deleteReviewUseCase.deleteReview(id);

            return response.map(value -> ResponseEntity.ok().body(response.get())).orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (DataOwnershipViolationException e) {
            return ResponseEntity.status(403).build();
        }
    }
}
