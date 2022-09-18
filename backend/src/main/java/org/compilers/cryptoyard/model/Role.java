package org.compilers.cryptoyard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @EqualsAndHashCode.Exclude
    private Set<User> users = new LinkedHashSet<>();

    /**
     * Empty constructor needed for JPA
     */
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}