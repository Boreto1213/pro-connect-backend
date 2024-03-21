package fontys.sem3.proconnectbackend.configuration.security;

import fontys.sem3.proconnectbackend.persistence.entity.ReviewEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class DataOwnershipValidator {
    public static boolean canUserModifyServiceResource(ServiceEntity serviceEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(serviceEntity.getExpert().getEmail());
    }

    public static boolean canUserModifyUserResource(UserEntity userEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(userEntity.getEmail());
    }

    public static boolean canUserModifyReviewResource(ReviewEntity reviewEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(reviewEntity.getCreatedBy().getEmail());
    }
}
