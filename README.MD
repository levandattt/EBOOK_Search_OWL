# OWL_EBOOK_Search

## Introduction
**OWL_EBOOK_Search** is an application for searching eBooks based on an OWL ontology.

## Configuring OWL
### 1. Environment Setup
Before you begin, ensure that you have the following software installed:
- Java JDK 17
- Gradle (depending on your project)
- Apache Jena (for RDF and SPARQL processing)

### 2. Project Configuration
- Add the OWL file to your project.
  - ```tbox``` file 
  - ```abox``` file
- Ensure that the project directory structure includes OWL files in the `resources/data` folder.
- Update the `application.properties` file with the path to the OWL files.
  - ```owl.tbox.path``` - path to the TBox file 
  - ```owl.abox.path``` - path to the ABox file

### 3. Running the Application
- Run the application using the following command:
  - cd to root directory of the project
    ```shell
    ./gradlew bootRun 
    ```
- The application will start on port 3001.
- API List:
  - Search ebook by author at API `http://localhost:3001/api/search?author=author_name`.
  - Execute SPARQL query at API `http://localhost:3001/api/sparql` with the following parameters:
    - form data
      - query: SPARQL query
- You can import the Postman collection from the `resources` folder to test the APIs.
