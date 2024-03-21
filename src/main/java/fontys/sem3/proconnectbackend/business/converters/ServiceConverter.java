package fontys.sem3.proconnectbackend.business.converters;

import fontys.sem3.proconnectbackend.domain.Service;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;

public class ServiceConverter {
    public static Service convert(ServiceEntity serviceEntity) {
        Service service = new Service();
        service.setId(serviceEntity.getId());
        service.setTitle(serviceEntity.getTitle());
        service.setDescription(serviceEntity.getDescription());
        service.setPrice(serviceEntity.getPrice());
        service.setExpert(ExpertConverter.convert(serviceEntity.getExpert()));
        service.setTags(serviceEntity.getTags().stream().map(TagConverter::convert).toList());

        return service;
    }
}
