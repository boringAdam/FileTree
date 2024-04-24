# FileTree

This is a simple Spring Boot application that provides endpoints to perform a recursive search for a given type of file. 

## Endpointns

<code>/getunique</code>
Lists the distinct filenames recursively inside the root folder with the spcified extention. <br>
<code>/history</code>
Lists the logs about the previous requests and responses on the <code>/getunique</code> endpoint. <br>
<code>/gen</code>
Generates random filestructure recursively. <br>
<code>/doc</code>
Redirects to the generated javaDoc.<br>

The application endpoints can also be tested via Swagger Ui <br>
http://localhost:8081/swagger-ui/index.html

## Requirements

<li>Java 17 or above
<li>Podman
<li>Make

## Quick Setup

```bash
git clone https://github.com/boringAdam/FileTree.git
cd FileTree
make run
```
The application starts automaticly on two instances. 
Those can be accessed on port ```http://localhost:8081/``` and ```http://localhost:8082/```.
