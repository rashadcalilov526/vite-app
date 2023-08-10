package com.example.msingress.service.specification;

import com.example.msingress.dto.request.StudentCriteria;
import com.example.msingress.model.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StudentSpecification implements Specification<Student> {
    private final StudentCriteria studentCriteria;

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";

    @Override
    public Predicate toPredicate(Root<Student> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (studentCriteria != null) {
            if (studentCriteria.getName() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(NAME), studentCriteria.getName()));
            }
            if (studentCriteria.getName() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(SURNAME), studentCriteria.getSurname()));
            } if (studentCriteria.getName() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(EMAIL), studentCriteria.getEmail()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
