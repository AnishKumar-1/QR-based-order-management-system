package com.menu.module;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="category")
@EntityListeners(EntityListeners.class)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Items> items;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime last_updated;

}
