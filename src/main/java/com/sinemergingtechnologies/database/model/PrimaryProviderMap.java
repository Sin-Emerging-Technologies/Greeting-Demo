package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "primaryprovidermap")
public class PrimaryProviderMap {

    private @NonNull UUID client_uuid; // = UUID.randomUUID();
    private @NonNull @Id Long id; // = client_uuid.getMostSignificantBits() & Long.MAX_VALUE;

    private @NonNull UUID provider_uuid; // = UUID.randomUUID();
    private @NonNull Long provider_id; // = provider_uuid.getMostSignificantBits() & Long.MAX_VALUE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof PrimaryProviderMap))
            return false;
        System.out.println("(o instanceof PrimaryProviderMap)");

        PrimaryProviderMap other = (PrimaryProviderMap) o;

        if (this.getId() == null) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());

        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
