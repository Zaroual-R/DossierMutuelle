package ma.zar.mutuellebatch.repository;

import ma.zar.mutuellebatch.model.MedicamentReferenctiel;
import ma.zar.mutuellebatch.model.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedRefRepository extends JpaRepository<MedicamentReferenctiel, String> {
    boolean existsByCodeBarre(String codeBarre);

    MedicamentReferenctiel findByCodeBarre(String codeBarre);
}
