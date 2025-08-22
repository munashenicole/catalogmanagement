package cicosy.templete.domain.dto;

public record CategoryDTO(
    Long id,
    String name,
    String description,
    Long parentId
) {}


