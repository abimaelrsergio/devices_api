package com.abimael.deviceresources.entity;

import com.abimael.deviceresources.util.State;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "device_name", nullable = false)
    private String name;

    @Column(name = "device_brand", nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_state", nullable = false)
    private State state;
}
