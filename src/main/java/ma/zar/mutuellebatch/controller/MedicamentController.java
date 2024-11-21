package ma.zar.mutuellebatch.controller;

import lombok.RequiredArgsConstructor;
import ma.zar.mutuellebatch.exceptions.ResourceAlreadyExistException;
import ma.zar.mutuellebatch.exceptions.ResourceNotFoundException;
import ma.zar.mutuellebatch.model.MedicamentReferenctiel;
import ma.zar.mutuellebatch.response.ApiResponse;
import ma.zar.mutuellebatch.service.prescription.MedRefService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch/dossier/medicament")
public class MedicamentController {
    private final MedRefService medRefService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMedicament(@RequestBody MedicamentReferenctiel ref){
        try {
            MedicamentReferenctiel medRef = medRefService.addTraitement(ref);
            return ResponseEntity.ok(new ApiResponse("upload success", medRef));
        } catch (ResourceAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", null));

        }
    }

    @PostMapping("/addmultiple")
    public ResponseEntity<ApiResponse> addMultipleMedicament(@RequestBody List<MedicamentReferenctiel> refs){
        try {
            medRefService.addMultipleMedicament(refs);
            return ResponseEntity.ok(new ApiResponse("upload success", null));
        } catch (ResourceAlreadyExistException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("upload failed", null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(){
        try {
            List<MedicamentReferenctiel> refs= medRefService.getAllMedicaments();
            return ResponseEntity.ok(new ApiResponse("upload success", refs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", null));
        }

    }
}
