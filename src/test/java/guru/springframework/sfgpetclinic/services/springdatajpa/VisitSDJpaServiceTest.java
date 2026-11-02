package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

	@Mock
	VisitRepository visitRepository;

	@InjectMocks
	VisitSDJpaService service;

	@Test
	void testFindAll() {

		// given
		Visit visit = new Visit();
		Set<Visit> visits = Set.of(visit);
		given(visitRepository.findAll()).willReturn(visits);

		// when
		Set<Visit> foundVisits = service.findAll();

		// then
		then(visitRepository).should().findAll();
		assertThat(foundVisits).hasSize(1);
	}

	@Test
	void testFindById() {

		// given
		Visit visit = new Visit();
		given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

		// when
		Visit foundVisit = service.findById(1L);

		// then
		then(visitRepository).should().findById(anyLong());
		assertThat(foundVisit).isNotNull();
	}

	@Test
	void testSave() {
		
		// given
		Visit visit = new Visit();
		given(visitRepository.save(any(Visit.class))).willReturn(visit);

		// when
		Visit savedVisit = service.save(new Visit());

		// then
		then(visitRepository).should().save(any(Visit.class));
		assertThat(savedVisit).isNotNull();
	}

	@Test
	void testDelete() {
		
		// given
		Visit visit = new Visit();

		// when
		service.delete(visit);

		// then
		then(visitRepository).should().delete(any(Visit.class));
	}

	@Test
	void testDeleteById() {
		
		// given - none
		
		// when
		service.deleteById(1L);

		// then
		then(visitRepository).should().deleteById(anyLong());
	}
}
