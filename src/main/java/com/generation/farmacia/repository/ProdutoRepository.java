package com.generation.farmacia.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.farmacia.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	public List<Produto> findAllByNomeContainingIgnoreCase(String nome);

	public List<Produto> findAllByNomeAndLaboratorioContainingIgnoreCase(String nome, String laboratorio);

	public List<Produto> findAllByPrecoBetween(BigDecimal preco1, BigDecimal preco2);
	// * Equivalente ao Select * from tb_produtos between x e y

}
