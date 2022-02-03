var fs =require('fs')

/* //Reading a file in NodeJS
fs.readFile('Square9.js','utf8',function(err,data)
{
console.log(data)
})
*/

/*
//Writng into file through NodeJS
fs.writeFile('calc1.js','console.log("done")',function(err)
{
	console.log("done")
}
)*/

//Deleting a file
fs.unlink('calc1.js',function(err)
{
	console.log("Deleted")
})
