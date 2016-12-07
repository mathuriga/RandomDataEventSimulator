function singleEventSimulation(){
$(document).ready(function(){


 var input1=["mathu","34","56"];

 var input={
            "streamName":"inputStream1",
            "attributeValues":input1
            };

 alert(JSON.stringify(input));


if (typeof input != 'undefined' ) {
                   if(typeof input !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/singleEvent",
                                 type: "POST",
                                 data: JSON.stringify(input),

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
}