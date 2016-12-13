$(document).ready(function () {


    var todayDate = moment().startOf('day');
    var YM = todayDate.format('YYYY-MM');
    var YESTERDAY = todayDate.clone().subtract(1, 'day').format('YYYY-MM-DD');
    var TODAY = todayDate.format('YYYY-MM-DD');
    var TOMORROW = todayDate.clone().add(1, 'day').format('YYYY-MM-DD');
    var dstart, dend, currentEvent, newEvent;
    // page is now ready, initialize the calendar...

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
        events: [{
            title: 'Meeting',
            start: TODAY + 'T10:30:00',
            end: TODAY + 'T12:30:00'
        }, {
            title: 'Meeting with Eman & Charles',
            start: YM + '-12',
            end: YM + '-14'
        }, {
            title: 'Project Deadline',
            start: YM + '-14',
            end: YM + '-15'
        }],
        eventMouseover: function (event, jsEvent, view) {
            if (view.name !== 'agendaDay') {
                $(jsEvent.target).attr('title', event.title);
            }
        },

        eventClick: function (calEvent, jsEvent, view) {
            // var r = confirm("Delete " + calEvent.title + "?");
            // if (r === true) {
            //     $('#calendar').fullCalendar('removeEvents', calEvent._id);
            // }

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

    $('#prompt-save').on('click', function (e) {
        // We don't want this to act as a link so cancel the link action
        e.preventDefault();
        $("#createEventModal").modal('hide');
        var title = $('#activityName').val();
        var eventData;
        if (title) {
            eventData = {
                title: title,
                start: dstart,
                end: dend
            };
            
            
            //AJAX feature to save to database            
            $.ajax({
              url: 'process',
              data: 'title='+title+'&startDate='+moment(dstart).format('dddd MMM DD, h:mm a')+'&endDate='+moment(dend).format('dddd MMM DD, h:mm a'),
              type: 'POST',
              dataType: 'json',
              success: function(response){
            	  $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true	
              },
              error: function(e){
                console.log(e.responseText);
              }
            });
            
        }
        $('#calendar').fullCalendar('unselect');
    });

    $('#prompt-update').on('click', function (e) {
        e.preventDefault();
        $("#editEventModal").modal('hide');
        var title = $('#inputActivity').val();
        var eventData;
        if (title) {
//            eventData = {
//                title: title,
//                start: dstart,
//                end: dend
//            };
        	currentEvent.title = title;
        	currentEvent.start = dstart;
        	currentEvent.end = dend;
            $('#calendar').fullCalendar('updateEvent', currentEvent);
            //$('#calendar').fullCalendar('removeEvents', currentEvent._id);
            //$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
        }
        $('#calendar').fullCalendar('unselect');
    });

    $('#prompt-delete').on('click', function (e) {
        e.preventDefault();
         var r = confirm("Are you sure? This cannot be undone.");
             if (r === true) {
                 $('#calendar').fullCalendar('removeEvents', currentEvent._id);
                 $("#editEventModal").modal('hide');
             }
        $('#calendar').fullCalendar('unselect');
    });

});

