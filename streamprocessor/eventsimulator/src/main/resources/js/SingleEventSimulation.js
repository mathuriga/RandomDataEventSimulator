function singleEventSimulation(){
$(document).ready(function(){


 var attributeValue=["WSO2","34","56"];

 var singleEventSimulationConfig={
            "streamName":"cseEventStream",
            "attributeValues":attributeValue
            };

 alert(JSON.stringify(singleEventSimulationConfig));


if (typeof singleEventSimulationConfig != 'undefined' ) {
                   if(typeof singleEventSimulationConfig !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/EventSimulation/singleEventSimulation",
                                 type: "POST",
                                 data: JSON.stringify(singleEventSimulationConfig),

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