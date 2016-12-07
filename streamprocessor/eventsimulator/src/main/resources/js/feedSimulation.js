function feedSimulationExecutor(){
$(document).ready(function(){


 var input=[
            {
            "simulationType" : "Random data simulation",
            "simulationConfig": {
                                "streamName": "inputStream1",
                                "events": "5",
                                "delay": "200",
                                "AttributeSimulation": [
                                    {

                                        "type": "PROPERTYBASED",
                                        "category": "Contact",
                                        "property": "Full Name",
                                    },
                                    {

                                        "type": "REGEXBASED",
                                        "pattern": "[+]?[0-9]*\\.?[0-9]+"
                                    },
                                    {

                                        "type": "PRIMITIVEBASED",
                                        "min": "2",
                                        "max": "200",
                                        "length": "2",
                                    },
                                    {

                                        "type": "custom",
                                        "list": "2,3,4"
                                    },
                                ]
                                },
            "orderBytimeStamp" : "false"

            },
            {
                "simulationType" : "CSV feed simulation",
                "simulationConfig": {
                                    "streamName" : "inputStream2",
                                    "fileName"   : "testData",
                                    "delimeter"  : ",",
                                    "delay"		 : "1000ms"
                                    },
                "orderBytimeStamp" : "false"
            },
            {
                "simulationType" : "Random data simulation",
            "simulationConfig": {
                                "streamName": "inputStream3",
                                "events": "5",
                                "delay": "200",
                                "AttributeSimulation": [
                                    {

                                        "type": "PROPERTYBASED",
                                        "category": "Contact",
                                        "property": "Full Name",
                                    },
                                    {

                                        "type": "REGEXBASED",
                                        "pattern": "[+]?[0-9]*\\.?[0-9]+"
                                    },
                                    {

                                        "type": "PRIMITIVEBASED",
                                        "min": "2",
                                        "max": "200",
                                        "length": "2",
                                    },
                                    {

                                        "type": "custom",
                                        "list": "2,3,4"
                                    },
                                ]
                                },
            "orderBytimeStamp" : "false"
            }
            ];





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