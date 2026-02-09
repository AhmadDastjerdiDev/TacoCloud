package tacos.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos", produces = {"application/json", "text/xml"})
@CrossOrigin(origins = {"http://tacocloud:8080", "http://tacocloud.com"})
public class TacoController {
    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo){
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos(){
        PageRequest page = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public Optional<Taco> tacoById(@PathVariable("id") Long id){
        return tacoRepo.findById(id);
    }
}
