package guru.springframework.sfgpetclinic.services.springdatajpa;

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
	}

}
