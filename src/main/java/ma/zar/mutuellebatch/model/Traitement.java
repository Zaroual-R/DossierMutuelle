package ma.zar.mutuellebatch.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Traitement {
    private String codeBarre;
    private boolean existe;
    private String nomMedicament;
    private String typeMedicament;
    private double prixMedicament;
    private MedicamentReferenctiel medicamentReferenctiel;
    private double montantRemboursement;
}
