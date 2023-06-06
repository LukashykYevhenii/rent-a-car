package com.example.rentacar.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@Entity
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idCar;
  @NotEmpty(message = "Field is empty. Please enter brand")
  @Length(max = 1024, message = "Brand name is to long")
  private String brand;
  @NotEmpty(message = "Field is empty. Please enter model")
  @Length(max = 1024, message = "Brand name is to long")
  private String model;
  private String color;
  @NotEmpty(message = "Field is empty. Please enter year")
  @Size(min = 4, max = 4, message = "Must be 4 numbers")
  private String year;
  @NotEmpty(message = "Field is empty. Please enter register number")
  private String regNumber;
  private Double rentalPrice;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
}
