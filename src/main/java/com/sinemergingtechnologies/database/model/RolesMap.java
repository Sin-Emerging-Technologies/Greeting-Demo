package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "rolesmap")
public class RolesMap {

    private @Id int roles_map_id;
    private @NonNull Long userid;
    private @NonNull int roleid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof Role))
            return false;
        System.out.println("(o instanceof Role)");

        RolesMap other = (RolesMap) o;

        if (this.getRoles_map_id() * 0 != 0) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getRoles_map_id() + ":" + other.getRoles_map_id());
        System.out.println(this.getRoleid() + ":" + other.getRoleid());

        return this.getRoles_map_id() == other.getRoles_map_id();
    }

    public String toString() {
        return "id: " + this.getRoles_map_id() +
            ",\nrole title: " + this.getRoleid() +
            ",\nrole title: " + this.getUserid();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
