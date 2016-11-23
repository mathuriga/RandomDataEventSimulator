
function generateEvent(){
$(document).ready(function(){
//$("#myButton").on('click',function(){

   var arrData=[];
   // loop over each table row (tr)
   $("#MyTable tr:not(:first-child)").each(function(){
        var currentRow=$(this);

               var col1=currentRow.find("td:eq(0) input").val(); // get current row 1st TD value
               var col2=currentRow.find("td:eq(1) input").val(); // get current row 2nd TD
               var col3=currentRow.find("td:eq(2) input").val(); // get current row 3rd TD
               var col4=currentRow.find("td:eq(3) input").val();
               var request = {
                  type: col1,
                  min: col2,
                  max: col3,
                  length:col4

            };
      // alert(JSON.stringify(request));
        arrData.push(request);
   });
   var no=$('#no').val();
   var delay=$('#delay').val();

   var RandomEventConfig={RandomEventDetail: arrData,
        noOfEvents: no,
        delay: delay
        };
         alert(JSON.stringify(RandomEventConfig));
//    console.log(arrData);

if (typeof RandomEventConfig != 'undefined' ) {
                   if(typeof RandomEventConfig !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/random",
                                 type: "POST",
                                 data: JSON.stringify(RandomEventConfig),

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


//////  js code to send configuration details of generating evemts using pattern to api /////////////////////


function generateEventPattern(){
$(document).ready(function(){
//$("#add").on('click',function(){
   //alert("wfrgfw");
   var arrData=[];
   // loop over each table row (tr)
   $("#pattern tr:not(:first-child)").each(function(){
        var currentRow=$(this);

               var col1=currentRow.find("td:eq(0) input").val(); // get current row 1st TD value
               var col2=currentRow.find("td:eq(1) input").val(); // get current row 2nd TD
               var col3=currentRow.find("td:eq(2) input").val(); // get current row 3rd TD
               var col4=currentRow.find("td:eq(3) input").val();
               var request = {
                  attributeName: col1,
                  pattern: col2,
                  length: col3,


            };
      // alert(JSON.stringify(request));
        arrData.push(request);
   });
   var no=$('#no').val();
   var delay=$('#delay').val();

   var RandomEventConfig={RandomPatternEventDetail: arrData,
        noOfEvents: no,
        delay: delay,
        min: 0,
        max: 0
        };
         alert(JSON.stringify(RandomEventConfig));
//    console.log(arrData);

if (typeof RandomEventConfig != 'undefined' ) {
                   if(typeof RandomEventConfig !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/random/pattern",
                                 type: "POST",
                                 data: JSON.stringify(RandomEventConfig),

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
