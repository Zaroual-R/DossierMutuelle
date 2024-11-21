package ma.zar.mutuellebatch.repository;

import ma.zar.mutuellebatch.model.RembAssure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RembAssureRepository  extends JpaRepository<RembAssure, Long> {
}
