function feedSimulationExecutor(){
$(document).ready(function(){


 var feedSimulationConfiguration= {
             	        "orderByTimeStamp" : "true",
                        "streamConfiguration" :[
             		                        {
            	 			                "simulationType" : "RandomDataSimulation",
            								"streamName": "inputStream1",
            								"events": "5",
            								"delay": "200",
            								"attributeConfiguration":[
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
            								    }
            								  ]
            	    						},
            	    						{
             								"simulationType" : "FileFeedSimulation",
             			 					"streamName" : "inputStream2",
             							    "fileName"   : "testdata.csv",
             							    "delimiter"  : ",",
             							    "delay"		 : "1000ms"
             							 	},
             			 					{
             								"simulationType" : "RandomDataSimulation",
            	 							"streamName": "inputStream3",
            								"events": "5",
            								"delay": "200",
            								"attributeConfiguration": [
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
            	    						}
            	       ]
             		};

alert(JSON.stringify(feedSimulationConfiguration));


if (typeof feedSimulationConfiguration != 'undefined' ) {
                   if(typeof feedSimulationConfiguration !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/feedSimulation",
                                 type: "POST",
                                 data: JSON.stringify(feedSimulationConfiguration),

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