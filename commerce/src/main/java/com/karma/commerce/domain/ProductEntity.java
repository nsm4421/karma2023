package com.karma.commerce.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
public class ProductEntity extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) @Setter
    private String name;
    @Column(name="img_url")
    private String imgUrl;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Column(name="description", columnDefinition = "TEXT") @Setter
    private String description;
    @Column(name="price") @Setter
    private Long price;
}
