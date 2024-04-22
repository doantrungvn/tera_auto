package org.terasoluna.qp.app.common;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.service.common.AutocompleteInput;
import org.terasoluna.qp.domain.service.common.AutocompleteOutput;
import org.terasoluna.qp.domain.service.common.AutocompleteService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "Autocomplete")
public class AutocompleteController {

	@Inject
	AutocompleteService autocompleteService;
	
	/*@Inject 
	LanguageDesignService languageDesignService;*/

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public AutocompleteOutput autocomplete(
			@RequestParam("parameters") String parameters, Model model) {
		AutocompleteInput param = new AutocompleteInput();
		AutocompleteOutput output = new AutocompleteOutput();
		if (parameters.isEmpty()) {
			output.setStatus("0");
			return output;
		}
		try {
			// convert string json to object
			JsonFactory json = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(json);

			TypeReference<AutocompleteInput> typeRef = new TypeReference<AutocompleteInput>() {
			};

			try {
				
				/*String languageCode = LocaleUtils.getDesaultLanguage().getLanguageCode();
				String countryCode  =  LocaleUtils.getDesaultLanguage().getCountryCode();
				LanguageDesign lang = new LanguageDesign(languageCode, countryCode);
				LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(lang);*/

				param = mapper.readValue(parameters, typeRef);
				param.setLanguageId(SessionUtils.getCurrentLanguageId());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}  catch (IOException e) {
				e.printStackTrace();
			}

			List<Autocomplete> autocomplete = autocompleteService.list(param,
					param.getSourceType());

			Autocomplete[] listOutput = autocomplete
					.toArray(new Autocomplete[autocomplete.size()]);

			output.setOutputGroup(listOutput);
			output.setStatus("1");
		} catch (Exception ex) {
			//ex.printStackTrace();
			return output;
		}
		return output;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public AutocompleteOutput autocompletePost(
			@RequestParam("parameters") String parameters, Model model) {

		return autocomplete(parameters, model);
	}
}
