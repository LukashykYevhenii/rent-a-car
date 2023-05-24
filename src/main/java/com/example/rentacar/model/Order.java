package com.example.rentacar.model;


import java.util.Date;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;
    private Date startRent;
    private Date endRent;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private String comment;
    private boolean isDamages;
    private boolean isReturned;
    private Date returnedDate;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Damage damage;
}
