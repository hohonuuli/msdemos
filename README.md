# MSDEMOS

Microservice demos in Scala. For performance comparison of HTTP + JSON using a frameworks idiomatic style in Scala.

Framework requirements:

- Enable CORS
- Non-blocking


## Test cases:

- GET /text - Returns the string "Hello World".

- GET /media/demo/{i}/{j}/{k} - Return a json response of random data with #i videosequences each containing #j videos each containing #k videoreferences

- POST /media/demo - Same as get above but parses a JSON request body of:

```json
{
  "i": {i},
  "j": {j},
  "k": {k}
}

```

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).


### Load testers
- [Ali](https://github.com/nakabonne/ali)
- [JMeter](https://jmeter.apache.org/)
- [Sniper](https://github.com/btfak/sniper)
- [Thrash](https://github.com/TylerBrock/thrash)

### Notebook

```
docker run -p 8192:8192 -v (pwd):/opt/polynote/notebooks --name polynote mbari/polynote
```