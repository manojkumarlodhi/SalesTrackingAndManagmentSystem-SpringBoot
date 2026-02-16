package com.company.salestracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deals")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", unique = true)
    @ToString.Exclude
    private Lead lead;

    @Enumerated(EnumType.STRING)
    private DealStatus dealStage = DealStatus.NEGOTIATION;

    private Double expectedAmount;

    private Double actualAmount;

    private LocalDate expectedClosingDate;

    private LocalDate actualClosingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    @ToString.Exclude
    private User assignedTo;

    private String notes;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "deal", cascade = CascadeType.ALL)
    private Sale sale;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}