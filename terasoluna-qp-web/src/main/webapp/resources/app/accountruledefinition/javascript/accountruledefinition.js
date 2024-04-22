$(document).ready(function() {
	// Initialize
	if ($('input[name="rangeOfStringCheckbox"]').prop('checked') == false) {
		$('input[name="rangeOfStringMinimum"]').prop('disabled', true);
		$('input[name="rangeOfStringMaximum"]').prop('disabled', true);
		$('input[name="rangeOfStringMinimum"]').val("");
		$('input[name="rangeOfStringMaximum"]').val("");
	}
	
	if ($('input[name="charactersTypeCheckbox"]').prop('checked') == false) {
		$('input[name="charactersTypeUpper"]').prop('disabled', true);
		$('input[name="charactersTypeLower"]').prop('disabled', true);
		$('input[name="charactersTypeNumeric"]').prop('disabled', true);
	}
	
	if ($('input[name="generationsCountCheckbox"]').prop('checked') == false) {
		$('input[name="generationsCount"]').prop('disabled', true);
		$('input[name="generationsCount"]').val("");
	}
	
	if ($('input[name="lifeTimeCheckbox"]').prop('checked') == false) {
		$('input[name="lifeTime"]').prop('disabled', true);
		$('input[name="lifeTime"]').val("");
	}
	
	if ($('input[name="loginContinuousFailureCountCheckbox"]').prop('checked') == false) {
		$('input[name="loginContinuousFailureCount"]').prop('disabled', true);
		$('input[name="loginContinuousFailureCount"]').val("");
		$('input[name="accountLockTimeCheckbox"]').prop('disabled', true);
	}
	
	if ($('input[name="accountLockTimeCheckbox"]').prop('checked') == false) {
		$('input[name="accountLockTime"]').prop('disabled', true);
		$('input[name="accountLockTime"]').val("");
	}
	
	if ($('input[name="initialPassword"]').prop('checked') == false) {
		$('input[name="initialPasswordForceChange"]').prop('disabled', true);
	}
	
	// Check change element
	$('input[name="rangeOfStringCheckbox"]').change(function() {
		if (this.checked) {
			$('input[name="rangeOfStringMinimum"]').prop('disabled', false);
			$('input[name="rangeOfStringMaximum"]').prop('disabled', false);
		} else {
			$('input[name="rangeOfStringMinimum"]').prop('disabled', true);
			$('input[name="rangeOfStringMaximum"]').prop('disabled', true);
		}
	});
	
	$('input[name="charactersTypeCheckbox"]').change(function() {
		if (this.checked) {
			$('input[name="charactersTypeUpper"]').prop('disabled', false);
			$('input[name="charactersTypeLower"]').prop('disabled', false);
			$('input[name="charactersTypeNumeric"]').prop('disabled', false);
		} else {
			$('input[name="charactersTypeUpper"]').prop('disabled', true);
			$('input[name="charactersTypeLower"]').prop('disabled', true);
			$('input[name="charactersTypeNumeric"]').prop('disabled', true);
		}
	});
	
	$('input[name="generationsCountCheckbox"]').change(function() {
		if (this.checked) {
			$('input[name="generationsCount"]').prop('disabled', false);
		} else {
			$('input[name="generationsCount"]').prop('disabled', true);
		}
	});
	
	$('input[name="lifeTimeCheckbox"]').change(function() {
		if (this.checked) {
			$('input[name="lifeTime"]').prop('disabled', false);
		} else {
			$('input[name="lifeTime"]').prop('disabled', true);
		}
	});
	
	$('input[name="loginContinuousFailureCountCheckbox"]').change(function() {
		if (this.checked) {
			$('input[name="loginContinuousFailureCount"]').prop('disabled', false);
			$('input[name="accountLockTimeCheckbox"]').prop('disabled', false);
			
			if ($('input[name="accountLockTimeCheckbox"]').prop('checked') == true) {
				$('input[name="accountLockTime"]').prop('disabled', false);
			}
		} else {
			$('input[name="loginContinuousFailureCount"]').prop('disabled', true);
			$('input[name="accountLockTimeCheckbox"]').prop('disabled', true);
			$('input[name="accountLockTime"]').prop('disabled', true);
		}
	});
	
	$('input[name="accountLockTimeCheckbox"]').change(function() {
		if (this.checked) {
			$('input[name="accountLockTime"]').prop('disabled', false);
		} else {
			$('input[name="accountLockTime"]').prop('disabled', true);
		}
	});
	
	$('input[name="initialPassword"]').change(function() {
		if (this.checked) {
			$('input[name="initialPasswordForceChange"]').prop('disabled', false);
		} else {
			$('input[name="initialPasswordForceChange"]').prop('disabled', true);
		}
	});
});