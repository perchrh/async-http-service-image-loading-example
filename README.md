# async-http-service-image-loading-example
Example on how to make async HTTP-based API calls and load images over HTTP for a RecyclerView
Using OkHttp3, Retrofit 2 and Picasso 2.5 with OkHttp3-downloader. 

There are Espresso tests and instrument tests. 
These can be extended to click through the app, but should in that case use a mocked recipesearchclient (or similar) to avoid tests depending on doing actual network calls. 
There are no unit tests, as there is no logic to test in the app - it just displays data it receives from the HTTP-based api. 
