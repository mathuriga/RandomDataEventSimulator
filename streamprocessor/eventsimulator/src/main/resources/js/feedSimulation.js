function feedSimulationExecutor(){
$(document).ready(function(){


 var feedSimulationConfiguration= {
             	        "orderByTimeStamp" : "false",
                        "streamConfiguration" :[
             		                        {
            	 			                "simulationType" : "RandomDataSimulation",
            								"streamName": "cseEventStream",
            								"events": "5",
            								"delay": "1000",
            								"attributeConfiguration":[
//            								    {
//            										"type": "PROPERTYBASED",
//            								        "category": "Contact",
//            								        "property": "Full Name",
//            								    },
           								        {
                                                    "type": "CUSTOMDATA",
            								        "list": "WSO2,'IBM,XXX'"
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
            								    }

            								  ]
            	    						},
//            	    						{
//             								"simulationType" : "FileFeedSimulation",
//             			 					"streamName" : "cseEventStream",
//             							    "fileName"   : "cseteststream.csv",
//             							    "delimiter"  : ",",
//             							    "delay"		 : "1000"
//             							 	},
             							 	{
                                            "simulationType" : "FileFeedSimulation",
                                            "streamName" : "cseEventStream2",
                                            "fileName"   : "cseteststream2.csv",
                                            "delimiter"  : ",",
                                            "delay"		 : "1000"
                                            }

//             			 					{
//             								"simulationType" : "RandomDataSimulation",
//            	 							"streamName": "inputStream3",
//            								"events": "5",
//            								"delay": "200",
//            								"attributeConfiguration": [
//            								    {
//
//            								        "type": "PROPERTYBASED",
//            								        "category": "Contact",
//            								        "property": "Full Name",
//            								    },
//            								    {
//
//            								        "type": "REGEXBASED",
//            								        "pattern": "[+]?[0-9]*\\.?[0-9]+"
//            								    },
//            								    {
//
//            								        "type": "PRIMITIVEBASED",
//            								        "min": "2",
//            								        "max": "200",
//            								        "length": "2",
//            								    },
//            								    {
//
//            								        "type": "custom",
//            								        "list": "2,3,4"
//            								    },
//            								]
//            	    						}
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