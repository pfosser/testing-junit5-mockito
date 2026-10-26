package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
		service.deleteById(1L);
		
		// By default (verify with 1 argument) it checks that the call is done exactly 1 time
		verify(specialtyRepository).deleteById(1L);
	}

	@Test
	void testDeleteByIdTwoTimes() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, times(2)).deleteById(1L);
	}

	@Test
	void testDeleteByIdAtLeast() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, atLeastOnce()).deleteById(1L);
	}

	@Test
	void testDeleteByIdAtMost() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, atMost(5)).deleteById(1L);
	}

	@Test
	void testDeleteByIdNever() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, never()).deleteById(5L);
	}

}
