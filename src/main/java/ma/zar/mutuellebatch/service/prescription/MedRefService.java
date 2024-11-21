package ma.zar.mutuellebatch.service.prescription;

import lombok.RequiredArgsConstructor;
import ma.zar.mutuellebatch.exceptions.ResourceAlreadyExistException;
import ma.zar.mutuellebatch.exceptions.ResourceNotFoundException;
import ma.zar.mutuellebatch.model.MedicamentReferenctiel;
import ma.zar.mutuellebatch.repository.MedRefRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedRefService implements IMedRefService{
    private final MedRefRepository medRefRepository;

    @Override
    public MedicamentReferenctiel addTraitement(MedicamentReferenctiel ref) throws ResourceAlreadyExistException {
        if(medRefRepository.existsByCodeBarre(ref.getCodeBarre())){
            throw new  ResourceAlreadyExistException("ce medicament est déja exist");
        }
        return medRefRepository.save(ref);
    }

    @Override
    public MedicamentReferenctiel getTraitement(String  codeBarre) throws ResourceNotFoundException {
        return Optional.ofNullable(medRefRepository.findByCodeBarre(codeBarre)).orElse(null);
    }

    @Override
    public MedicamentReferenctiel updateTraitement(MedicamentReferenctiel traitement, String  id) throws ResourceNotFoundException {
        MedicamentReferenctiel traitement1= getTraitement(id);
        traitement1.setCodeBarre(traitement.getCodeBarre());
        traitement1.setNomMedicament(traitement.getNomMedicament());
        return medRefRepository.save(traitement1);
    }

    @Override
    public void addMultipleMedicament(List<MedicamentReferenctiel> medicaments) throws ResourceAlreadyExistException {
        for(MedicamentReferenctiel med: medicaments){
            addTraitement(med);
        }
    }

    @Override
    public List<MedicamentReferenctiel> getAllMedicaments() {
        return medRefRepository.findAll();
    }

    @Override
    public void deleteTraitement(String id) {
        medRefRepository.findById(id).ifPresentOrElse(medRefRepository::delete, () ->
            new ResourceClosedException("le medicament avec id"+id+"n'as pas été trouvé")
        );
    }
}
