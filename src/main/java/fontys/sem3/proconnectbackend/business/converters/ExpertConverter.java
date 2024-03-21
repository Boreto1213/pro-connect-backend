package fontys.sem3.proconnectbackend.business.converters;

import fontys.sem3.proconnectbackend.domain.Expert;
import fontys.sem3.proconnectbackend.domain.enums.Role;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpertConverter {
    public static Expert convert(ExpertEntity expertEntity) {
        Expert expert = new Expert();
        expert.setId(expertEntity.getId());
        expert.setEmail(expertEntity.getEmail());
        expert.setPassword(expertEntity.getPassword());
        expert.setFirstName(expertEntity.getFirstName());
        expert.setLastName(expertEntity.getLastName());
        expert.setCity(expertEntity.getCity());
        expert.setAddress(expertEntity.getAddress());
        expert.setPhone(expertEntity.getPhone());
        expert.setProfession(expertEntity.getProfession());
        expert.setYearsOfExperience(expertEntity.getYearsOfExperience());
        expert.setBio(expertEntity.getBio());
        expert.setLikes(expertEntity.getLikes());
        expert.setDislikes(expertEntity.getDislikes());
        expert.setProfileImageUrl(expertEntity.getProfileImageUrl());
        expert.setRole(Role.ROLE_Expert);
        return expert;
    }
}
