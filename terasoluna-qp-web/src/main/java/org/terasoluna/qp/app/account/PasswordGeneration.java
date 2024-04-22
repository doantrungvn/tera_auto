package org.terasoluna.qp.app.account;

import java.util.Random;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;

@Component
public class PasswordGeneration {
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	
	private String charactersUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String charactersLowerCase = "abcdefghijklmnopqrstuvwxyz";
	private String charactersNumeric = "0123456789";
	
	private Pattern hasUpperCasePattern = Pattern.compile("[A-Z]");
	private Pattern hasLowerCasePattern = Pattern.compile("[a-z]");
	private Pattern hasNumericPattern = Pattern.compile("\\d");
	
	public StringBuilder generatePassword(long accountRuleDefinitionId) {
		AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinitionId);
		
		StringBuilder charactersPasswordCollection = new StringBuilder();
		StringBuilder generatedPassword = new StringBuilder();
		Random random = new Random();
		
		if (accountRuleDefinition.isCharactersTypeUpper()) {
			charactersPasswordCollection.append(charactersUpperCase);
		}
		
		if (accountRuleDefinition.isCharactersTypeLower()) {
			charactersPasswordCollection.append(charactersLowerCase);
		}
		
		if (accountRuleDefinition.isCharactersTypeNumeric()) {
			charactersPasswordCollection.append(charactersNumeric);
		}
		
		if (charactersPasswordCollection.length() == 0 && !accountRuleDefinition.isCharactersTypeUpper() && !accountRuleDefinition.isCharactersTypeLower() && !accountRuleDefinition.isCharactersTypeNumeric()) {
			charactersPasswordCollection.append(charactersUpperCase);
			charactersPasswordCollection.append(charactersLowerCase);
			charactersPasswordCollection.append(charactersNumeric);
		}
		
		int randomLength = random.nextInt((accountRuleDefinition.getRangeOfStringMaximum() - accountRuleDefinition.getRangeOfStringMinimum()) + 1) + accountRuleDefinition.getRangeOfStringMinimum();
		
		while (generatedPassword.length() < randomLength) {
			int index = (int) (random.nextFloat() * charactersPasswordCollection.length());
			generatedPassword.append(charactersPasswordCollection.charAt(index));
		}
		
		if (accountRuleDefinition.isCharactersTypeUpper()) {
			if (!hasUpperCasePattern.matcher(generatedPassword).find()) {
				generatedPassword = generatePassword(accountRuleDefinitionId);
			}
		}
		
		if (accountRuleDefinition.isCharactersTypeLower()) {
			if (!hasLowerCasePattern.matcher(generatedPassword).find()) {
				generatedPassword = generatePassword(accountRuleDefinitionId);
			}
		}
		
		if (accountRuleDefinition.isCharactersTypeNumeric()) {
			if (!hasNumericPattern.matcher(generatedPassword).find()) {
				generatedPassword = generatePassword(accountRuleDefinitionId);
			}
		}
		
		return generatedPassword;
	}
}
