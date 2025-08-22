package cicosy.templete.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hosted_catalogs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostedCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "catalog_id", unique = true)
    private Catalog catalog;

    private String fileLocation;

    private String format; // CSV, XLSX
}


