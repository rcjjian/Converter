"use strict";

const net = require('net');
const fs = require('fs');
const path = require('path');

class Socket{

	constructor(){
		console.log('create connection');

		//读取目录批量转换
		this.convert = (dir_path)=>{
			let format_limit = ['.ppt','.pptx','.doc','.docx'];
			fs.readdir(dir_path,(err,files) => {
		  	while(files.length > 0){
		  		let file = files.pop();
		  		if(format_limit.indexOf(path.extname(file)) < 0)continue;
		  		let req_body = {
		  			inputPath : dir_path + '\\' + file,
		  			outputPath : dir_path + '\\' + path.basename(file,path.extname(file)),
		  			action : 'play'
		  		}
		  		let json_str = JSON.stringify(req_body) + '\n';
		  		console.log(json_str);
		  		client.write(json_str,'UTF-8');
		  	}
		  });
		}

		let main = this;
		//链接DocumentConverter ServerSocket
		const client = net.createConnection({port: 8100}, () => {
		  // 'connect' listener
		  console.log('connected to server!');

		  console.log('test batch convert files');

		  // let dir_path = 'H:\\DTPlatform\\git_project\\DocumentToThumbnail\\nodejs_connect\\test_files';
		  let dir_path = 'H:\\DTPlatform\\github\\document_to_thumbnail\\nodejs_connect\\test_files';

		  //自动发送信息转换
		  //setInterval(main.convert,5000,dir_path);
		  

            let req_body = {
				inputPath : dir_path + '\\1.doc',
				outputPath : dir_path + '\\1',
				action : 'play'
            }
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