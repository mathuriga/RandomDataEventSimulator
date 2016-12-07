function fileFeedSimulation(){
$(document).ready(function(){

var input={
            "streamName" : "inputStream1",
            "fileName"   : "testdata.csv",
            "delimiter"  : ",",
            "delay"		 : "1000"
            };

 alert(JSON.stringify(input));


if (typeof input != 'undefined' ) {
                   if(typeof input !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/fileFeedSimulation",
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