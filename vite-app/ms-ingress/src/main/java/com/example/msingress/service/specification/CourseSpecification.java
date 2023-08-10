package com.example.msingress.service.specification;

import com.example.msingress.dto.request.CourseCriteria;
import com.example.msingress.model.Course;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CourseSpecification implements Specification<Course> {
    private final CourseCriteria courseCriteria;

    private static final String NAME = "name";
    private static final String STUDENT = "student";

    @Override
    public Predicate toPredicate(Root<Course> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (courseCriteria != null) {
            if (courseCriteria.getName() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(NAME), courseCriteria.getName()));
            }
            if (courseCriteria.getStudents() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(STUDENT), courseCriteria.getName()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}