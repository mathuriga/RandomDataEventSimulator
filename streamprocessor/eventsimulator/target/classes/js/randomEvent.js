
//$(document).ready(function() {
//
//    $("#add").click(function(event){
//
//
//        var request = {
//            type: $('#type').val(),
//            ceilingValue: $('#ceilingValue').val(),
//            floorValue: $('#floorValue').val(),
//            length: $('#length').val()
//        };
//
//        if (typeof request != 'undefined' ) {
//           if(typeof request !='null') {
//
//                $.ajax({
//                        url: "http://localhost:8080/RandomEvent/random",
//                        type: "POST",
//                        data: JSON.stringify(request),
//                        success: function(response) {
//                            console.log(response);
//                        },
//                        error: function(e) {
//                            console.log(e.statusText);
//                        }
//                });
//
//           }
//        }
//    });
//});


//function fetchGrades(rowClass) {
//    var JSONresults = {};
//
//    var indexColumn = 0;
//
//    $('.' + rowClass).each(function () {
////        indexColumn++;
////        JSONresults['field_' + indexColumn] = $(this).val();
//     JSONresults = {
//            type: $('#type').val(),
//            ceilingValue: $('#ceilingValue').val(),
//            floorValue: $('#floorValue').val(),
//            length: $('#length').val()
//        };
//    });
//
//    alert(JSON.stringify(JSONresults)); //show me my JSON object in string format
//    if (typeof JSONresults != 'undefined' ) {
//           if(typeof JSONresults !='null') {
//
//                $.ajax({
//                        url: "http://localhost:8080/RandomEvent/random",
//                        type: "POST",
//                        data: JSON.stringify(JSONresults),
//                        success: function(response) {
//                            console.log(response);
//                        },
//                        error: function(e) {
//                            console.log(e.statusText);
//                        }
//                });
//
//           }
//        }
//}


 $(document).ready(function(){

    // code to read selected table row cell data (values).
    $("#randomevent1").on('click','.addbtn',function(){
         // get the current row
         var currentRow=$(this).closest("tr");

         var col1=currentRow.find("td:eq(0) input").val(); // get current row 1st TD value
         var col2=currentRow.find("td:eq(1) input").val(); // get current row 2nd TD
         var col3=currentRow.find("td:eq(2) input").val(); // get current row 3rd TD
         var col4=currentRow.find("td:eq(3) input").val();
       var request = {
            type: col1,
            ceilingValue: col2,
            floorValue: col3,
            length:col4

      };
         alert(JSON.stringify(request));


         if (typeof request != 'undefined' ) {
                   if(typeof request !='null') {

                         $.ajax({
                                 url: "http://localhost:8080/RandomEvent/random",
                                 type: "POST",
                                 data: JSON.stringify(request),
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
});