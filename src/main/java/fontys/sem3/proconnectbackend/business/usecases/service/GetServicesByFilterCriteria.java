package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.business.dtos.GetServicesByPageResponse;

import java.math.BigDecimal;

public interface GetServicesByFilterCriteria {
    GetServicesByPageResponse getServicesByQueryTitleAndPriceRangeAndPage(String titleQuery, BigDecimal minPrice, BigDecimal maxPrice, int page);
}
