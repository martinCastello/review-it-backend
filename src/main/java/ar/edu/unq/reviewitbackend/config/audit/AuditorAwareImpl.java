package ar.edu.unq.reviewitbackend.config.audit;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {

		if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			return Optional.of("UNAUTHENTICATED");
		}
		
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
