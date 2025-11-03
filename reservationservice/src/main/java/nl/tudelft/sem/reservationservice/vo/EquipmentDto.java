package nl.tudelft.sem.reservationservice.vo;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class EquipmentDto {

    private String equipmentId;
    private String name;
    private Set<SportDto> sports;

    /**
     * Hash code method.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return equipmentId.hashCode();
    }

    /**
     * Equals method for this object.
     *
     * @param o The object
     * @return True iff equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipmentDto that = (EquipmentDto) o;

        return equipmentId.equals(that.equipmentId);
    }

    /**
     * ToString method.
     *
     * @return The toString
     */
    @Override
    public String toString() {
        return "EquipmentDto{" + "equipmentId='" + equipmentId + '\'' + '}';
    }

}
