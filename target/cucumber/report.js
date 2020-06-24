$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/java/resources/individual.feature");
formatter.feature({
  "line": 1,
  "name": "Individual",
  "description": "How user enter with dates\nTo register yourself",
  "id": "individual",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 5,
  "name": "Insert one Individual",
  "description": "",
  "id": "individual;insert-one-individual",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 6,
  "name": "Create a IndividualDTO",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "User access the endPoint \"http://localhost:8080/legal-entity\", with HTTP verb Post",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "the server should return a id",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});