package com.freebox.controller;

import com.freebox.model.*;
import com.freebox.repository.*;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Endpoints de Agendamento")
public class AgendamentoController {


    final
    UserRepository userRepository ;

    final
    ServicoRepository servicoRepository ;

    final
    EstabelecimentoRepository estabelecimentoRepository ;

    final
    AgendamentoRepository agendamentoRepository;

    final
    AtendimentoRepository atendimentoRepository;

    public AgendamentoController(UserRepository userRepository, ServicoRepository servicoRepository, EstabelecimentoRepository estabelecimentoRepository, AgendamentoRepository agendamentoRepository, AtendimentoRepository atendimentoRepository) {
        this.userRepository = userRepository;
        this.servicoRepository = servicoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    @GetMapping("/agendamento")
    public List<AgendamentoModel> getAllAgendamento(){
        return agendamentoRepository.findAll();
    }

    @GetMapping("/agendamentos/cliente/{id_user}")
    public List<AgendamentoModel> getAllAgendamentosByCliente(@PathVariable(value="id_user") long idUser){
        Optional<UserModel> user = userRepository.findById(idUser);
        return agendamentoRepository.findByClient(user);
    }

    @GetMapping("/agendamento/{id}")
    public Optional<AgendamentoModel> getAgendamentoById(@PathVariable(value="id") long id){
        return agendamentoRepository.findById(id);
    }

    @PostMapping("/agendamento")
    public AgendamentoModel saveAgendamento(@RequestBody @Valid AgendamentoModel agendamento) {
        final Optional<UserModel> user = userRepository.findById(agendamento.getClient().getId());
        final ServicoModel service = servicoRepository.getById(agendamento.getService().getId());
        final EstabelecimentoModel business = estabelecimentoRepository.getById(agendamento.getBusinessService().getId());

        agendamento.setClient(user.get());
        agendamento.setService(service);
        agendamento.setBusinessService(business);

        return agendamentoRepository.save(agendamento);

    }

    @DeleteMapping("/agendamento/{id}")
    public void deleteAgendamento(@PathVariable(value="id") long id) {
        Optional<AgendamentoModel> agendamento = agendamentoRepository.findById(id);
        agendamentoRepository.deleteById(agendamento.get().getId());
    }

    @PutMapping("/agendamento/{id}")
    public AgendamentoModel updateAgendamento(@PathVariable(value="id") long id, @RequestBody @Valid AgendamentoModel agendamentoUpdate) {
        Optional<AgendamentoModel> agendamento = agendamentoRepository.findById(id);
        agendamentoUpdate.setId(agendamento.get().getId());
        return agendamentoRepository.save(agendamentoUpdate);
    }


    @PutMapping("/agendamento/completed/{id}")
    public AgendamentoModel updateAgendamentoCompleted(@PathVariable(value="id") long id) {
        Optional<AgendamentoModel> agendamento = agendamentoRepository.findById(id);
        agendamento.get().setServiceCompleted(new Date());
        agendamento.get().setStatus(Status.CONCLUIDO);

        AtendimentoModel atendimento = new AtendimentoModel();
        atendimento.setAgendamento(agendamento.get());

        atendimentoRepository.save(atendimento);

        return agendamentoRepository.save(agendamento.get());
    }

    @PutMapping("/agendamento/cancel/{id}")
    public AgendamentoModel updateAgendamentoCanceled(@PathVariable(value="id") long id) {
        Optional<AgendamentoModel> agendamento = agendamentoRepository.findById(id);
        agendamento.get().setStatus(Status.CANCELADO);

        return agendamentoRepository.save(agendamento.get());
    }

    @PutMapping("/agendamento/Avaliar/{id}")
    public AgendamentoModel updateAgendamentoAvaliar(@PathVariable(value="id") long id, @RequestBody @Valid long nota )  {
        Optional<AgendamentoModel> agendamento = agendamentoRepository.findById(id);
        agendamento.get().setAvaliacao(nota);

        return agendamentoRepository.save(agendamento.get());
    }

}