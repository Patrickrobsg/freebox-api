package com.freebox.controller;

import com.freebox.model.EstabelecimentoModel;
import com.freebox.model.ServicoModel;
import com.freebox.repository.EstabelecimentoRepository;
import com.freebox.repository.ServicoRepository;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Endpoints de Serviços")
public class ServicoController {

    final
    ServicoRepository servicoRepository;

    final
    EstabelecimentoRepository estabelecimentoRepository ;

    public ServicoController(ServicoRepository servicoRepository, EstabelecimentoRepository estabelecimentoRepository) {
        this.servicoRepository = servicoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @GetMapping("/servicos")
    public List<ServicoModel> getAllServicos(){
        return servicoRepository.findAll();
    }

    @GetMapping("/servico/{id}")
    public Optional<ServicoModel> getServicoById(@PathVariable(value="id") long id){
        return servicoRepository.findById(id);
    }

    @GetMapping("/servico/find/{nome}")
    public List<ServicoModel> getServicoByNomeIgnoreCase(@PathVariable(value = "nome") String nome) {
        return servicoRepository.findByNameIgnoreCase(nome.toUpperCase());
    }

    @GetMapping("/servico/getActiveServices/{idEstabelecimento}")
    public List<ServicoModel> getActiveServicesByIdBarber(@PathVariable(value = "idEstabelecimento") long id) {
        Optional<EstabelecimentoModel> estabelecimento = estabelecimentoRepository.findById(id);

        List<ServicoModel> activeServices = new ArrayList<>();
        for(ServicoModel servico : servicoRepository.findByBusinessService(estabelecimento)) {
            if(!servico.isInativo()) {
                activeServices.add(servico);
            }
        }

        return activeServices;
    }

    @PostMapping("/servico")
    public ServicoModel saveServico(@RequestBody @Valid ServicoModel servico) {
        final EstabelecimentoModel business = estabelecimentoRepository.getById(servico.getBusinessService().getId());

        servico.setBusinessService(business);

        return servicoRepository.save(servico);

    }

    @PutMapping("/servico/{id}")
    public ServicoModel updateServico(@PathVariable(value="id") long id, @RequestBody @Valid ServicoModel servicoUpdate) {
        Optional<ServicoModel> servico = servicoRepository.findById(id);
        servicoUpdate.setId(servico.get().getId());
        return servicoRepository.save(servicoUpdate);
    }

    @PutMapping("/servico/inactive/{id}")
    public ServicoModel updateServicoInactive(@PathVariable(value="id") long id) {
        Optional<ServicoModel> servico = servicoRepository.findById(id);
        servico.get().setInativo(true);

        return servicoRepository.save(servico.get());
    }

    @PutMapping("/servico/active/{id}")
    public ServicoModel updateServicoActive(@PathVariable(value="id") long id) {
        Optional<ServicoModel> servico = servicoRepository.findById(id);
        servico.get().setInativo(false);

        return servicoRepository.save(servico.get());
    }

    @DeleteMapping("/servico/{id}")
    public void deleteServico(@PathVariable(value="id") long id) {
        Optional<ServicoModel> servico = servicoRepository.findById(id);
        servicoRepository.deleteById(servico.get().getId());
    }
}
