function openDialogClassification(obj, type) {
	
	$($("#classification")).modal(
		{ 
			show: true,
			closable: false,
			keyboard:false,
			backdrop:'static'
		}
	);
	var classificationModel = $("#classification");
	if(type == 0){
		classificationModel.find("span[class=pq-heading-text]").text(dbMsgSource['sc.domaindesign.0045']);
	}else if(type == 1){
		classificationModel.find("span[class=pq-heading-text]").text(dbMsgSource['sc.domaindesign.0048']);
	}
	var classificationObj = JSON.parse(classificationModel.find("#classificationTemp").val());
	
	classificationModel.find("textarea[name=majorClassification]").val(classificationObj.majorClassification);
	classificationModel.find("textarea[name=subClassification]").val(classificationObj.subClassification);
	classificationModel.find("textarea[name=minorClassification]").val(classificationObj.minorClassification);
	
	
}

function saveclassification(){
	var classificationModel = $("#classification");
	var classificationObj = {
		majorClassification : classificationModel.find("textarea[name=majorClassification]").val(),
		subClassification : classificationModel.find("textarea[name=subClassification]").val(),
		minorClassification : classificationModel.find("textarea[name=minorClassification]").val()
	};
	
	classificationModel.find("#classificationTemp").val(JSON.stringify(classificationObj));
	
	$("#classification").modal("hide");
}