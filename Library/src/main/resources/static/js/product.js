$('document').ready(function() {
	
	$('.table #editButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(productDto, status){
			$('#nameEdit').val(productDto.name);
			$('#descriptionEdit').val(productDto.description);
			$('#categoryEdit').val(productDto.category);
			$('#quantiyEdit').val(productDto.currentQuantity);
			$('#costPriceEdit').val(productDto.costPrice);
			$('#salePriceEdit').val(productDto.salePrice);
		});			
		$('#editModal').modal();		
	});
	
});