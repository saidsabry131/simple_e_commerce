package com.example.e_commerce.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(nullable = false,name = "first_name")
    private String firstName;

    private String lastName;
    @Column(nullable = false,unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid _email}")
    private String email;

    @NotEmpty
    private String password;

    @ManyToMany(cascade =CascadeType.MERGE,fetch = FetchType.EAGER )
    @JoinTable(
        name="user_role",
        joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName ="id" )
    )
    private List<UserRoles> userRoles;



    public MyUser(MyUser myUser)
    {
        this.firstName=myUser.getFirstName();
        this.lastName=myUser.getLastName();
        this.email=myUser.getEmail();
        this.password=myUser.getPassword();
        this.userRoles=myUser.getUserRoles();
    }


}
