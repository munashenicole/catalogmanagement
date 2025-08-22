package cicosy.templete.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contract_prices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractId;
    private String supplierName;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private CatalogItem item;

    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
}


