<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="OpenWEI.aspx.cs" Inherits="OhmBaseOpenWei.OpenWEI" %>
<% @Import Namespace="OhmBaseOpenWei" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>The Open WEI</title>
    <style>
        body {
            background-color: #0081C9
        }       
        table {
            width: 90%;
            border-collapse: collapse;
            background-color: #FFFFFF;

        }
        table, td, th {
            border: 2px solid black;
            padding: 5px;
            color : #000000;
        }
        th {
	        text-align: left;
        }
        div{
            color: #FFFFFF;
        }
</style>
</head>
<body runat="server">
    <form id="OpenWEIform" runat="server">
    <center>
     <img src="headerbar.png" style="width:900px;height:200px;">
     <img src="corner.png" style="width:50px;height:50px;position:fixed;bottom:0px;left:0px;""/> 
     <img src="corner.png" style="width:50px;height:50px;position:fixed;bottom:0px;right:0px;""/> 
     <img src="corner.png" style="width:50px;height:50px;position:fixed;top:0px;right:0px;""/> 
     <img src="corner.png" style="width:50px;height:50px;position:fixed;top:0px;left:0px;""/> 

	<div></div>
        <select id="parts" name="parts">
		<option value="">Select a part:</option>
		<option value="amplifiers">Amplifiers</option>
		<option value="batteries">Batteries</option>
		<option value="buzzers">Buzzers</option>
		<option value="capacitors">Capacitors</option>
		<option value="diodes">Diodes</option>
		<option value="fuses">Fuses</option>
		<option value="inductors">Inductors</option>
		<option value="isolators">Isolators</option>
		<option value="leds">LEDs</option>
		<option value="logic_gates">Logic Gates</option>
		<option value="microcontrollers">Microcontrollers</option>
		<option value="microphones">Microphones</option>
		<option value="motors">Motors</option>
		<option value="oscillators">Oscillators</option>
		<option value="potentiometers">Potentiometers</option>
		<option value="power_supplies">Power Supplies</option>
		<option value="resistors">Resistors</option>
		<option value="sensors">Sensors</option>
		<option value="speakers">Speakers</option>
		<option value="transformers">Transformers</option>
		<option value="transistors">Transistors</option>
		<option value="other">Other</option>	
	</select> 

    <input type="text" placeholder="Search..." name="search_name"/>
    <input type="submit" formaction="OpenWEI.aspx?Search=read" value="Search"/>
       <div>
       
       <%
           //looks at url and checks if it sees Search=read 
        if (Request.QueryString["Search"]=="read")
        {
            //checks if the user selected a part
            if (Request.Form["parts"] == "")
            Response.Write("<div>Please Select a Part before searching</div>");
            else
            {
                //runs search and gets results back in a list
                List<tableinfo> temp_list = searchcontrol.Read(Request.Form["parts"], Request["search_name"]);
                //if it has any results
                if (temp_list.Any())
                {
                    //displays total results count
                    Response.Write(String.Format("<div>{0} Results found</div>", temp_list.Count()));
                    Response.Write("<div><table>");
                    Response.Write("<tr><th style= width:20% >Name</th><th style= width:30% >Notes</th><th style= width:5% >Quantity</th><th style = width:10% >Last Modified</th><th  style = width:35% >Spec Sheets</th></tr>");
                    //the results are loaded into a table
                    for (int i = 0; i < temp_list.Count; i++)
                    {
                        Response.Write(String.Format("<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td></tr>",
                            temp_list[i].Name, temp_list[i].Notes, temp_list[i].Quantity, temp_list[i].Last_Modified, temp_list[i].Spec_Sheets));
                    }

                    Response.Write("</table></div>");
                   
                }
                // if no results found it displays 0 found
                else
                    Response.Write("<div>0 Results found</div>");
            }
                
                
        }
        
    %>  
       
        </div>
        </center>
       </form>
</body>
</html>
