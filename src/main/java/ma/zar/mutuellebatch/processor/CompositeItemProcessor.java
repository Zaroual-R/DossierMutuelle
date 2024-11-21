package ma.zar.mutuellebatch.processor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.zar.mutuellebatch.model.DossierMutuelle;
import ma.zar.mutuellebatch.model.RembAssure;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
public class CompositeItemProcessor implements ItemProcessor<DossierMutuelle, RembAssure> {
    @Autowired
    private   ValidationProcessor validationProcessor;
    @Autowired
    private   CalculationProcessor calculationProcessor;


    @Override
    public RembAssure process(DossierMutuelle item) throws Exception {
        DossierMutuelle dossierMutuelle = validationProcessor.process(item);
        RembAssure rembAssure = calculationProcessor.process(dossierMutuelle);
        return rembAssure;
    }

}
