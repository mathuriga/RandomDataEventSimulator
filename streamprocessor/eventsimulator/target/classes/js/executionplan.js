function deployExecutionPlan(){
$(document).ready(function(){

var input= {
        "executionPlanName": "executionPlan1",
        "inputStream":[
            {
                "streamDefinition":"define stream inputStream1 (fullName String ,price2 double, price3 double);",
                "streamName":"inputStream1",
                "streamAttributeDtos" : [
                                {
                                "attributeName":"fullName",
                                "attributeType":"String"
                                },
                                {
                                "attributeName":"price2",
                                 "attributeType":"Double"
                                },
                                {
                                "attributeName":"price3",
                                "attributeType":"Double"
                                }
                                ]
            },
            {
                "streamDefinition":"define stream inputStream2 (lastName String ,price2 double, price3 double);",
                "streamName":"inputStream2",
                "streamAttributeDtos" : [
                                {
                                "attributeName":"lastName",
                                "attributeType":"String"
                                },
                                {
                                "attributeName":" price2 ",
                                 "attributeType":" Double"
                                },
                                {
                                "attributeName":" price3",
                                "attributeType":"Double"
                                }
                                ]
            }
        ],
        "OutputStream":[
            {
                "streamDefinition":"define stream outputStream (maximum double,fullName String,lastName String);",
                "streamName":"outputStream",
                "streamAttributeDtos" : [
                                {
                                "attributeName":" maximum ",
                                 "attributeType":" Double"
                                },
                                {
                                "attributeName":"fullName",
                                "attributeType":"String"
                                },
                                {
                                "attributeName":"lastName",
                                "attributeType":"String"
                                }
                                ]
            }
        ],
        "Queries":[
            {
               "queryName" : "query1",
               "queryDefinition":"@info(name = 'query1') from inputStream1 select maximum(price2, price3) as maximum,inputStream1.fullName as fullName,inputStream2.lastName as lastName insert into outputStream;"
            }
        ]
    };

alert(JSON.stringify(input));


if (typeof input != 'undefined' ) {
                   if(typeof input !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/ExecutionPlan/deploy",
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