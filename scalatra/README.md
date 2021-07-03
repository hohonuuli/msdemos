# Scalatra

Scalatra is a fancy routing DSL that sits on top of good 'ol Java Servlets. It expects to be run in an application server. For this demo we create our own embedded jetty server.

## Notes

Attempted to use json4s support that's built in to Scalatra. That caused the service to fail. No error was reported in the logs.

Deviated from scalatra docs slightly on how to set response type to json. They have a format method to set this, but I couldn't find how to import it.