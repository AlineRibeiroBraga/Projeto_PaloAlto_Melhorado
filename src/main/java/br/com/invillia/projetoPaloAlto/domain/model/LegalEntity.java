package br.com.invillia.projetoPaloAlto.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LegalEntity extends Customer{

    @Id
    @Column(name = "idt_legal_entity", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "des_document", nullable = false, unique = true, length = 14)
    private String document;

    @Column(name = "des_trade_name", nullable = false)
    private String tradeName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="individual_legal_entity",
            joinColumns = @JoinColumn(name ="idt_legal_entity"),
            inverseJoinColumns = @JoinColumn(name = "idt_individual")
    )
    private List<Individual> individuals;

    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL)
    private List<Address> addresses;
}
