package cicosy.templete.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "punchout_configs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PunchoutConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "catalog_id", unique = true)
    private Catalog catalog;

    private String endpointUrl;
    private String identity;
    private String sharedSecret;
    private String protocol; // cXML, OCI
    private Boolean enabled;
}


