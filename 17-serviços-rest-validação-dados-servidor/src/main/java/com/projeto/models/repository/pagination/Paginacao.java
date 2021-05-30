package com.projeto.models.repository.pagination;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class Paginacao<T, D> {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Page<T> listUsuarioByPage(Class<T> classe, Class<D> filtro, Pageable pageable, String ...strings) {
		
		List<T> lista = new ArrayList<>();
		TypedQuery<T> query = null;
		
		int totalRegistrosPorPagina = pageable.getPageSize();
		int paginaAtual = pageable.getPageNumber();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(classe);
		Root<T> root = cq.from(classe);
		
		Predicate predicate = predicados(cb, root, filtro, strings);
		
		sortQuery(pageable, cb, cq, root);
		
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		
		query = entityManager.createQuery(cq);
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPagina);
		
		lista = query.getResultList();
		
		
		return new PageImpl<T>(lista, pageable, totalRegistro(predicate, classe));
	}

	private Long totalRegistro(Predicate predicate, Class<T> classe) {
		TypedQuery<Long> query = null;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> root = cq.from(classe);
		cq.select(cb.count(root));
		if (!Objects.isNull(predicate)) {
			cq.where(predicate);
		}
		query = entityManager.createQuery(cq);
		return query.getSingleResult(); 
	}

	private void sortQuery(Pageable pageable, CriteriaBuilder cb, 
						   CriteriaQuery<T> cq, Root<T> root) {
		
		Sort sort = pageable.getSort();
   		Sort.Order order = sort.iterator().next();
		String propriedade = order.getProperty();
		cq.orderBy(order.isAscending() ? 
				   cb.asc(root.get(propriedade)) : 
				   cb.desc(root.get(propriedade)));
   }

	private Predicate predicados(CriteriaBuilder cb, Root<T> root, 
								 Class<D> filtro, String[] strings) {
		Predicate predicates = null;
		for ( int i = 0 ; i <  filtro.getDeclaredFields().length; i++ ) {
		    Field atributo = filtro.getDeclaredFields()[i]; 
			if ( !StringUtils.isEmpty(atributo)) {
				predicates = cb.like(cb.lower(root.get( atributo.getName())), "%"+ strings[i] + "%");
			}
		}
		return predicates;
	}
}
