package com.projeto.models.repository.queryimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.projeto.models.model.Usuario;
import com.projeto.models.repository.filtros.UsuarioFiltro;
import com.projeto.models.repository.query.UsuarioQuery;

public class UsuarioRepositoryImpl implements UsuarioQuery{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Usuario> findUsuarioByEmail(String email) {
		
		TypedQuery<Usuario> consultaUsuarioEmail = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email =:email",Usuario.class); 
		
		consultaUsuarioEmail.setParameter("email", email);
		
		return consultaUsuarioEmail
				.setMaxResults(1)
				.getResultList()
				.stream()
				.findFirst();
	}


	@Override
	public Page<Usuario> listUsuarioByPage(UsuarioFiltro usuarioFiltro, Pageable pageable) {
		
		List<Usuario> lista = new ArrayList<>();
		TypedQuery<Usuario> query = null;
		
		int totalRegistrosPorPagina = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> root = cq.from(Usuario.class);
		
		root.fetch("departamento", JoinType.LEFT);
		root.fetch("roles", JoinType.LEFT);
		
		Predicate predicate = predicados(cb, root, usuarioFiltro);
		
		sortQuery(pageable, cb, cq, root);
		
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		
		query = entityManager.createQuery(cq);
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPagina);
		
		lista = query.getResultList();
		
		
		return new PageImpl<Usuario>(lista, pageable, totalRegistro(predicate));
	}

	private Long totalRegistro(Predicate predicate) {
		TypedQuery<Long> query = null;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Usuario> root = cq.from(Usuario.class);
		cq.select(cb.count(root));
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		query = entityManager.createQuery(cq);
		return query.getSingleResult(); 
	}

	private void sortQuery(Pageable pageable, CriteriaBuilder cb, 
						   CriteriaQuery<Usuario> cq, Root<Usuario> root) {
		
		Sort sort = pageable.getSort();
   		Sort.Order order = sort.iterator().next();
		String propriedade = order.getProperty();
		cq.orderBy(order.isAscending() ? 
				   cb.asc(root.get(propriedade)) : 
				   cb.desc(root.get(propriedade)));
   }

	private Predicate predicados(CriteriaBuilder cb, Root<Usuario> root, 
								 UsuarioFiltro usuarioFiltro) {
		
		Predicate predicates = null;
		
		if ( !StringUtils.isEmpty(usuarioFiltro.getNome())) {
			predicates = cb.like(cb.lower(root.get("username")), "%"+usuarioFiltro.getNome() + "%");
		}
		return predicates;
	}


	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Optional<Usuario> findUsuarioById(Long id) {
//		
//		List<Object[]> listaUsuario = new ArrayList<>();
//		
//		Query query = entityManager.createNativeQuery("select distinct "
//				+ "	u.usuario_id,  "
//				+ " u.usuario_nome, "
//				+ " u.usuario_email, "
//				+ " d.id, "
//				+ " d.departamento_nome, "
//				+ " r.role_id, "
//				+ " r.role_nome "
//				+ "from tab_usuario as u "
//				+ "left join tab_departamento as d on u.departamento_id = d.id "
//				+ "left join tab_usuario_role as ur on u.usuario_id = ur.usuario_id "
//				+ "left join tab_role as r on ur.role_id = r.role_id "
//				+ "where u.usuario_id = :id").setParameter("id", id);
//		
//		listaUsuario = query.getResultList();
//		
//		Optional<Usuario> usuario = null;
//		
//		if (!listaUsuario.isEmpty()) {
//		   
//			usuario = Optional.of(new Usuario());
//			
//			Departamento departamento = new Departamento();
//			
//			Role role = new Role();
//			
//			for (Object[] u : listaUsuario) {
//				
//				usuario.get().setId(Long.valueOf(u[0].toString()));
//				usuario.get().setUsername(u[1].toString());
//				usuario.get().setEmail(u[2].toString());
//				departamento.setId(Long.valueOf(u[3].toString()));
//				departamento.setNome(u[4].toString());
//				
//				usuario.get().setDepartamento(departamento);
//	            
//				if (!Objects.isNull(u[5])) {
//					role.setId(Long.valueOf(u[5].toString()));
//					role.setNome(u[6].toString());
//					usuario.get().getRoles().add(role);
//				}
//			}
//		}
//		return usuario;
//	}


	

}











