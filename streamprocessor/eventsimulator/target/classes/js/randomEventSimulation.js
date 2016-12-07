function generateEvent(){
$(document).ready(function(){


 var input={
             "streamName": "inputStream1",
             "events": "5",
             "delay": "100",
             "attributeSimulation": [
                 {
                     "type": "PROPERTYBASED",
                     "category": "Contact",
                     "property": "Full Name"
                 },
//                 {
//                     "type": "CUSTOMDATA",
//                     "customDataList": "'mathu','kapil,thava','milani','siva'"
//                      //"customDataList": "mathu,'kapil,thava',milani,siva"
//                 },
                 {
                     "type": "REGEXBASED",
                     "pattern": "[+]?[0-9]*\\.?[0-9]+"
                 },
                 {
                     "type": "PRIMITIVEBASED",
                     "min": "2",
                     "max": "200",
                     "length": "2"
                 }
             ]

             };


         alert(JSON.stringify(input));


if (typeof input != 'undefined' ) {
                   if(typeof input !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/demo",
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
//});

}