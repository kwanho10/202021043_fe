package kr.ac.kku.cs.wp.kwanho10.user.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import kr.ac.kku.cs.wp.kwanho10.user.entity.User;
import kr.ac.kku.cs.wp.kwanho10.user.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {

	private static final Logger logger = LogManager.getLogger(UserSpecifications.class);

	public static Specification<User> filterUsersByQueryString(String queryString) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			// Join for userRoles
			Join<User, UserRole> userRolesJoin = root.join("userRoles", JoinType.LEFT);

			// Dynamic conditions
			if (queryString != null && !queryString.isEmpty()) {
				logger.debug("Applying filters with queryString: {}", queryString);

				// Filtering by id, name, email, and status
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("id")),"%" + queryString.toLowerCase() + "%"));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + queryString.toLowerCase() + "%"));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" + queryString.toLowerCase() + "%"));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")),"%" + queryString.toLowerCase() + "%"));
				
				// Filtering by user role
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(userRolesJoin.get("roleName")),
						"%" + queryString.toLowerCase() + "%"));
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		};
	}
	
	
	
	 public static Specification<User> filterUsers(User user) {
	        return (root, query, criteriaBuilder) -> {
	            List<Predicate> predicates = new ArrayList<>();

	            // Join for userRoles
	            Join<User, UserRole> userRolesJoin = root.join("userRoles", JoinType.LEFT);

	            // Dynamic conditions
	            if (user != null) {
	                if (user.getId() != null && !user.getId().isEmpty()) {
	                    predicates.add(criteriaBuilder.like(
	                            criteriaBuilder.lower(root.get("id")), "%" + user.getId().toLowerCase() + "%"
	                    ));
	                }

	                if (user.getName() != null && !user.getName().isEmpty()) {
	                    predicates.add(criteriaBuilder.like(
	                            criteriaBuilder.lower(root.get("name")), "%" + user.getName().toLowerCase() + "%"
	                    ));
	                }

	                if (user.getEmail() != null && !user.getEmail().isEmpty()) {
	                    predicates.add(criteriaBuilder.like(
	                            criteriaBuilder.lower(root.get("email")), "%" + user.getEmail().toLowerCase() + "%"
	                    ));
	                }

	                if (user.getStatus() != null && !user.getStatus().isEmpty()) {
	                    predicates.add(criteriaBuilder.like(
	                            criteriaBuilder.lower(root.get("status")), "%" + user.getStatus().toLowerCase() + "%"
	                    ));
	                }

	                if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
	                    // Assuming we are filtering based on the first role name
	                    predicates.add(criteriaBuilder.like(
	                            criteriaBuilder.lower(userRolesJoin.get("roleName")),
	                            "%" + user.getUserRoles().get(0).getRoleName().toLowerCase() + "%"
	                    ));
	                }
	            }

	            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	        };
	}
	
}
