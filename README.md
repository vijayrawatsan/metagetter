# Meta-Getter

MetaGetter is a handy library written in java, for getting meta information from any url. 

## Running?
```
mvn clean install exec:java
```
Examples:

```
http://localhost:8081?url=https://gist.github.com/berg/9142463
```
```javascript
{
	url: "https://gist.github.com/berg/9142463",
	title: "Comcast and Netflix now have a direct adjacency",
	description: "Comcast and Netflix now have a direct adjacency - Gist is a simple way to share snippets of text and code with others.",
	images: [
				"https://2.gravatar.com/avatar/6d68d7deb2bfecfdd356fcc4d2833d5a?d=https%3A%2F%2Fidenticons.github.com%2F847be4f9bc5b840f9c9cbcadbcfcff7c.png&s=140"
			]
}
```

```
http://localhost:8081?url=http://arstechnica.com/tech-policy/2014/02/harvard-supercomputing-cluster-hijacked-to-produce-alt-cryptocurrency/
```
```javascript
{
	url: "http://arstechnica.com/tech-policy/2014/02/harvard-supercomputing-cluster-hijacked-to-produce-alt-cryptocurrency/",
	title: "Harvard supercomputing cluster hijacked to produce dumb cryptocurrency",
	description: "Wow. Shibe-faced Dogecoin illicitly mined on Ivy League supercomputer. Amaze!",
	images: [
				"http://cdn.arstechnica.net/wp-content/uploads/2014/02/ir6RDbtTesYUv-640x640.png",
				"http://cdn.arstechnica.net/wp-content/themes/arstechnica/assets/images/feature-thumbs/smartphone-thumb.jpg",
				"http://cdn.arstechnica.net/wp-content/uploads/2014/02/ir6RDbtTesYUv-300x300.png",
				"http://cdn.arstechnica.net/wp-content/uploads/2011/06/bitcoin_list.jpg",
				"http://cdn.arstechnica.net/wp-content/uploads/2014/02/ody5-640x273.png",
				"http://cdn.arstechnica.net/wp-content/uploads/2013/12/lee-headshot-2-sm.jpg",
				"http://cdn.arstechnica.net/wp-content/uploads/2014/02/2014-02-17_12-47-33-300x100.jpg",
				"http://brightcove.vo.llnwd.net/d21/unsecured/media/636468927001/201402/2788/636468927001_3219377200001_Twitch-Plays-Pokemon-vs.jpg?pubId=636468927001",
				"http://cdn.arstechnica.net/wp-content/uploads/2014/01/verizon-money-talks-150x150.jpg",
				"http://cdn.arstechnica.net/wp-content/uploads/2014/02/pick-150x150.jpg",
				"http://cdn.arstechnica.net/wp-content/uploads/2012/09/quasar_3c186-150x150.jpg",
				"http://cdn.arstechnica.net/wp-content/themes/arstechnica/assets/images/ad_choices_arrow.png"
			]
}
```
## Why?

Well, I needed a similar functionality like facebook url parsing. 

## Will it be as fast as facebook?

No. 
Facebook caches all the links posted on it by its users. And may be even pre-caches the trending urls.
If you want you can  easily implement a caching solution on top of this code. Easiest way would be to use
google-guava CacheBuilder. Or use redis if you want.


## License

This program is free software; it is distributed under an
[The MIT License](https://github.com/vijayrawatsan/metagetter/blob/master/LICENSE).
