function eventSimulationDemo(){
$(document).ready(function(){
//$("#add").on('click',function(){
   //alert("wfrgfw");
   var attributeDetails=[];
   // loop over each table row (tr)
   $("#pattern tr:not(:first-child)").each(function(){
        var currentRow=$(this);

               var col1=currentRow.find("td:eq(0) input").val(); // get current row 1st TD value
               var col2=currentRow.find("td:eq(1) input").val(); // get current row 2nd TD
               var col3=currentRow.find("td:eq(2) input").val(); // get current row 3rd TD
               var col4=currentRow.find("td:eq(3) input").val();
               var col5=currentRow.find("td:eq(4) input").val();
               var col6=currentRow.find("td:eq(5) input").val();
               var col7=currentRow.find("td:eq(6) input").val();
               var col8=currentRow.find("td:eq(7) input").val();
               var col9=currentRow.find("td:eq(8) input").val();
               var request = {
                  attributeName: col1,
                  attributeDataType: col2,
                  option: col3,
                  min:col4,
                  max:col5,
                  length:col6,
                  pattern:col7,
                  moduleName:col8,
                  subTypeName:col9
            };
        alert(JSON.stringify(request));
        attributeDetails.push(request);
   });
   var streamName=$('#streamName').val();
   var option=$('#option').val();
   var no=$('#no').val();
   var delay=$('#delay').val();

   var streamDto={streamName: streamName,
              streamAttributeDto: attributeDetails,
              };

   var RandomDataSimulationConfig={streamDto: streamDto,
              option: option,
              noOfEvents: no,
              delay: delay
   };

   alert(JSON.stringify(RandomDataSimulationConfig));

if (typeof RandomDataSimulationConfig != 'undefined' ) {
                   if(typeof RandomDataSimulationConfig !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/demo",
                                 type: "POST",
                                 data: JSON.stringify(RandomDataSimulationConfig),

                                 success: function(response) {
                                     console.log(response);
                                 },
                                 error: function(e) {
                                     console.log(e.statusText);
                                 }
                         });

                    }
                 }
});
//});

}

function singleEventSimulation(){
$(document).ready(function(){
   var attributeDetails=[];
//   var request= {
//             attribute1: $('#attribute1').val(),
//             attribute2: $('#attribute2').val(),
//             attribute3: $('#attribute3').val()
//             };

var request= [$('#attribute1').val(), $('#attribute2').val(), $('#attribute3').val()];

//   attributeDetails.push(request);

   var streamName=$('#streamName').val();

   var SingleEventSimulationConfig={streamName: streamName,
              attributeValues: request
              };

   alert(JSON.stringify(SingleEventSimulationConfig));

if (typeof SingleEventSimulationConfig != 'undefined' ) {
                   if(typeof SingleEventSimulationConfig !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/singleEvent",
                                 type: "POST",
                                 data: JSON.stringify(SingleEventSimulationConfig),
                                 success: function(response) {
                                     console.log(response);
                                 },
                                 error: function(e) {
                                     console.log(e.statusText);
                                 }
                         });

                    }
                 }
});
//});

}










