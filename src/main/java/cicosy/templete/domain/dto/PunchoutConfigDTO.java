package cicosy.templete.domain.dto;

public record PunchoutConfigDTO(
    Long catalogId,
    String endpointUrl,
    String identity,
    String sharedSecret,
    String protocol,
    Boolean enabled
) {}


