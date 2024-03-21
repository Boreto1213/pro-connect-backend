package fontys.sem3.proconnectbackend.business.converters;

import fontys.sem3.proconnectbackend.domain.Client;
import fontys.sem3.proconnectbackend.domain.enums.Role;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientConverter {
    public static Client convert(ClientEntity clientEntity) {
        Client client = new Client();
        client.setId(clientEntity.getId());
        client.setEmail(clientEntity.getEmail());
        client.setPassword(clientEntity.getPassword());
        client.setFirstName(clientEntity.getFirstName());
        client.setLastName(clientEntity.getLastName());
        client.setCity(clientEntity.getCity());
        client.setAddress(clientEntity.getAddress());
        client.setPhone(clientEntity.getPhone());
        client.setRole(Role.ROLE_Client);
        client.setProfileImageUrl(clientEntity.getProfileImageUrl());

        return client;
    }
}