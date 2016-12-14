$(document).ready(function () {


    var todayDate = moment().startOf('day');
    var YM = todayDate.format('YYYY-MM');
    var YESTERDAY = todayDate.clone().subtract(1, 'day').format('YYYY-MM-DD');
    var TODAY = todayDate.format('YYYY-MM-DD');
    var TOMORROW = todayDate.clone().add(1, 'day').format('YYYY-MM-DD');
    var dstart, dend, currentEvent, newEvent;
    // page is now ready, initialize the calendar...
    
    var json_events;
    
    function loadCalendar() {
	    //Start-Ajax//
	    $.ajax({
	    	   url: 'loadEvents',
	    	   type: 'POST',
	    	   data: 'json',
	    	   //async: false,
	    	   success: function(response){
	    	     json_events = response;    	     
	    	////////////////////////////////////////////////////////
	    	////////////   Calendar Initialization  ////////////////
	    	////////////////////////////////////////////////////////     
	    	     var calendar = $('#calendar').fullCalendar({
	    	         theme: true,
	    	         //default: true,
	    	         header: {
	    	             left: 'month,agendaWeek,agendaDay,list',
	    	             center: 'title'
	    	         },
	    	         editable: true,
	    	         selectable: true,
	    	         //eventLimit: true, // allow "more" link when too many events
	    	         navLinks: true,
	 	         
	    	         events: JSON.parse(json_events),
	    	         
	    	         eventMouseover: function (event, jsEvent, view) {
	    	             if (view.name !== 'agendaDay') {
	    	                 $(jsEvent.target).attr('title', event.title);
	    	             }
	    	         },
	
	    	         eventClick: function (calEvent, jsEvent, view) {
	    	             $('#inputActivity').val(calEvent.title);
	    	             var starttime = moment(calEvent.start).format('dddd MMM DD, h:mm a');
	    	             var endtime = moment(calEvent._end).format('dddd MMM DD, h:mm a');
	    	             var mywhen = starttime + ' - ' + endtime;
	    	             $('#createEventModal #editStartTime').val(moment(calEvent.start));
	    	             $('#createEventModal #editEndTime').val(moment(calEvent._end));
	
	    	             $('#editEventModal #editWhen').text(mywhen);
	    	             $('#editEventModal').modal('show');
	    	             dstart = calEvent.start;
	    	             dend = calEvent._end;
	    	             currentEvent = calEvent;
	    	         },
	
	    	         selectHelper: true,
	
	    	         select: function (start, end) {
	    	             $('#activityName').val("");
	    	             var starttime = moment(start).format('dddd MMM DD, h:mm a');
	    	             var endtime = moment(end).format('h:mm a');
	    	             var mywhen = starttime + ' - ' + endtime;
	    	             $('#createEventModal #apptStartTime').val(moment(start));
	    	             $('#createEventModal #apptEndTime').val(moment(end));
	
	    	             $('#createEventModal #when').text(mywhen);
	    	             $('#createEventModal').modal('show');
	    	             dstart = start;
	    	             dend = end;	    	             
	    	         }	
	    	     });    	     
	    	     
	    	   },
	    	   error: function(e){
	               console.log(e.responseText);
	             } 
	    	   
	    	////////////////////////////////////////////////////////
	       	////////////   Calendar Initialization  ////////////////
	       	//////////////////////////////////////////////////////// 
	    	   
	    	}); // End-Ajax //
    }

    loadCalendar();
    
    
    function addEvent() {
        $("#createEventModal").modal('hide');
        var title = $('#activityName').val();
        var eventData;
        
        var trimStart = new Date(moment(dstart).format('dddd MMM DD, YYYY h:mm a'));
        if (trimStart.getMinutes() == 30)
        	trimStart.setMinutes(trimStart.getMinutes() - 30);
        var trimEnd = new Date(moment(dend).format('dddd MMM DD, YYYY h:mm a'));
        if (trimEnd.getMinutes() == 30)
        	trimEnd.setMinutes(trimEnd.getMinutes() - 30);
        console.log(moment(trimStart).format('dddd MMM DD, YYYY h:mm a'));
        
        
        if (title) {           
 
            //AJAX feature to save to database            
            $.ajax({
                url: 'process',
                data: 'description='+title+'&startTime='+moment(dstart).format('YYYY-MM-DD HH:mm:ss')+'&endTime='+moment(dend).format('YYYY-MM-DD HH:mm:ss'),
                type: 'POST',
                dataType: 'json',
                success: function(response){
                	eventData = {
                            title: title,
                            start: moment(trimStart),
                            end: moment(trimEnd),
                            id: JSON.parse(response)
                        };
              	  $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true	
                },
                error: function(e){
                  console.log(e.responseText);
                }
              });            
        }
        $('#calendar').fullCalendar('unselect');
    }
    
    $('#activityName').keypress(function (e) {
    	 var key = e.which;
    	 if(key == 13)  // the enter key code
    	  {
    	   addEvent();
    	   return false;
    	  }
    	});   
    

    $('#prompt-save').on('click', function (e) {
    	// We don't want this to act as a link so cancel the link action
        e.preventDefault();
    	addEvent();
    });
    
    
    function updateEvent() {
    	$("#editEventModal").modal('hide');
        var title = $('#inputActivity').val();        
        if (title) {
        	currentEvent.title = title;
        	currentEvent.start = dstart;
        	currentEvent.end = dend;
        	
        	$.ajax({
                url: 'update',
                data: 'event_id=' + currentEvent._id + '&description='+title,
                type: 'POST',
                dataType: 'json',
                success: function(response){
                	$('#calendar').fullCalendar('updateEvent', currentEvent);
                },
                error: function(e){
                  console.log(e.responseText);
                }
              });                 
        	
        	
        	
            //$('#calendar').fullCalendar('updateEvent', currentEvent);
        }
        $('#calendar').fullCalendar('unselect');
    }
    
    $('#inputActivity').keypress(function (e) {
   	 var key = e.which;
   	 if(key == 13)  // the enter key code
   	  {
   	   updateEvent();
   	   return false;
   	  }
   	});   

    $('#prompt-update').on('click', function (e) {
        e.preventDefault();
        updateEvent();
    });

    $('#prompt-delete').on('click', function (e) {
        e.preventDefault();
         var r = confirm("Are you sure? This cannot be undone.");
             if (r === true) {
            	 $.ajax({
                     url: 'delete',
                     data: 'event_id=' + currentEvent._id,
                     type: 'POST',
                     dataType: 'json',
                     success: function(response){
                    	 $('#calendar').fullCalendar('removeEvents', currentEvent._id);
                     },
                     error: function(e){
                       console.log(e.responseText);
                     }
                   });                 
                 $("#editEventModal").modal('hide');
             }
        $('#calendar').fullCalendar('unselect');
    });
    
	$('#createEventModal').on('shown.bs.modal', function () {
    	   $('#activityName').focus();
    	});
    
	$('#editEventModal').on('shown.bs.modal', function () {
 	   $('#inputActivity').focus();
 	});

	var checkboxes = $("input[type='checkbox']");
    var submitButt = $("#searchbutton");
	checkboxes.click(function() {
	    submitButt.attr("disabled", !checkboxes.is(":checked"));
	});
	
});