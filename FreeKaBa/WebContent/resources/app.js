$(document).ready(function(){
	
	$('.hour').change(function(){
		
		var thisCheckBox = $(this).attr('id');
		
		var id = thisCheckBox.substring(4);
		
		if($(this).is(":checked")){
			$('#event'+id).prop("disabled", false);
		/*	$('#event'+id).prop("placeholder", "Enter event for " + id +"am");*/
		}else{
			$('#event'+id).prop("disabled", true);
		/*	$('#event'+id).prop("placeholder", "");*/
		}
	});
	

	
});