package ma.zar.mutuellebatch.config;

import ma.zar.mutuellebatch.exceptions.ValidationException;
import ma.zar.mutuellebatch.model.DossierMutuelle;
import ma.zar.mutuellebatch.model.RembAssure;
import ma.zar.mutuellebatch.processor.CompositeItemProcessor;
import ma.zar.mutuellebatch.repository.RembAssureRepository;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.retry.RetryListener;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.logging.Logger;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private RembAssureRepository rembAssureRepository;
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    public BatchConfig(RembAssureRepository rembAssureRepository,
                       JobRepository jobRepository,
                       PlatformTransactionManager transactionManager
                       ) {
        this.rembAssureRepository = rembAssureRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }


    @Bean
    public Step step1(){
         return new StepBuilder("step1",jobRepository)
                 .<DossierMutuelle,RembAssure>chunk(4,transactionManager)
                 .reader(jsonReader())
                 .processor(compositeItemProcessor())
                 .writer(writer())
                 .faultTolerant()
                     .retryLimit(3)
                     .retry(ItemReaderException.class)
                     .retry(ItemWriterException.class)
                     .skipLimit(10)
                     .skip(ValidationException.class)
                     .skip(RuntimeException.class)
                 .build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("job",jobRepository)
                .start(step1())
                .build();
    }


    @Bean
    public CompositeItemProcessor compositeItemProcessor() {
        return new CompositeItemProcessor();
    }


    @Bean
    public RepositoryItemWriter<RembAssure> writer() {
        RepositoryItemWriter<RembAssure> writer = new RepositoryItemWriter<>();
        writer.setRepository(rembAssureRepository);
        writer.setMethodName("save");
        return writer;
    }
    @Bean
    public ItemReader<DossierMutuelle> jsonReader() {
        return new JsonItemReaderBuilder<DossierMutuelle>()
                .name("dossierMutuielle")
                .jsonObjectReader(new JacksonJsonObjectReader<>(DossierMutuelle.class))
                .resource(new FileSystemResource("src/main/resources/dossiers.json"))
                .build();
    }



    @Bean
    public SkipListener<DossierMutuelle,RembAssure> skipListener() {
        return new SkipListener<DossierMutuelle, RembAssure>() {
            private final Logger logger= (Logger) LoggerFactory.getLogger("batchLogger");

            @Override
            public void onSkipInRead(Throwable t) {
               logger.warning("skip un item durant la lecture de données"+t.getMessage());
            }

            @Override
            public void onSkipInWrite(RembAssure item, Throwable t) {
                logger.warning("skip un item durant le traitement "+t.getMessage());
            }

            @Override
            public void onSkipInProcess(DossierMutuelle item, Throwable t) {
               logger.warning("skip un item durant l'ecriture " +t.getMessage());
            }
        };
    }

//    public  RetryListener retryListener() {
//        return new RetryListener() {
//
//        }
//    }







   /* @Bean
    public ItemReader<DossierMutuelle> csvReader() {
        FlatFileItemReader<DossierMutuelle> flatFileItemReader=new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/dossiers.csv"));

    }*/





}
