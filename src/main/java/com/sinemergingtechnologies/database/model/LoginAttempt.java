package com.sinemergingtechnologies.database.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class LoginAttempt {

    private @NonNull String email;
    private @NonNull String password;
    private @NonNull String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof User))
            return false;
        System.out.println("(o instanceof User)");

        User other = (User) o;

        if (this.getEmail() == null) return false;
        System.out.println("this.getEmail() != null");
        System.out.println(this.getEmail() + ":" + other.getEmail());
        System.out.println(this.getEmail() + ":" + other.getEmail());

        return this.getEmail().equals(other.getEmail());
    }

    public String toString() {
        return "email: " + this.getEmail() + "\npassword: " + this.getPassword() + "\nrole: " + this.getRole();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
