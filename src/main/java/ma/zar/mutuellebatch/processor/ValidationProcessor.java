package ma.zar.mutuellebatch.processor;

import ma.zar.mutuellebatch.exceptions.ValidationException;
import ma.zar.mutuellebatch.model.DossierMutuelle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ValidationProcessor implements ItemProcessor<DossierMutuelle,DossierMutuelle> {

    @Override
    public DossierMutuelle process(DossierMutuelle item) throws Exception {
        if(item.getNomAssure()==null || item.getNomAssure().equals("")){
            throw new ValidationException("le non de l'assuré ne doit pas etre vide");
        }
        if(item.getNumeroAffiliation()==null || item.getNumeroAffiliation().equals("")){
            throw new ValidationException("le numéro d'affilation ne doit pas etre vide");
        }
        if(item.getPrixConsultation() <= 0 ){
            throw new ValidationException("prix de consultation doit étre positif");
        }
        if(item.getMontantTotalFrais() <= 0 ){
            throw new ValidationException("montant total doit étre positif");
        }
        if(item.getTraitements()==null|| item.getTraitements().isEmpty()){
            throw new ValidationException("list des traitement doit étre pas vide ");
        }
        return item;
    }
}
