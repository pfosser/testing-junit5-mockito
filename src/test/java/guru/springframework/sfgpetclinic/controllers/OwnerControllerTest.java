package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

	private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

	@Mock
	OwnerService service;
	
	@Mock
	Model model;

	@InjectMocks
	OwnerController controller;

	@Mock
	BindingResult bindingResult;

	@Captor
	ArgumentCaptor<String> stringArgumentCaptor; // saves lines if used in multiple tests

	// @BeforeEach
	void setUp() {
		given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocation -> {
			List<Owner> owners = new ArrayList<>();
			String name = invocation.getArgument(0);
			if (name.equals("%Buck%")) {
				owners.add(new Owner(1L, "Joe", "Buch"));
				return owners;
			} else if (name.equals("%DontFindMe%")) {
				return owners;
			} else if (name.equals("%FindMe%")) {
				owners.add(new Owner(1L, "Joe", "Buch"));
				owners.add(new Owner(2L, "Joe2", "Buch2"));
				return owners;
			}

			throw new RuntimeException("Invalid argument");
		});
	}

	@Test
	void processCreationFormHasErrors() {
		// given
		Owner owner = new Owner(1L, "Jim", "Bob");
		given(bindingResult.hasErrors()).willReturn(true);

		// when
		String viewName = controller.processCreationForm(owner, bindingResult);

		// then
		assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
	}

	@Test
	void processCreationFormNoErrors() {
		// given
		Owner owner = new Owner(5L, "Jim", "Bob");
		given(bindingResult.hasErrors()).willReturn(false);
		given(service.save(any())).willReturn(owner);

		// when
		String viewName = controller.processCreationForm(owner, bindingResult);

		// then
		assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
	}

	@Test
	void processFindFormWildCardString() {
		// given
		Owner owner = new Owner(1L, "Joe", "Buck");
		List<Owner> ownerList = new ArrayList<>();
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		given(service.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

		// when
		controller.processFindForm(owner, bindingResult, null);

		// then
		assertThat("%" + owner.getLastName() + "%").isEqualToIgnoringCase(captor.getValue());
	}

	@Test
	void processFindFormWildCardFound() {
		// given
		setUp();
		Owner owner = new Owner(1L, "Joe", "FindMe");
		InOrder inOrder = Mockito.inOrder(service, model);

		// when
		String viewName = controller.processFindForm(owner, bindingResult, model);

		// then
		assertThat("%" + owner.getLastName() + "%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
		assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);
		
		// inorder asserts (the order of the verifications is what counts)
		inOrder.verify(service).findAllByLastNameLike(anyString());
		inOrder.verify(model).addAttribute(anyString(), anyList());
	}
	
	@Test
	void processFindFormWildCardNotFound() {
		// given
		setUp();
		Owner owner = new Owner(1L, "Joe", "DontFindMe");

		// when
		String viewName = controller.processFindForm(owner, bindingResult, null);

		// then
		assertThat("%" + owner.getLastName() + "%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
		assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
	}
	
	@Test
	void processFindFormWildCardStringAnnotation() { // alternative with the annotated captor
		// given
		setUp();
		Owner owner = new Owner(1L, "Joe", "Buck");

		// when
		String viewName = controller.processFindForm(owner, bindingResult, null);

		// then
		assertThat("%" + owner.getLastName() + "%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
		assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
	}
}
