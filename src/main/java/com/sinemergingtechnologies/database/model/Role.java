package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    private @Id Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    private @NonNull String roleTitle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof Role))
            return false;
        System.out.println("(o instanceof Role)");

        Role other = (Role) o;

        if (this.getId() == null) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());
        System.out.println(this.getRoleTitle() + ":" + other.getRoleTitle());

        return this.getId().equals(other.getId());
    }

    public String toString() {
        return "id: " + this.getId() +
                "\n, role title: " + this.getRoleTitle();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
