package nl.tudelft.sem.facilityservice.domain;


import java.util.Objects;
import java.util.Set;

public class EquipmentDto {
    private String equipmentId;
    private String name;
    private Set<Sport> sports;

    /**
     * EquipmentDto constructor.
     *
     * @param equipmentId The ID of the equipment
     * @param name The name of the equipment
     * @param sports The sports associated with the equipment
     */
    public EquipmentDto(String equipmentId, String name, Set<Sport> sports) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.sports = sports;
    }

    public EquipmentDto() {
    }

    public String getId() {
        return equipmentId;
    }

    public void setEquipmentId(String id) {
        this.equipmentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sport> getSports() {
        return sports;
    }

    public void setSport(Set<Sport> sport) {
        this.sports = sport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EquipmentDto that = (EquipmentDto) o;
        return Objects.equals(equipmentId, that.equipmentId) && Objects.equals(name, that.name);
    }
}
