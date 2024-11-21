package ma.zar.mutuellebatch.processor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.zar.mutuellebatch.model.DossierMutuelle;
import ma.zar.mutuellebatch.model.RembAssure;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class CalculationProcessor implements ItemProcessor<DossierMutuelle, RembAssure> {
    @Autowired
    private ConsultationProcessor consultationProcessor;
    @Autowired
    private TraitementMappingProcessor traitementMappingProcessor;
    @Autowired
    private TraitementRembouresementProcessor traitementRembouresementProcessor;
    @Autowired
    private TotalRembouresmentProcessor totalRembouresmentProcessor;

    @Override
    public RembAssure process(DossierMutuelle item) throws Exception {
        DossierMutuelle dossierMutuelle = consultationProcessor.process(item);
        dossierMutuelle = traitementMappingProcessor.process(dossierMutuelle);
        dossierMutuelle = traitementRembouresementProcessor.process(dossierMutuelle);
        RembAssure rembAssure= totalRembouresmentProcessor.process(dossierMutuelle);

        return rembAssure;
    }
}
