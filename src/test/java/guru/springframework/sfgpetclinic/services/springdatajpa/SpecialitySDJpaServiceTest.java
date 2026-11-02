package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

	// This is the instance that will be injected into the service
	@Mock
	SpecialtyRepository specialtyRepository;

	@InjectMocks // tells Mockito to inject all the mocked dependencies
	SpecialitySDJpaService service;

	@Test
	void testDelete() {
		service.delete(new Speciality());
	}

	@Test
	void testDeleteById() {
		
		// given - none
		
		// when
		service.deleteById(1L);

		// then
		then(specialtyRepository).should().deleteById(1L);
	}

	@Test
	void testDeleteByIdTwoTimes() {
		
		// given - none
		
		// when
		service.deleteById(1L);
		service.deleteById(1L);

		// then
		then(specialtyRepository).should(times(2)).deleteById(1L);
	}

	@Test
	void testDeleteByIdAtLeast() {
		
		// given - none
		
		// then
		service.deleteById(1L);
		service.deleteById(1L);

		// then
		then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
	}

	@Test
	void testDeleteByIdAtMost() {
		
		// given - none
		
		// then
		service.deleteById(1L);
		service.deleteById(1L);

		// then
		then(specialtyRepository).should(atMost(5)).deleteById(1L);
	}

	@Test
	void testDeleteByIdNever() {
		
		// given - none
		
		// then
		service.deleteById(1L);
		service.deleteById(1L);

		// then
		then(specialtyRepository).should(never()).deleteById(5L);
	}

	@Test
	void testFindById() {
		
		// given
		Speciality speciality = new Speciality();
		given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

		// when
		Speciality foundSpeciality = service.findById(1L);

		// then
		assertThat(foundSpeciality).isNotNull();
		// then(specialtyRepository).should().findById(anyLong());
		then(specialtyRepository).should(times(1)).findById(anyLong());
		then(specialtyRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	void testDeleteByObject() {
		
		// given
		Speciality speciality = new Speciality();

		// when
		service.delete(speciality);

		// then
		then(specialtyRepository).should().delete(any(Speciality.class));
	}
}
