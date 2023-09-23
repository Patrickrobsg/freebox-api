package com.freebox.controller;

import com.freebox.model.EstabelecimentoModel;
import com.freebox.model.UserModel;
import com.freebox.repository.EstabelecimentoRepository;
import com.freebox.repository.UserRepository;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Endpoints de Estabelecimento")
public class EstabelecimentoController {

    final
    UserRepository userRepository ;

    final
    EstabelecimentoRepository estabelecimentoRepository ;

    public EstabelecimentoController(UserRepository userRepository, EstabelecimentoRepository estabelecimentoRepository) {
        this.userRepository = userRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @GetMapping("/estabelecimento")
    public List<EstabelecimentoModel> getAllEstabelecimento(){
        return estabelecimentoRepository.findAll();
    }

    @GetMapping("/estabelecimento/{id}")
    public Optional<EstabelecimentoModel> getEstabelecimentoById(@PathVariable(value="id") long id){
        return estabelecimentoRepository.findById(id);
    }

    @GetMapping("/estabelecimento/find/{name}")
    public List<EstabelecimentoModel> getEstabelecimentoByName(@PathVariable(value = "name") String name) {
        return estabelecimentoRepository.findByNameContaing(name.toUpperCase());
    }
    
    @GetMapping("/estabelecimento/responsible/{id_responsible}")
    public List<EstabelecimentoModel> getEstabelecimentoByResponsible(@PathVariable(value = "id_responsible") long idUser) {
    	Optional<UserModel> user = userRepository.findById(idUser);
    	return estabelecimentoRepository.findByResponsible(user);
    }

    @PostMapping("/estabelecimento")
    public EstabelecimentoModel saveEstabelecimento(@RequestBody @Valid EstabelecimentoModel estabelecimento) {
        final UserModel user = userRepository.getById(estabelecimento.getResponsible().getId());
        estabelecimento.setResponsible(user);
        return estabelecimentoRepository.save(estabelecimento);

    }

    @DeleteMapping("/estabelecimento/{id}")
    public void deleteEstabelecimento(@PathVariable(value="id") long id) {
        Optional<EstabelecimentoModel> estabelecimento = estabelecimentoRepository.findById(id);
        estabelecimentoRepository.deleteById(estabelecimento.get().getId());
    }

    @PutMapping("/estabelecimento/{id}")
    public EstabelecimentoModel updateEstabelecimento(@PathVariable(value="id") long id, @RequestBody @Valid EstabelecimentoModel estabelecimentoUpdate) {
        Optional<EstabelecimentoModel> estabelecimento = estabelecimentoRepository.findById(id);
        estabelecimentoUpdate.setId(estabelecimento.get().getId());
        estabelecimentoUpdate.setResponsible(estabelecimento.get().getResponsible());
        return estabelecimentoRepository.save(estabelecimentoUpdate);
    }
}