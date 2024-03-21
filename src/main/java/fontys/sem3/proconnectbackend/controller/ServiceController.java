package fontys.sem3.proconnectbackend.controller;

import fontys.sem3.proconnectbackend.business.dtos.CreateServiceRequest;
import fontys.sem3.proconnectbackend.business.dtos.GetServicesByPageResponse;
import fontys.sem3.proconnectbackend.business.dtos.UpdateServiceRequest;
import fontys.sem3.proconnectbackend.business.exeptions.DataOwnershipViolationException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.business.usecases.service.*;
import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.domain.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class ServiceController {
    private final CreateServiceUseCase createServiceUseCase;
    private final GetServiceByIdUseCase getServiceByIdUseCase;
    private final GetServicesByExpertIdUseCase getServicesByExpertIdUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;
    private final GetServicesByPageUseCase getServicesByPageUseCase;
    private final GetServicesByFilterCriteria getServicesByFilterCriteria;
    private final GetAllTagsUseCase getAllTagsUseCase;

    @PostMapping
    @RolesAllowed("Expert")
    public ResponseEntity<String> createService(@RequestBody @Valid CreateServiceRequest request) {
        try {
            createServiceUseCase.createService(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InvalidUserIdException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid expert id!");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable(value = "id") @Valid final Long id) {
        Optional<Service> service = getServiceByIdUseCase.getServiceById(id);

        return service.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/expert/{id}")
    public ResponseEntity<ArrayList<Service>> getServicesByExpertId(@PathVariable(value = "id") @Valid final Long id) {
        ArrayList<Service> services = getServicesByExpertIdUseCase.getServicesByExpertId(id);

        return ResponseEntity.ok().body(services);
    }

    @PutMapping
    @RolesAllowed({"Expert"})
    public ResponseEntity<Long> updateService(@RequestBody @Valid UpdateServiceRequest request){
        try {
            Optional<Long> response = updateServiceUseCase.updateService(request);

            return response.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (DataOwnershipViolationException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"Expert"})
    public ResponseEntity<Long> deleteService(@PathVariable(value = "id") @Valid final Long id) {
        try {
            Optional<Long> response = deleteServiceUseCase.deleteService(id);

            return response.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (DataOwnershipViolationException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping
    public ResponseEntity<GetServicesByPageResponse> getServicesByPage(@RequestParam(name = "page") @NotNull final int page) {
        GetServicesByPageResponse response = getServicesByPageUseCase.getServicesByPage(page);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<GetServicesByPageResponse> getServicesByFilterCriteriaAndPage(
            @RequestParam(name = "page") @NotNull final int page,
            @RequestParam(name = "titleQuery", required = false, defaultValue = "") @NotNull final String titleQuery,
            @RequestParam(name = "minPrice", required = false, defaultValue = "0") @NotNull final BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "1000000000") @NotNull final BigDecimal maxPrice) {
        GetServicesByPageResponse response = getServicesByFilterCriteria.getServicesByQueryTitleAndPriceRangeAndPage(titleQuery, minPrice, maxPrice, page);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/tags")
    public ResponseEntity<ArrayList<Tag>> getAllTags() {
        ArrayList<Tag> response = getAllTagsUseCase.getAllTags();

        return ResponseEntity.ok().body(response);
    }
}
