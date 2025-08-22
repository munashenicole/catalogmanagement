package cicosy.templete.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "price_lists")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    private String name;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceListItem> items;
}


