package ma.zar.mutuellebatch.service.prescription;

import ma.zar.mutuellebatch.exceptions.ResourceAlreadyExistException;
import ma.zar.mutuellebatch.exceptions.ResourceNotFoundException;
import ma.zar.mutuellebatch.model.MedicamentReferenctiel;
import ma.zar.mutuellebatch.model.Traitement;

import java.util.List;

public interface IMedRefService {
    MedicamentReferenctiel addTraitement(MedicamentReferenctiel traitement) throws ResourceAlreadyExistException;
    MedicamentReferenctiel getTraitement(String codeBarre) throws ResourceNotFoundException;
    MedicamentReferenctiel updateTraitement(MedicamentReferenctiel ref,String codeBarre) throws ResourceNotFoundException;
    void addMultipleMedicament(List<MedicamentReferenctiel> medicaments) throws ResourceAlreadyExistException;
    List<MedicamentReferenctiel> getAllMedicaments();
    void deleteTraitement(String id);
}
