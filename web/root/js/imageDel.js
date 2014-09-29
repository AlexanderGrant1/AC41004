$.fn.imageDel = function (url,options)
{
    var defaults = 
    {
    	url: url,
        mainOb: this
    }

    var opts = $.extend(defaults, options);

    return this.each(function()
    {
        $(opts.mainOb).on('touchstart click','.remove', function()
        {
        	// Get the id of the linker.
            var entry     = $(this).parent();
            var elementId = entry.attr("id");
            var arr       = elementId.split('-');
            var objType   = arr[0];
            var objId     = parseInt(arr[1]);

            if(!isNaN(objId))
            {
            	$.ajax({
				    url: url+objType+"/delmedia/"+objId,
				    type: 'DELETE',
				    success: function(result) {
				        alert('deleted');
				        entry.fadeOut();
				    }
				});         
            }
        });
    });
}