package ma.zar.mutuellebatch.processor;

import ma.zar.mutuellebatch.model.DossierMutuelle;
import ma.zar.mutuellebatch.model.Traitement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TraitementRembouresementProcessor implements ItemProcessor  <DossierMutuelle, DossierMutuelle> {

    @Override
    public DossierMutuelle process(DossierMutuelle item) throws Exception {
        for(Traitement traitement:item.getTraitements()){
            if(traitement.isExiste()){
                double prixDeReference=traitement.getMedicamentReferenctiel().getPrixMedicament();
                double TauxRembourecement=traitement.getMedicamentReferenctiel().getTauxRemboursement();
                traitement.setMontantRemboursement(prixDeReference*TauxRembourecement);
            }
        }
        return item;
    }
}
