package ma.zar.mutuellebatch.service.roumboresement;

import lombok.RequiredArgsConstructor;
import ma.zar.mutuellebatch.model.RembAssure;
import ma.zar.mutuellebatch.repository.RembAssureRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RembAssureSerivce implements IRembAssureService {

    private final RembAssureRepository repo;
    @Override
    public RembAssure addRembAssure(RembAssure rembAssure) {
        return repo.save(rembAssure);
    }
}
