package cicosy.templete.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "search_config")
@Getter
@Setter
@NoArgsConstructor
public class SearchConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String facetsJson; // JSON string representing facets configuration

    @Lob
    private String synonyms; // newline-separated synonyms
}


