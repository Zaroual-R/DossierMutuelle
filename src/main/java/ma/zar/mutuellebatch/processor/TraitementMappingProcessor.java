package ma.zar.mutuellebatch.processor;

import lombok.RequiredArgsConstructor;
import ma.zar.mutuellebatch.model.DossierMutuelle;
import ma.zar.mutuellebatch.model.MedicamentReferenctiel;
import ma.zar.mutuellebatch.model.Traitement;
import ma.zar.mutuellebatch.service.prescription.IMedRefService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraitementMappingProcessor implements ItemProcessor<DossierMutuelle,DossierMutuelle> {
    @Autowired
    private  IMedRefService medRefService;
    @Override
    public DossierMutuelle process(DossierMutuelle item) throws Exception {

        for(Traitement traitement : item.getTraitements()){
            MedicamentReferenctiel ref=medRefService.getTraitement(traitement.getCodeBarre());
            traitement.setMedicamentReferenctiel(ref);
            traitement.setExiste(ref != null);
        }
        return item;
    }
}
