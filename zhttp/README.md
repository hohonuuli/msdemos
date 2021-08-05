# zhttp (zio-http)

## Notes

ctrl-c does not exit the program. Apparently, this is a quirk of zio.

Running the bare-bones service as described on the web site had issues. Initial load testing had 25% of the requests failing. I configured it to run with a few threads and that seemed to address it.
