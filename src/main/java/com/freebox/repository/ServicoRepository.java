package com.freebox.repository;

import com.freebox.model.EstabelecimentoModel;
import com.freebox.model.ServicoModel;
import com.freebox.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoModel, Long>{

    @Query("Select e from ServicoModel e where inativo=false and UPPER(e.nome) like %:nomePart%")
    List<ServicoModel> findByNameIgnoreCase(@Param("nomePart") String nomePart);

    List<ServicoModel> findByBusinessService(Optional<EstabelecimentoModel> estabelecimento);

}