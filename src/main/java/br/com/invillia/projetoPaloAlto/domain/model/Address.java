package br.com.invillia.projetoPaloAlto.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class Address {

    @Id
    @Column(name="idt_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "des_district", nullable = false)
    private String district;

    @Column(name = "des_number", nullable = false)
    private String number;

    @Column(name = "des_city", nullable = false)
    private String city;

    @Column(name = "des_state", nullable = false)
    private String state;

    @Column(name = "des_zip_code", nullable = false, length = 8)
    private String zipCode;

    @Column(name = "flg_main", nullable = false)
    private Boolean main;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idt_legal_entity", referencedColumnName = "idt_legal_entity")
    private LegalEntity legalEntity;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idt_individual", referencedColumnName = "idt_individual")
    private  Individual individual;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode);
    }
}