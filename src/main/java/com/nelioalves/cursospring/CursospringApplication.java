package com.nelioalves.cursospring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nelioalves.cursospring.domain.Categoria;
import com.nelioalves.cursospring.domain.Cidade;
import com.nelioalves.cursospring.domain.Cliente;
import com.nelioalves.cursospring.domain.Endereco;
import com.nelioalves.cursospring.domain.Estado;
import com.nelioalves.cursospring.domain.ItemPedido;
import com.nelioalves.cursospring.domain.Pagamento;
import com.nelioalves.cursospring.domain.PagamentoComBoleto;
import com.nelioalves.cursospring.domain.PagamentoComCartao;
import com.nelioalves.cursospring.domain.Pedido;
import com.nelioalves.cursospring.domain.Produto;
import com.nelioalves.cursospring.domain.enums.EstadoPagamento;
import com.nelioalves.cursospring.domain.enums.TipoCliente;
import com.nelioalves.cursospring.repositories.CategoriaRepository;
import com.nelioalves.cursospring.repositories.CidadeRepository;
import com.nelioalves.cursospring.repositories.ClienteRepository;
import com.nelioalves.cursospring.repositories.EnderecoRepository;
import com.nelioalves.cursospring.repositories.EstadoRepository;
import com.nelioalves.cursospring.repositories.ItemPedidoRepository;
import com.nelioalves.cursospring.repositories.PagamentoRepository;
import com.nelioalves.cursospring.repositories.PedidoRepository;
import com.nelioalves.cursospring.repositories.ProdutoRepository;

@SpringBootApplication
public class CursospringApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired 
	private PedidoRepository pedidoRepository; 
	
	@Autowired
	private PagamentoRepository pagamentoRepository; 
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository; 
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursospringApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Informática"); 
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00); 
		Produto p3 = new Produto(null, "Mouse", 80.00); 
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3)); 
		cat2.getProdutos().addAll(Arrays.asList(p2)); 
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null,"Minas Gerais"); 
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		Cidade c3 = new Cidade(null,"Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		
		categoriaRepository.save(Arrays.asList(cat1, cat2)); 
		produtoRepository.save(Arrays.asList(p1,p2,p3));
		estadoRepository.save(Arrays.asList(est1,est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva", "maria@gmail.com", "367-897-756-13", TipoCliente.PESSOAFISICA); 
		cli1.getTelefones().addAll(Arrays.asList("21-85634570" , "21-990017845"));
		
		Endereco end1 = new Endereco(	null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);

		Endereco end2 = new Endereco(	null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
	
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1 ); 
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2 ); 
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6); 
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2)); 
		
		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00); 
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00); 
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00); 
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2)); 
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));
		
		
		
	}
}
