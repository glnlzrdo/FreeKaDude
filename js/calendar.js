$(document).ready(function () {


    var todayDate = moment().startOf('day');
    var YM = todayDate.format('YYYY-MM');
    var YESTERDAY = todayDate.clone().subtract(1, 'day').format('YYYY-MM-DD');
    var TODAY = todayDate.format('YYYY-MM-DD');
    var TOMORROW = todayDate.clone().add(1, 'day').format('YYYY-MM-DD');
    // page is now ready, initialize the calendar...

    var calendar = $('#calendar').fullCalendar({
        // put your options and callbacks here
        theme: Boolean,
        default: true,
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
            end: YM + '-14'
        }],
        eventMouseover: function(event, jsEvent, view) {
            if (view.name !== 'agendaDay') {
                $(jsEvent.target).attr('title', event.title);
            }
        },
        /*
        eventClick: function (calEvent, jsEvent, view) {

            alert('Event: ' + calEvent.title);
            alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            alert('View: ' + view.name);

            // change the border color just for fun
            $(this).css('border-color', 'red');

        },
        */
        
        eventClick: function(calEvent, jsEvent, view)
        {
            var r=confirm("Delete " + calEvent.title + "?");
            if (r===true)
              {
                  $('#calendar').fullCalendar('removeEvents', calEvent._id);
              }
        },

        selectHelper: true,

        /*

        select: function (start, end) {

            var starttime = moment(start).format('dddd MMM DD, h:mm a');
            var endtime = moment(end).format('h:mm a');
            var mywhen = starttime + ' - ' + endtime;
            $('#createEventModal #apptStartTime').val(moment(start));
            $('#createEventModal #apptEndTime').val(moment(end));
            
            //$('#createEventModal #apptAllDay').val(allDay);
            $('#createEventModal #when').text(mywhen);
            $('#createEventModal').modal('show');

            $('#prompt-save').on('click', function (e) {
                // We don't want this to act as a link so cancel the link action
                e.preventDefault();

                doSubmit();
            });

            function doSubmit() {
                $("#createEventModal").modal('hide');
                
                console.log($('#apptStartTime').val());
                console.log($('#apptEndTime').val());
                console.log($('#apptAllDay').val());
                //alert("form submitted");

                  
                // $("#calendar").fullCalendar('renderEvent',
                //     {
                //         title: $('#activityName').val(),
                //         start: new Date($('#apptStartTime').val()),
                //         end: new Date($('#apptEndTime').val()),
                //         //allDay: ($('#apptAllDay').val() == "true"),
                //     },
                //     true);
                
                
                var title = $.trim($('#activityName').val());
                var eventData;
				if (title) {
					eventData = {
						title: title,
						start: start,
						end: end
					};
					$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
				}
				$('#calendar').fullCalendar('unselect');
            }

        }

        */

        
        select: function(start, end) {
            var title = prompt('Activity Description:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };
                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
            }
            $('#calendar').fullCalendar('unselect');
        }
        

    });


    /*

    function selectCal(start, end, allDay) {
        //var endtime = $.fullCalendar.formatDate(end, 'h:mm tt');
        //var starttime = $.fullCalendar.formatDate(start, 'ddd, MMM d, h:mm tt');
        var starttime = moment(start).format('dddd MMM d, h:mm a');
        var endtime = moment(end).format('h:mm a');
        var mywhen = starttime + ' - ' + endtime;
        $('#createEventModal #apptStartTime').val(start);
        $('#createEventModal #apptEndTime').val(end);
        $('#createEventModal #apptAllDay').val(allDay);
        $('#createEventModal #when').text(mywhen);
        $('#createEventModal').modal('show');

    }

    $('#prompt-save').on('click', function (e) {
        // We don't want this to act as a link so cancel the link action
        e.preventDefault();

        doSubmit();
    });

    function doSubmit() {
        $("#createEventModal").modal('hide');
        
        console.log($('#apptStartTime').val());
        console.log($('#apptEndTime').val());
        console.log($('#apptAllDay').val());
        alert("form submitted");
          
        $("#calendar").fullCalendar('renderEvent',
            {
                title: $('#patientName').val(),
                start: new Date($('#apptStartTime').val()),
                end: new Date($('#apptEndTime').val()),
                allDay: ($('#apptAllDay').val() == "true"),
            },
            true);
    }

    */

});

