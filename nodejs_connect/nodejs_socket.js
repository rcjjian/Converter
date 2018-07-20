"use strict";

const net = require('net');

class Socket{

	constructor(){
		console.log('create connection');

		const client = net.createConnection({port: 8100}, () => {
		  // 'connect' listener
		  console.log('connected to server!');
		  let req_body = {
		  	id : '1234',
		  	inputPath : "H:\\test\\5ab9feaf4052c9a024000002.ppt",
		  	outputPath : "H:\\test\\thumbs",
		  	action : 'play'
		  };
		  let json_str = JSON.stringify(req_body) + '\n';
		  console.log(json_str);
		  client.write(json_str,'UTF-8');
		});

		client.on('data',(data)=>{
			console.log('server callback data:');
			console.log(data.toString());
		});
	}

}

module.exports = new Socket();