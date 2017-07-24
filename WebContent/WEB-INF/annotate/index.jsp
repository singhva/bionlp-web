<%@page import="com.bionlp.ctgov.InterventionStruct"%>
<%@page import="edu.stanford.nlp.ling.CoreAnnotations"%>
<%@page import="edu.stanford.nlp.ling.CoreLabel"%>
<%@page import="java.util.List, java.util.Map"%>
<%@page import="com.bionlp.BioNer"%>
<%@page import="edu.stanford.nlp.ie.crf.CRFClassifier"%>
<%@page import="com.bionlp.ctgov.ClinicalStudy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% ClinicalStudy study = (ClinicalStudy) request.getAttribute("study"); %>
<script src="jquery-2.2.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery.qtip.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#conditions").find("li").each(function() {
			var $this = $(this);
			var html = $this.html().trim();
			console.log(html);
			var data = {"q" : html, "type" : ["dsyn", "idcn", "fndg", "neop", "phpr", "bpoc"]};
			$.ajax("/cds/restapi/lookup", { data: data, traditional: true,
				success: function(data) {
					if (data.length > 0) {
						var concept = data[0];
						var text = concept["names"][0] + ",  NCI Code: " + concept["nciCode"];
						$this.append("<span class='annotation'>[ " + text + " ]</span>");
					}
				}
			});
		});
		
		$("#interventions").find("li").each(function() {
			var $this = $(this);
			var html = $this.html().trim();
			console.log(html);
			var data = {"q" : html, "type" : ["orch", "phsu"]};
			$.ajax("/cds/restapi/lookup", { data: data, traditional: true,
				success: function(data) {
					if (data.length > 0) {
						var concept = data[0];
						var text = concept["names"][0] + ",  NCI Code: " + concept["nciCode"];
						$this.append("<span class='annotation'>[ " + text + " ]</span>");
					}
				}
			});
		});
		
		$(".entity").qtip({
			content: {
				text: function(event, api) {
					var myContent = $(this).html();
					var className = $(this).attr("class");
					console.log("My content: " + myContent);
					if (className != "entity O") {
						var semanticTypes = $(this).hasClass("Disease") ? ["dsyn", "idcn", "fndg", "neop", "phpr", "bpoc"] : 
							["orch", "phsu"];
						var data = {"q" : myContent, "type": semanticTypes};
						$.ajax("/cds/restapi/lookup", { data: data, traditional: true,
							success: function(data) {
								if (data.length > 0) {
									var concept = data[0];
									var text = concept["names"][0] + "<br>" + "NCI Code: " + concept["nciCode"];
									api.set('content.text', text);
								}
								else {
									api.set('content.text', "No concept found");
								}
							}
						});
						return;
					}
					else {
						return false;
					}
				}
			}
		});
	});
</script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="jquery.qtip.min.css" />
<style type="text/css">

.entity {
	padding: .2em .6em .3em;
	font-size: 75%;
	font-weight: 700;
	color: #fff;
	display: inline;
	border-radius: .25em;
}

.Disease {
	background-color: #d9534f;
}

.Chemical {
	background-color: orange;
}

.annotation {
	font-size: 75%;
	font-weight: 700
}

</style>
<title>Annotated Clinical Trial <%= request.getParameter("nctId") %> Document</title>
</head>
<body>
	
	<div class="container">
		<h2>Clinical Trial <%= request.getParameter("nctId") %></h2>
		<table class="table">
			<tr>
				<th>Trial field
				<th>Field Value
			</tr>
			<tr>
				<td>Title
				<td><%= study.getOfficialTitle()%>
			</tr>
			<tr>
				<td>Trial Status
				<td><%= study.getOverallStatus() %>
			</tr>
			<tr>
				<td>Trial Phase
				<td><%= study.getPhase() %>
			</tr>
			<tr>
				<td>Start Date
				<td><%= study.getStartDate().getValue() %>
			</tr>
			<tr>
				<td>Anticipated Completion Date
				<td><%= study.getCompletionDate().getValue() %>
			</tr>
			<tr>
				<td>Conditions
				<td>
				<ul id="conditions">
				<%
					for (String condition : study.getCondition()) {
				%>
					<li>
				<%
					out.print(condition);
					}
				%>
				</ul>
			</tr>
			<tr>
				<td>Interventions
				<td>
				<ul id="interventions">
				<%
					for (InterventionStruct intervention : study.getIntervention()) {
				%>
					<li>
				<%
					out.print(intervention.getInterventionName());
					}
				%>
				</ul>
			</tr>
			<tr>
				<td>Detailed Description
				<td>
				<%
				List<Map<String, String>> spans = (List<Map<String, String>>) request.getAttribute("spans");
				for (Map<String, String> span : spans) {
					String entity = span.get("entity");
					String text = span.get("text");
					String className = entity.equals("O") ? "" : "entity " + entity;
					
				%>
				<span class='<%= className%>'><%= text%></span>
				<%
				}
				%>
			</tr>
		</table>	
	</div>
</body>
</html>