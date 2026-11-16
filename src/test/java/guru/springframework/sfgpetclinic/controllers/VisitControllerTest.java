package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

	@Mock
	VisitService visitService;

	// A spy is a wrapper around a real object
	@Spy // @Mock
	PetMapService petService;

	@InjectMocks
	VisitController controller;

	@Test
	void testLoadPetWithVisit() {
		// given
		Map<String, Object> model = new HashMap<>();
		Pet pet = new Pet(12L);
		Pet pet3 = new Pet(3L);

		petService.save(pet);
		petService.save(pet3);

		given(petService.findById(anyLong())).willCallRealMethod();

		// when
		Visit visit = controller.loadPetWithVisit(12L, model);

		// then
		assertThat(visit).isNotNull();
		assertThat(visit.getPet()).isNotNull();
		assertThat(visit.getPet().getId()).isEqualTo(12L);
		verify(petService, times(1)).findById(anyLong());
	}

	@Test
	void testLoadPetWithVisitWithStubbing() {
		// given
		Map<String, Object> model = new HashMap<>();
		Pet pet = new Pet(12L);
		Pet pet3 = new Pet(3L);

		petService.save(pet);
		petService.save(pet3);

		given(petService.findById(anyLong())).willReturn(pet3);

		// when
		Visit visit = controller.loadPetWithVisit(12L, model);

		// then
		assertThat(visit).isNotNull();
		assertThat(visit.getPet()).isNotNull();
		assertThat(visit.getPet().getId()).isEqualTo(3L);
		verify(petService, times(1)).findById(anyLong());
	}

}
