package cicosy.templete.domain;

public enum CatalogStatus {
    DRAFT("Draft - Not yet active"),
    ACTIVE("Active - Available for use"),
    INACTIVE("Inactive - Temporarily disabled"),
    EXPIRED("Expired - No longer valid");

    private final String description;

    CatalogStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean canBeActivated() {
        return this == DRAFT || this == INACTIVE;
    }

    public boolean canBeDeactivated() {
        return this == ACTIVE;
    }
}