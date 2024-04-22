$(function() {
    var porjectId = $("input:hidden[name='projectId']");
    $("input:text[name='moduleIdAutocomplete']").attr("arg01", $(porjectId).val());
});

function changeArgModuleAutocomplete(obj){
    var scope = $(obj.target).closest("tr");
    var value = $(scope).find("input:hidden[name='projectId']").val();

    // Setting module follow project select
    $(scope).find("input:text[name='moduleIdAutocomplete']").val('');
    $(scope).find("input:hidden[name='moduleId']").val('');
    $(scope).find("input:text[name='moduleIdAutocomplete']").attr("arg01", value);
    $.qp.removeAutocompleteData($($(scope).find("input:hidden[name='moduleId']")).nextAll('.dropdown-toggle:first'));
}