<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>QueryDeployer</title>
</head>
<body>
<form method="post" action="http://localhost:8080/Query/Deploy">
    <H3>Simulate using single event</h3>
    <table cellspacing="10">
        <tr>
            <td>Input Stream Definition:</td>
            <td><input type="text" name="inputStreamDefinition" /></td>
        </tr>
        <tr>
            <td>Query:</td>
            <td><textarea rows="4" cols="50" name="query">Type your Query</textarea></td>
        </tr>
        <tr>
            <td>Input Stream</td>
            <td><input type="text" name="inputStream" /></td>
        </tr>

        <tr>
                <td>Input Stream Attribute type List</td>
                <td><input type="text" name="inputStreamAttributeNameList" /></td>
         </tr>

        <tr>
                    <td>Input Stream Attribute type List</td>
                    <td><input type="text" name="inputStreamAttributeTypeList" /></td>
         </tr>
         <tr>
            <td>Output Stream</td>
            <td><input type="text" name="outputStream" /></td>
         </tr>

         <tr></tr>
        <tr>
            <td><input name="add" type="submit" value="Add Query" /></td>
            <td><input name="reset" type="reset" value="Reset" /></td>
        </tr>

    </table>
</form>
</body>
</html>