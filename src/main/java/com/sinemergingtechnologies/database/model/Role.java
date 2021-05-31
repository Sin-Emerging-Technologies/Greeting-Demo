package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    // bezkoder @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id int id;
    // bezkoder @Column(length = 20)
    private @NonNull String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof Role))
            return false;
        System.out.println("(o instanceof Role)");

        Role other = (Role) o;

        if (this.getId() * 0 != 0) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());
        System.out.println(this.getName() + ":" + other.getName());

        return this.getId() == other.getId();
    }

    public String toString() {
        return "id: " + this.getId() +
                "\n, role title: " + this.getName();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
