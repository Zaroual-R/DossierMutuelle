package ma.zar.mutuellebatch.processor;

import ma.zar.mutuellebatch.model.DossierMutuelle;
import ma.zar.mutuellebatch.model.RembAssure;
import ma.zar.mutuellebatch.model.Traitement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TotalRembouresmentProcessor implements ItemProcessor<DossierMutuelle,RembAssure> {

    @Override
    public RembAssure process(DossierMutuelle item) throws Exception {
        double totalRembours = item.getRobouresementConsultation();
        for(Traitement traitement: item.getTraitements()){
            if(traitement.isExiste()){
                totalRembours += traitement.getMontantRemboursement();
            }
        }
        RembAssure rembAssure = new RembAssure();
        rembAssure.setNomAssure(item.getNomAssure());
        rembAssure.setImmatriculation(item.getImmatriculation());
        rembAssure.setNumeroAffiliation(item.getNumeroAffiliation());
        rembAssure.setTotalRembouresement(totalRembours);

        return rembAssure;

    }
}
