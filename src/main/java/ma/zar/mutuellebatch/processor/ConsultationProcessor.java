package ma.zar.mutuellebatch.processor;

import ma.zar.mutuellebatch.model.DossierMutuelle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConsultationProcessor implements ItemProcessor <DossierMutuelle,DossierMutuelle> {

    private final double POURCENTAGE_SUR_CONSULTATION=0.7;
    @Override
    public DossierMutuelle process(DossierMutuelle item) throws Exception {
        double remboursementConsultation=item.getPrixConsultation()*POURCENTAGE_SUR_CONSULTATION;
        item.setRobouresementConsultation(remboursementConsultation);
        return item;
    }
}
