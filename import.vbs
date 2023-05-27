Dim fso, scriptPath, inputFile, fileContent, lines, variables

' Create a FileSystemObject
Set fso = CreateObject("Scripting.FileSystemObject")

' Get the path of the script
scriptPath = fso.GetParentFolderName(WScript.ScriptFullName)
inputFile = fso.BuildPath(scriptPath, "CONFIG.txt")

' Read the file content
fileContent = fso.OpenTextFile(inputFile).ReadAll

' Split the content into lines
lines = Split(fileContent, vbCrLf)

' Initialize the dictionary to store the variables
Set variables = CreateObject("Scripting.Dictionary")

' Process each line and extract the variable and its value
For Each line In lines
    parts = Split(line, "=")
    If Ubound(parts) = 1 Then
	    variable = Trim(parts(0))
	    value = Trim(parts(1))
	    variables(variable) = value
    End If
Next

' Access the values using the variable names
password = variables("password")
path = variables("path")

Function Q(s)
	Q = """" & s & """"
End Function

Set shell = CreateObject("WScript.Shell")
commandmysql = Q(path & "\mysql") & " -u root -p" & password & " -e " & Q("CREATE DATABASE IF NOT EXISTS library")
commanddump = Q(path & "\mysqldump") & " -u root -p" & password & " library < " & Q(scriptPath & "\dump.sql")

' Run the command in CMD
shell.Run "%comspec% /c " & Q(commandmysql), 1, True
shell.Run "%comspec% /c " & Q(commanddump), 1, True