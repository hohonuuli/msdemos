# Helidon

## Notes

Configuring CORS as described in the docs causes the app to fail to compile in Scala due to a cyclic reference error.

Uses JSON-B. So scala case classes need `@BeanProperty` annotations on their fields and should expose any collections as a Java version via a getter method. Use Java class for converting json to objects. This means that you would probably need to mantain two parallel codecs, one for the Scala case classes and a another for the Java objects.