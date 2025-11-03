package nl.tudelft.sem.facilityservice.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String equipmentId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "equipment_sport",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_name"))
    private Set<Sport> sports;

    public Equipment() {
    }

    /**
     * Equipment constructor.
     *
     * @param equipmentDto The equipmentDto to construct from
     */
    public Equipment(EquipmentDto equipmentDto) {
        this.equipmentId = equipmentDto.getId();
        this.name = equipmentDto.getName();
        this.sports = equipmentDto.getSports();
    }

    public EquipmentDto getEquipmentDto() {
        EquipmentDto equipmentDto = new EquipmentDto(equipmentId, name, sports);
        return equipmentDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipment equipment = (Equipment) o;
        return Objects.equals(equipmentId, equipment.equipmentId)
            && Objects.equals(name, equipment.name) && Objects.equals(sports, equipment.sports);
    }

    @Override
    public String toString() {
        return "Equipment{"
             + "equipment_id='" + equipmentId + '\''
             + ", name='" + name + '\''
             + ", sports:" + sports
             + '}';
    }
}
