package fontys.sem3.proconnectbackend.business.dtos;

import fontys.sem3.proconnectbackend.domain.Service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetServicesByPageResponse {
    @NotNull
    @Min(0)
    private int totalPages;
    @NotNull
    @Min(0)
    private int currentPage;
    @NotNull
    private List<Service> services;
}
